int direction;
Player player = new Player(width/2, height/2);
boolean isLastKeyPressedUp;
boolean isLastKeyPressedRight;

void setup() {
  size(1200, 800);
}

void draw() {
  background(255);
  player.run();
}

class Player {
  int x;
  int y;
  int speed;
  int direction;
  boolean[] shouldMove;
  boolean[] isFacing;

  Player(int x, int y) {
    this.x = x;
    this.y = y;
    this.speed = 5;
    this.direction = 0;
    this.shouldMove = new boolean[4];
    this.isFacing = new boolean[4];
    stopAllMovement();
  }

  void stopAllMovement() {
    for (int i=0; i<4; i++) {
      shouldMove[i] = false;
    }
  }

  void run() {
    //setDirection();
    display();
    move();
  }

  void display() {
    pushMatrix();
    translate(x,y);
    
    rotate(PI/4 * -direction);
    ellipse(0, 0, 10, 10);
    line(0,0, 0, -10);
    popMatrix();
  } 

  void move() {
    if (shouldMove[0]) {
      this.y = this.y - speed;
    }
    if (shouldMove[1]) {
      this.x = this.x - speed;
    }
    if (shouldMove[2]) {
      this.y = this.y + speed;
    }
    if (shouldMove[3]) {
      this.x = this.x + speed;
    }
  }
/*
  void setDirection() {
    if (isFacing[0] && isFacing[1]) {
      direction = 1;
    } 
    else if (isFacing[0] && isFacing[3]) {
      direction = 7;
    } 
    else if (isFacing[2] && isFacing[1]) {
      direction = 3;
    } 
    else if (isFacing[2] && isFacing[3]) {
      direction = 5;
    }
    if (isFacing[0]) {
      direction = 0;
    } 
    else if (isFacing[1]) {
      direction = 2;
    } 
    else if (isFacing[2]) {
      direction = 4;
    } 
    else if (isFacing[3]) {
      direction = 6;
    }
  }
*/
  void setDirection(int i){
    direction = i;
  }
  
  
}

public void keyPressed() {
  if (keyCode == UP) {
    player.isFacing[0] = true;
  }

  if (keyCode == LEFT) {
    player.isFacing[1] = true;
  }

  if (keyCode == DOWN) {
    player.isFacing[2] = true;
  }

  if (keyCode == RIGHT) {
    player.isFacing[3] = true;
  }
  if (key == 'w') {
    player.shouldMove[0] = true;
  }
  if (key == 'a') {
    player.shouldMove[1] = true;
  }
  if (key == 's') {
    player.shouldMove[2] = true;
  }
  if (key == 'd') {
    player.shouldMove[3] = true;
  }
}

public void keyReleased() {
  if (key == 'w') {
    player.shouldMove[0] = false;
  }
  if (key == 'a') {
    player.shouldMove[1] = false;
  }
  if (key == 's') {
    player.shouldMove[2] = false;
  }
  if (key == 'd') {
    player.shouldMove[3] = false;
  }
}