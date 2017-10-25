class Gun {
  Player owner;
  boolean isFiring;
  int gLength = 9;
  int gWidth = 4;
  int lastFiredTime = 0;
  float reloadingTime = 100;
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();

  Gun(Player owner) {
    this.owner = owner;
  }

  void run() {
    fire();
    runBullets();
    print(bullets.size() + " ");
  }

  void display() {
    rect(-gWidth/2, -owner.radius, gWidth, -gLength);
  }

  void fire() {
    if (isFiring) {
      float bulletSpawnX = (float)(owner.location.x +  1.5 * Math.sin(owner.directionModifier * PI/4) * gLength);
      float bulletSpawnY = (float)(owner.location.y -  1.5 * Math.cos(owner.directionModifier * PI/4) * gLength);
      if (millis() - lastFiredTime >= reloadingTime) {
        bullets.add(new Bullet(bulletSpawnX, bulletSpawnY, owner.directionModifier, owner.rgb));
        lastFiredTime = millis();
      }
    }
  }

  void runBullets() {
    for (int i=0; i<bullets.size(); i++) {
      bullets.get(i).run();
      if (bullets.get(i).isOutOfMap() || !bullets.get(i).isActive) {
        bullets.remove(i);
      }
    }
  }

  void shouldFire(boolean value) {
    this.isFiring = value;
  }
}