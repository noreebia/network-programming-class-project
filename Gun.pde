class Gun {
  Player owner;

  int gLength;
  int gWidth;
  float reloadingTime;
  int lastFiredTime;
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
    if (isFiring) {
      if (millis() - lastFiredTime >= reloadingTime) {
        bullets.add(new Bullet(owner.x, owner.y, owner.directionModifier, owner.rgb));
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