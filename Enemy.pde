class Enemy {
  float x;
  float y;
  int destX;
  int destY;
  int radius;
  int offset;
  float velocityX;
  float velocityY;
  float speed;
  Player target;

  Enemy(float x, float y, Player target) {
    this.x = x;
    this.y = y;
    this.radius = 20;
    this.offset = radius + 50;
    this.speed = 1.5;
    this.target = target;
    setVelocity();
  }

  Enemy(float x, float y) {
    this.x = x;
    this.y = y;
    this.radius = 20;
    this.offset = radius + 50;
    this.speed = 1.5;
  }

  void setTarget(Player target) {
    this.target = target;
  }

  void run() {
    if (isOutsideOfMap()) {
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
    fill(255,128,0);
    ellipse(x, y, radius, radius);
  }

  boolean isOutsideOfMap() {
    if (x<-radius || x>(width+offset) || y<-radius || y>(height+offset)) {
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
}