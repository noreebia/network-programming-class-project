class Enemy {
  Player target;

  float x;
  float y;
  float velocityX;
  float velocityY;

  int radius = 10;
  int offset = radius + 50;
  float speed = 1.5;

  Enemy(float x, float y, Player target) {
    this.x = x;
    this.y = y;
    this.target = target;
    setVelocity();
  }

  Enemy(float x, float y) {
    this.x = x;
    this.y = y;
  }

  void setTarget(Player target) {
    this.target = target;
  }

  void run() {
    if (isOutOfMap()) {
      setVelocity();
    }
    move();
    display();
  }

  void move() {
    this.x += velocityX;
    this.y += velocityY;
  }

  void display() {
    fill(255, 128, 0);
    ellipse(x, y, radius*2, radius*2);
  }

  boolean isOutOfMap() {
    if (x<-offset || x>(width+offset) || y<-offset || y>(height+offset)) {
      return true;
    }
    return false;
  }

  /*
  void setDestAndVel() {
   setDestination();
   setVelocity();
   }
   
   void setDestination() {
   this.destX = target.x;
   this.destY = target.y;
   }
   */
  void setVelocity() {
    float dx = target.x - this.x;
    float dy = target.y - this.y;

    float mag = sqrt(dx * dx + dy * dy);
    this.velocityX = (dx/mag) * this.speed;
    this.velocityY = (dy/mag) * this.speed;
  }

  void respawn() {
    switch((int)random(0, 4)) {
    case 0:
      this.x = random(width);
      this.y = -offset;
      break;
    case 1:
      this.x = width + offset;
      this.y = random(height);
      break;
    case 2:
      this.x = random(width);
      this.y = height + offset;
      break;
    case 3:
      this.x = -offset;
      this.y = random(height);
      break;
    }
  }
}