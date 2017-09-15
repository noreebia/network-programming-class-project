class Enemy {
  Player target;

/*
  float x;
  float y;
  float velocityX;
  float velocityY;
  */
  PVector location = new PVector();
  PVector velocity = new PVector();

  int radius = 10;
  int offset = radius + 50;
  float speed = 1.5;

  Enemy(float x, float y, Player target) {
    /*
    this.x = x;
    this.y = y;
    */
    location.x = x;
    location.y = y;
    
    this.target = target;
    setVelocity();
  }

  Enemy(float x, float y) {
    /*
    this.x = x;
    this.y = y;
    */
    location.x = x;
    location.y = y;
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
    /*
    this.x += velocityX;
    this.y += velocityY;
    */
    location.add(velocity.x * speed, velocity.y * speed);
  }

  void display() {
    fill(255, 128, 0);
    ellipse(location.x, location.y, radius*2, radius*2);
  }

  boolean isOutOfMap() {
    if (location.x<-offset || location.x>(width+offset) || location.y<-offset || location.y>(height+offset)) {
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
    /*
    float dx = target.x - this.x;
    float dy = target.y - this.y;

    float mag = sqrt(dx * dx + dy * dy);
    this.velocityX = (dx/mag) * this.speed;
    this.velocityY = (dy/mag) * this.speed;
    */
    velocity.set(target.location.x - this.location.x, target.location.y - this.location.y);
    velocity.normalize();
  }

  void respawn() {
    switch((int)random(0, 4)) {
    case 0:
      this.location.x = random(width);
      this.location.y = -offset;
      break;
    case 1:
      this.location.x = width + offset;
      this.location.y = random(height);
      break;
    case 2:
      this.location.x = random(width);
      this.location.y = height + offset;
      break;
    case 3:
      this.location.x = -offset;
      this.location.y = random(height);
      break;
    }
  }
}