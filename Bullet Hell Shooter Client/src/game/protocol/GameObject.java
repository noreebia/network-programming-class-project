package game.protocol;

import java.io.Serializable;

public class GameObject implements Serializable{
	
	public float x,y;
	public short[] rgb = {255,255,255};
	public short size = 10;
	
	public GameObject() {
	}
	
	public GameObject(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public GameObject(float x, float y, short r, short g, short b) {
		this.x = x;
		this.y = y;
		setRGB(r,g,b);
	}
	
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setRGB(short r, short g, short b) {
		rgb[0] = r;
		rgb[1] = g;
		rgb[2] = b;
	}
	
	public short[] getRGB() {
		return rgb;
	}
	
	public short getRGB(int i) {
		return rgb[i];
	}
	
	public void setSize(short size) {
		this.size = size;
	}
	
	public short getSize() {
		return size;
	}
	
	public boolean isOutOfMap(int screenWidth, int screenHeight) {
		if(x < -(size * 2 + 100) || x > screenWidth + (size * 2 +100)|| y < -(size*2 + 100) || y > (screenHeight + size*2 + 100)) {
			return true;
		}
		return false;
	}
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
}
