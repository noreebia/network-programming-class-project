package game.protocol;

public class Bullet extends GameObject{
	
	public short direction;
	boolean active = false;
	
	public Bullet(float x, float y, short direction) {
		super(x, y);
		activate();
		this.direction = direction;
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
}
