class Bullet {
  float x;
  float y;
  int direction;
  int radius;
  float originalSpeed;
  float diagonalSpeed;
  float speed;
  boolean isActive;
  int[] rgb = new int[3];

  Bullet(float x, float y, int direction, int[] rgb) {
    this.x = x;
    this.y = y;
    this.originalSpeed = 10;
    this.diagonalSpeed = originalSpeed/sqrt(2);
    this.direction = direction;
    this.radius = 15;
    this.rgb = rgb;
    this.isActive = true;
    setSpeed();
  }

  void setSpeed() {
    if (this.direction == 1 || this.direction == 3 || this.direction == 5 || this.direction == 7) {
      this.speed = diagonalSpeed;
    } else {
      this.speed = originalSpeed;
    }
  }

  void display() {
    fill(rgb[0], rgb[1], rgb[2]);
    ellipse(x, y, radius, radius);
  }

  void move() {
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

  void deactivate() {
    this.isActive = false;
  }

  boolean isOutOfMap() {
    if (x<-radius || x>(width+radius) || y<-radius || y>(height+radius)) {
      return true;
    }
    return false;
  }
}