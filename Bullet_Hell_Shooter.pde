Player player;
ArrayList<Enemy> enemies = new ArrayList<Enemy>();

void setup() {
  size(1200, 800);
  strokeWeight(2);
  stroke(255);
  player = new Player(width/2, height/2);
  initializeEnemies();
}

void initializeEnemies() {
  for (int i=0; i< 100; i++) {
    enemies.add(new Enemy(random(0, width), (random(0, height)) ));
    enemies.get(i).setTarget(player);
    enemies.get(i).setVelocity();
  }
}

void draw() {
  background(0);
  player.run();
  for (Enemy e : enemies) {
    e.run();
  }
  handleBulletEnemyCollision();
}

void handleBulletEnemyCollision() {
  int i, k;
  for (i=0; i<player.gun.bullets.size(); i++) {
    for (k=0; k<enemies.size(); k++) {
      if (isBulletCollidingWithEnemy(i, k)) {
        player.gun.bullets.get(i).deactivate();
        enemies.get(k).respawn();
      }
    }
  }
}

boolean isBulletCollidingWithEnemy(int i, int k) {
  float distX = player.gun.bullets.get(i).location.x - enemies.get(k).location.x;
  float distY = player.gun.bullets.get(i).location.y - enemies.get(k).location.y;

  if (getDistanceBetween(distX, distY) <= player.gun.bullets.get(i).radius + enemies.get(k).radius) {
    return true;
  }
  return false;
}

float getDistanceBetween(float x, float y) {
  return sqrt(x * x + y * y);
}


public void keyPressed() {
  if (keyCode == UP) {
    player.shouldFace(0, true);
  }

  if (keyCode == LEFT) {
    player.shouldFace(1, true);
  }

  if (keyCode == DOWN) {
    player.shouldFace(2, true);
  }

  if (keyCode == RIGHT) {
    player.shouldFace(3, true);
  }
  if (key == 'w') {
    player.shouldMove(0, true);
  }
  if (key == 'a') {
    player.shouldMove(1, true);
  }
  if (key == 's') {
    player.shouldMove(2, true);
  }
  if (key == 'd') {
    player.shouldMove(3, true);
  }
  if (key == ' ') {
    player.shoot();
  }
}

public void keyReleased() {
  if (keyCode == UP) {
    player.shouldFace(0, false);
  }

  if (keyCode == LEFT) {
    player.shouldFace(1, false);
  }

  if (keyCode == DOWN) {
    player.shouldFace(2, false);
  }

  if (keyCode == RIGHT) {
    player.shouldFace(3, false);
  }
  if (key == 'w') {
    player.shouldMove(0, false);
  }
  if (key == 'a') {
    player.shouldMove(1, false);
  }
  if (key == 's') {
    player.shouldMove(2, false);
  }
  if (key == 'd') {
    player.shouldMove(3, false);
  }
  if (key == ' ') {
    player.stopShooting();
  }
}