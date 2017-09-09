class Gun {
  Player owner;

  int gLength;
  int gWidth;
  float reloadingTime;
  int lastFiredTime;
  int currentTime;
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  boolean isFiring;

  Gun(Player owner) {
    this.owner = owner;
    this.gLength = 9;
    this.gWidth = 4;
    this.lastFiredTime = 0;
    this.reloadingTime = 100;
  }

  void run() {
    fire();
    manageBullets();
    print(bullets.size() + " ");
  }

  void display() {
    rect(-gWidth/2, -owner.radius/2, gWidth, -gLength);
  }

  void fire() {
    /*
    float bulletSpawnX;
    float bulletSpawnY;
    */
    if (isFiring) {
      currentTime = millis();
      if (currentTime - lastFiredTime >= reloadingTime) {
        /*
        bulletSpawnX = (float)(owner.x +   Math.sin(owner.directionModifier * PI/4) * gLength);
        bulletSpawnY = (float)(owner.y -   Math.cos(owner.directionModifier * PI/4) * gLength);
        */
        bullets.add(new Bullet(owner.x, owner.y, owner.directionModifier));
        bullets.get(bullets.size() - 1).launch();
        lastFiredTime = millis();
      }
    }
  }

  void manageBullets() {
    for (int i=0; i<bullets.size(); i++) {
      bullets.get(i).move();
      bullets.get(i).display();

      if (bullets.get(i).isOutOfMap()) {
        bullets.remove(i);
      }
    }
  }

  void shouldFire(boolean value) {
    this.isFiring = value;
  }
}