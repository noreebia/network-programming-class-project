class Player {
  Gun gun = new Gun(this);

  float x;
  float y;
  float speed;
  float angle;

  int radius = 10;
  float originalSpeed = 4;
  float diagonalSpeed = originalSpeed/sqrt(2);
  int directionModifier = 0;

  int[] rgb = {0, 255, 255};
  boolean[] moving = new boolean[4];
  boolean[] facing = new boolean[4];


  Player(int x, int y) {
    this.x = x;
    this.y = y;
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

  void stopShooting() {
    this.gun.shouldFire(false);
  }

  void display() {
    pushMatrix();
    translate(x, y);
    setAngle();
    rotate(angle);
    fill(rgb[0], rgb[1], rgb[2]);
    ellipse(0, 0, radius*2, radius*2);
    gun.display();
    popMatrix();
  } 

  void setAngle() { 
    angle = PI/4 * this.directionModifier;
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

  void setDirectionModifier(int value) {
    directionModifier = value;
  }

  void shouldMove(int direction, boolean val) {
    moving[direction] = val;
  }

  void shouldFace(int direction, boolean value) {
    facing[direction] = value;
  }

  boolean isFacing(int direction) {
    return facing[direction];
  }

  boolean isMoving(int direction) {
    return moving[direction];
  }
}