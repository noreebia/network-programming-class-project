class Gun {
  Player owner;

  int gLength = 9;
  int gWidth = 4;
  float reloadingTime = 100;
  int lastFiredTime = 0;
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  boolean isFiring;

  Gun(Player owner) {
    this.owner = owner;
  }

  void run() {
    fire();
    manageBullets();
  }

  void display() {
    rect(-gWidth/2, -owner.radius, gWidth, -gLength);
  }

  void fire() {
    float bulletSpawnX;
    float bulletSpawnY;
    if (isFiring) {
      bulletSpawnX = (float)(owner.x +  1.5 * Math.sin(owner.directionModifier * PI/4) * gLength);
      bulletSpawnY = (float)(owner.y -  1.5 * Math.cos(owner.directionModifier * PI/4) * gLength);
      if (millis() - lastFiredTime >= reloadingTime) {
        bullets.add(new Bullet(bulletSpawnX, bulletSpawnY, owner.directionModifier, owner.rgb));
        lastFiredTime = millis();
      }
    }
  }

  void manageBullets() {
    for (int i=0; i<bullets.size(); i++) {
      bullets.get(i).move();
      bullets.get(i).display();

      if (bullets.get(i).isOutOfMap() || !bullets.get(i).isActive) {
        bullets.remove(i);
      }
    }
  }

  void shouldFire(boolean value) {
    this.isFiring = value;
  }
}