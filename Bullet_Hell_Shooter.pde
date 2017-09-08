int direction;
Player player;
ArrayList<Enemy> enemies = new ArrayList<Enemy>();

void setup() {
  size(1200, 800);
  player = new Player(width/2, height/2);
  initializeEnemies();
}

void initializeEnemies(){
  for(int i=0; i< 50; i++){
   enemies.add(new Enemy(int(random(0, width)), int(random(0, height)), player)); 
  }
}

void draw() {
  background(255);
  player.run();
  
  for(Enemy e: enemies){
    e.run();
  }
  
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
}