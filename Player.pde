class Player {
  float x;
  float y;
  float speed;
  int radius;
  float originalSpeed;
  float diagonalSpeed;
  int directionModifier;
  float angle;
  int[] rgb = {0, 255, 255};
  boolean[] moving;
  boolean[] facing;
  Gun gun;

  Player(int x, int y) {
    this.x = x;
    this.y = y;
    this.radius = 20;
    this.originalSpeed = 4;
    this.diagonalSpeed = originalSpeed/sqrt(2);
    this.directionModifier = 0;
    this.moving = new boolean[4];
    this.facing = new boolean[4];
    this.gun = new Gun(this);
    stopAllMovement();
  }

  void stopAllMovement() {
    for (int i=0; i<4; i++) {
      shouldMove(i, false);
    }
  }

  void run() {
    adjustSpeed();
    move();
    setDirection();
    gun.run();
    display();
  }

  void adjustSpeed() {
    int count=0;
    for (int i=0; i<4; i++) {
      if (moving[i]) {
        count++;
      }
    }
    if (count >= 2) {
      setSpeed(diagonalSpeed);
    } else {
      setSpeed(originalSpeed);
    }
  }

  void setSpeed(float speed) {
    this.speed = speed;
  }

  void move() {
    if (isMoving(0)) {
      this.y = this.y - speed;
    }
    if (isMoving(1)) {
      this.x = this.x - speed;
    }
    if (isMoving(2)) {
      this.y = this.y + speed;
    }
    if (isMoving(3)) {
      this.x = this.x + speed;
    }
  }

  void shoot() {
    this.gun.shouldFire(true);
  }

  void holdFire() {
    this.gun.shouldFire(false);
  }

  void display() {
    pushMatrix();
    translate(x, y);
    setAngle();
    rotate(getAngle());
    fill(rgb[0], rgb[1], rgb[2]);
    ellipse(0, 0, radius, radius);
    gun.display();
    popMatrix();
  } 

  void setAngle() { 
    angle = PI/4 * this.directionModifier;
  }

  float getAngle() {
    return angle;
  }

  void setDirection() {
    int count=0;
    for (int i=0; i<4; i++) {
      if (isFacing(i)) {
        count++;
      }
    }
    if (count >= 2) {
      if (isFacing(0) && isFacing(3)) {
        setDirectionModifier(1);
      } else if (isFacing(2) && isFacing(3)) {
        setDirectionModifier(3);
        this.speed = diagonalSpeed;
      } else if (isFacing(2) && isFacing(1)) {
        setDirectionModifier(5);
        this.speed = diagonalSpeed;
      } else if (isFacing(0) && isFacing(1)) {
        setDirectionModifier(7);
        this.speed = diagonalSpeed;
      }
    } else {
      if (isFacing(0)) {
        setDirectionModifier(0);
      } else if (isFacing(2)) {
        setDirectionModifier(4);
      } else if (isFacing(3)) {
        setDirectionModifier(2);
      } else if (isFacing(1)) {
        setDirectionModifier(6);
      }
    }
  }

  void shouldMove(int direction, boolean val) {
    moving[direction] = val;
  }

  boolean isMoving(int direction) {
    return moving[direction];
  }

  void shouldFace(int direction, boolean value) {
    facing[direction] = value;
  }

  boolean isFacing(int direction) {
    return facing[direction];
  }

  void setDirectionModifier(int value) {
    directionModifier = value;
  }
}