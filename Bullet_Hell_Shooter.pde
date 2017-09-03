int direction;
Player player;

void setup() {
  size(1200, 800);
  player = new Player(width/2, height/2);
}

void draw() {
  background(255);
  player.run();
}

class Player {
  int x;
  int y;
  int speed;
  int directionModifier;
  float angle;
  boolean[] moving;
  boolean[] facing;

  Player(int x, int y) {
    this.x = x;
    this.y = y;
    this.speed = 5;
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
    setDirection();
    move();
    display();
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
      } else if (isFacing(2) && isFacing(1)) {
        setDirectionModifier(5);
      } else if (isFacing(0) && isFacing(1)) {
        setDirectionModifier(7);
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

public void keyPressed() {
  if (keyCode == UP) {
    player.shouldFace(0, true);
  }

  if (keyCode == LEFT) {
    player.shouldFace(1, true);
  }

  if (keyCode == DOWN) {
    player.shouldFace(2, true);
  }

  if (keyCode == RIGHT) {
    player.shouldFace(3, true);
  }
  if (key == 'w') {
    player.shouldMove(0, true);
  }
  if (key == 'a') {
    player.shouldMove(1, true);
  }
  if (key == 's') {
    player.shouldMove(2, true);
  }
  if (key == 'd') {
    player.shouldMove(3, true);
  }
}

public void keyReleased() {
  if (keyCode == UP) {
    player.shouldFace(0, false);
  }

  if (keyCode == LEFT) {
    player.shouldFace(1, false);
  }

  if (keyCode == DOWN) {
    player.shouldFace(2, false);
  }

  if (keyCode == RIGHT) {
    player.shouldFace(3, false);
  }
  if (key == 'w') {
    player.shouldMove(0, false);
  }
  if (key == 'a') {
    player.shouldMove(1, false);
  }
  if (key == 's') {
    player.shouldMove(2, false);
  }
  if (key == 'd') {
    player.shouldMove(3, false);
  }
}