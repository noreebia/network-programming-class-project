package model;

import processing.core.PApplet;

public class User extends GameObject {
	PApplet world;
	float speed = 4;
	int directionModifier = 0;
	double angle;
	boolean[] moving = new boolean[4];
	boolean[] facing = new boolean[4];

	float originalSpeed = 4;
	float diagonalSpeed = (float) (originalSpeed / Math.sqrt(2));
	
	public User(PApplet world){
		this.world = world;
	}

	public void run() {
		adjustSpeed();
		move();
		setDirection();
		//gun.run();
		display();
	}

	void display() {
		world.pushMatrix();
		world.translate(x, y);
		setAngle();
		world.rotate((float) angle);
		world.fill(rgb[0], rgb[1], rgb[2]);
		world.ellipse(0, 0, size * 2, size * 2);
	//	gun.display();
		world.popMatrix();
	}

	public void adjustSpeed() {
		int count = 0;
		int i;
		for (i = 0; i < 4; i++) {
			if (moving[i]) {
				count++;
			}
		}
		if (count >= 2) {
			setSpeed(diagonalSpeed);
		} else {
			setSpeed(originalSpeed);
		}
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void move() {
		if (isMoving(0)) {
			this.y = this.y - speed;
		}
		if (isMoving(1)) {
			this.x = this.x - speed;
		}
		if (isMoving(2)) {
			this.y = this.y + speed;
		}
		if (isMoving(3)) {
			this.x = this.x + speed;
		}
	}

	public void setAngle() {
		angle = Math.PI / 4 * this.directionModifier;
	}

	public void setDirection() {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (isFacing(i)) {
				count++;
			}
		}
		if (count >= 2) {
			if (isFacing(0) && isFacing(3)) {
				setDirectionModifier(1);
			} else if (isFacing(2) && isFacing(3)) {
				setDirectionModifier(3);
				this.speed = diagonalSpeed;
			} else if (isFacing(2) && isFacing(1)) {
				setDirectionModifier(5);
				this.speed = diagonalSpeed;
			} else if (isFacing(0) && isFacing(1)) {
				setDirectionModifier(7);
				this.speed = diagonalSpeed;
			}
		} else {
			if (isFacing(0)) {
				setDirectionModifier(0);
			} else if (isFacing(2)) {
				setDirectionModifier(4);
			} else if (isFacing(3)) {
				setDirectionModifier(2);
			} else if (isFacing(1)) {
				setDirectionModifier(6);
			}
		}
	}

	public void setDirectionModifier(int value) {
		directionModifier = value;
	}

	public void shouldMove(int direction, boolean val) {
		moving[direction] = val;
	}

	public void shouldFace(int direction, boolean value) {
		facing[direction] = value;
	}

	public boolean isFacing(int direction) {
		return facing[direction];
	}

	public boolean isMoving(int direction) {
		return moving[direction];
	}
}
