class Enemy {
  PVector location = new PVector();
  PVector velocity = new PVector();
  int radius = 10;
  int offset = radius + 50;
  float speed = 1.5;

  Player target;

  Enemy(float x, float y, Player target) {
    location.x = x;
    location.y = y;
    this.target = target;
    setVelocity();
  }

  Enemy(float x, float y) {
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

  void setVelocity() {
    velocity.set(target.location.x - this.location.x, target.location.y - this.location.y);
    velocity.normalize();
  }

  void move() {
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