class Player {
  float x;
  float y;
  float speed;

  float originalSpeed;
  float diagonalSpeed;
  int directionModifier;
  float angle;
  boolean[] moving;
  boolean[] facing;

  Player(int x, int y) {
    this.x = x;
    this.y = y;

    originalSpeed = 5;
    diagonalSpeed = originalSpeed/sqrt(2);

    this.directionModifier = 0;
    this.moving = new boolean[4];
    this.facing = new boolean[4];
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

  void display() {
    pushMatrix();
    translate(x, y);
    setAngle();
    rotate(getAngle());
    ellipse(0, 0, 10, 10);
    line(0, 0, 0, -10);
    popMatrix();
  } 

  void setAngle() { 
    angle = PI/4 * getDirectionModifier();
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

  int getDirectionModifier() {
    return directionModifier;
  }
}