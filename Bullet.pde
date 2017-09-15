class Bullet {
  /*
  float x;
   float y;
   float speed;
   */
  PVector location = new PVector();
  PVector velocity = new PVector();
  float speed = 10;

  int direction;

  int radius = 8;
  float originalSpeed = 10;
  float diagonalSpeed = originalSpeed/sqrt(2);
  boolean isActive = true;
  int[] rgb = new int[3];

  Bullet(float x, float y, int direction, int[] rgb) {
    //this.x = x;
    //this.y = y;

    location.x = x;
    location.y = y;
    this.direction = direction;
    this.rgb = rgb;
    //setSpeed();
  }

  /*
  void setSpeed() {
   if (this.direction == 1 || this.direction == 3 || this.direction == 5 || this.direction == 7) {
   this.speed = diagonalSpeed;
   } else {
   this.speed = originalSpeed;
   }
   }
   */

  void run() {
    setVelocity();
    move();
    display();
  }
  void display() {
    fill(rgb[0], rgb[1], rgb[2]);
    ellipse(location.x, location.y, radius*2, radius*2);
  }

  void move() {
    location.add(velocity.x * speed, velocity.y * speed);
  }

  void setVelocity() {
    switch(direction) {
    case 0:
      //y -= speed;
      velocity.set(0, -1);
      break;
    case 1:
      //x += speed;
      //y -= speed;
      velocity.set(1, -1);
      break;
    case 2:
      //x += speed;
      velocity.set(1, 0);
      break;
    case 3:
      //y += speed;
      //x += speed;
      velocity.set(1, 1);
      break;
    case 4:
      //y += speed;
      velocity.set(0, 1);
      break;
    case 5:
      //x -= speed;
      //y += speed;
      velocity.set(-1, 1);
      break;
    case 6:
      //x -= speed;
      velocity.set(-1, 0);
      break;
    case 7:
      //x -= speed;
      //y -= speed;
      velocity.set(-1, -1);
      break;
    }
    velocity.normalize();
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