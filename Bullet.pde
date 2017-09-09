class Bullet {
  float x;
  float y;
  int direction;
  int radius;
  boolean isLaunched;
  float originalSpeed;
  float diagonalSpeed;
  float speed;

  Bullet(float x, float y, int direction) {
    this.x = x;
    this.y = y;
    this.originalSpeed = 10;
    this.diagonalSpeed = originalSpeed/sqrt(2);
    this.direction = direction;
    this.radius = 15;
    setSpeed();
    isLaunched = false;
  }

  void setSpeed() {
    if (this.direction == 1 || this.direction == 3 || this.direction == 5 || this.direction == 7) {
      this.speed = diagonalSpeed;
    } else {
      this.speed = originalSpeed;
    }
  }

  boolean isOutOfMap() {
    if (x<-radius || x>(width+radius) || y<-radius || y>(height+radius)) {
      return true;
    }
    return false;
  }

  void display() {
    fill(255);
    ellipse(x, y, radius, radius);
  }

  void launch() {
    isLaunched = true;
  }

  void move() {
    if (isLaunched) {
      switch(direction) {
      case 0:
        y -= speed;
        break;
      case 1:
        x += speed;
        y -= speed;
        break;
      case 2:
        x += speed;
        break;
      case 3:
        y += speed;
        x += speed;
        break;
      case 4:
        y += speed;
        break;
      case 5:
        x -= speed;
        y += speed;
        break;
      case 6:
        x -= speed;
        break;
      case 7:
        x -= speed;
        y -= speed;
        break;
      }
    }
  }
}