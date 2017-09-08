class Enemy {
  float x;
  float y;
  int destX;
  int destY;
  int radius;
  float velocityX;
  float velocityY;
  float speed;
  // ArrayList<Player> targets = new ArrayList<Player>();
  Player target;

  Enemy(int x, int y, Player target) {
    this.x = x;
    this.y = y;
    this.radius = 10;
    this.target = target;
    this.speed = 5;
    setVelocity();
  }

  /*
  void addTarget(Player target) {
   this.targets.add(target);
   }
   */
   
  void run() {
    if (isOutOfMap()) {
      setVelocity();
    }
    move();
    display();
    //print("10");
  }

  void move() {
    this.x += velocityX;
    this.y += velocityY;
  }

  void display() {
    ellipse(x, y, radius, radius);
  }

  boolean isOutOfMap() {
    if (x<-radius && x>(width+radius) && y<-radius && y>(height+radius)) {
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

    float mag = sqrt(dx * dx * dy * dy);
    this.velocityX = (dx/mag) * this.speed;
    this.velocityY = (dy/mag) * this.speed;
  }
}