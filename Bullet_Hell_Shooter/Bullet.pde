class Bullet {
  PVector location = new PVector();
  PVector velocity = new PVector();
  float speed = 10;
  int radius = 8;
  int direction;
  int[] rgb = new int[3];
  boolean isActive = true;

  Bullet(float x, float y, int direction, int[] rgb) {
    location.x = x;
    location.y = y;
    this.direction = direction;
    this.rgb = rgb;
  }

  void run() {
    setVelocity();
    move();
    display();
  }

  void setVelocity() {
    switch(direction) {
    case 0:
      velocity.set(0, -1);
      break;
    case 1:
      velocity.set(1, -1);
      break;
    case 2:
      velocity.set(1, 0);
      break;
    case 3:
      velocity.set(1, 1);
      break;
    case 4:
      velocity.set(0, 1);
      break;
    case 5:
      velocity.set(-1, 1);
      break;
    case 6:
      velocity.set(-1, 0);
      break;
    case 7:
      velocity.set(-1, -1);
      break;
    }
    velocity.normalize();
  }

  void display() {
    fill(rgb[0], rgb[1], rgb[2]);
    ellipse(location.x, location.y, radius*2, radius*2);
  }

  void move() {
    location.add(velocity.x * speed, velocity.y * speed);
  }

  void deactivate() {
    this.isActive = false;
  }

  boolean isOutOfMap() {
    if (location.x<-radius || location.x>(width+radius) || location.y<-radius || location.y>(height+radius)) {
      return true;
    }
    return false;
  }
}