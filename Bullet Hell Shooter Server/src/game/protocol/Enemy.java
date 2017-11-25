package game.protocol;

public class Enemy extends GameObject {

	int hp = 3;
	float velocityX = -1;
	float velocityY = -1; 
	float lastKnownX;
	float lastKnownY;
	float speed = (float) 2;
	boolean active = true;
	
	public Enemy(float x, float y) {
		super(x, y);
	}
	
	public void setDestination(float x, float y) {
		float dx = x - this.getX();
		float dy = y - this.getY();
		
		float mag = (float) Math.sqrt( Math.pow(dx, 2) + Math.pow(dy, 2) );
		setVelocityX(dx/mag * speed);
		setVelocityY(dy/mag * speed);
	}
	
	public void moveToDestination() {
		this.move(velocityX, velocityY);
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void getHit() {
		lastKnownX = x;
		lastKnownY = y;
		hp -= 1;
		if(hp <= 0) {
			setXY(-1000, - 1000);
			deactivate();
		}
	}
	
	public float getLastKnownX() {
		return lastKnownX;
	}
	
	public float getLastKnownY() {
		return lastKnownY;
	}
	
	public void activate() {
		active = true;
	}
	
	public void deactivate() {
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
}
