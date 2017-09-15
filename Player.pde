class Player {
  Gun gun = new Gun(this);

  //float x;
  //float y;
  float speed = 5;
  float angle;

  int radius = 10;
  float originalSpeed = 4;
  float diagonalSpeed = originalSpeed/sqrt(2);
  int directionModifier = 0;

  int[] rgb = {0, 255, 255};
  boolean[] moving = new boolean[4];
  boolean[] facing = new boolean[4];

  PVector location = new PVector();
  PVector velocity = new PVector(0, 0);


  Player(int x, int y) {
    //this.x = x;
    //this.y = y;

    location.x = x;
    location.y = y;
    
  }

  void run() {
    //adjustSpeed();
        setVelocity();
    move();
    setDirection();
    gun.run();
    display();
    velocity.set(0, 0);
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
    location.add(velocity.x * speed, velocity.y * speed);
  }

  void setVelocity() {
    if (isMoving(0)) {
      //this.y = this.y - speed;
      velocity.set(velocity.x, -1);
    }
    if (isMoving(1)) {
      //this.x = this.x - speed;
      velocity.set(-1, velocity.y);
    }
    if (isMoving(2)) {
      ///this.y = this.y + speed;
      velocity.set(velocity.x, 1);
    }
    if (isMoving(3)) {
      //this.x = this.x + speed;
      velocity.set(1, velocity.y);
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
    translate(location.x, location.y);
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
      } else if (isFacing(1)) {
        setDirectionModifier(6);
      } else if (isFacing(2)) {
        setDirectionModifier(4);
      } else if (isFacing(3)) {
        setDirectionModifier(2);
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