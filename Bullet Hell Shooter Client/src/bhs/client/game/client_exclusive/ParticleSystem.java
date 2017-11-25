package bhs.client.game.client_exclusive;

import game.protocol.GameObject;
import processing.core.PApplet;

public class ParticleSystem {

	PApplet world;
	Particle particles[] = new Particle[8];

	boolean active = false;
	short r, g, b;

	float pointOfExplosionX;
	float pointOfExplosionY;

	float deactivationRange = 300;

	float particleStraightSpeed = 10;
	float particleDiagonalSpeed = (float) (particleStraightSpeed / Math.sqrt(2));

	public ParticleSystem(PApplet world) {
		this.world = world;
		int i;
		for (i = 0; i < 8; i++) {
			particles[i] = new Particle(world);
			setVelocityOfParticle(particles[i], i);
		}
	}

	public void setVelocityOfParticle(Particle particle, int direction) {
		switch (direction) {
		case 0:
			particle.setSpeedY(-particleStraightSpeed);
			break;
		case 1:
			particle.setSpeedX(particleDiagonalSpeed);
			particle.setSpeedY(-particleDiagonalSpeed);
			break;
		case 2:
			particle.setSpeedX(particleStraightSpeed);
			break;
		case 3:
			particle.setSpeedX(particleDiagonalSpeed);
			particle.setSpeedY(particleDiagonalSpeed);
			break;
		case 4:
			particle.setSpeedY(particleDiagonalSpeed);
			break;
		case 5:
			particle.setSpeedX(-particleDiagonalSpeed);
			particle.setSpeedY(particleDiagonalSpeed);
			break;
		case 6:
			particle.setSpeedX(-particleStraightSpeed);
			break;
		case 7:
			particle.setSpeedX(-particleDiagonalSpeed);
			particle.setSpeedY(-particleDiagonalSpeed);
			break;
		}
	}

	public void activate(float x, float y, short r, short g, short b) {
		if (!isActive()) {
			pointOfExplosionX = x;
			pointOfExplosionY = y;
			this.r = r;
			this.g = g;
			this.b = b;
			for (Particle p : particles) {
				p.activate(x, y);
			}
		}
		this.active = true;
	}

	public void run() {
		if (isActive()) {
			boolean isEveryParticleDeactivated = true;
			world.stroke(r, g, b);
			world.fill(r, g, b);
			for (Particle p : particles) {
				if (!isParticleOutOfRange(p)) {
					p.run();
					isEveryParticleDeactivated = false;
				} else {
					p.deactivate();
				}
			}
			if (isEveryParticleDeactivated) {
				this.deactivate();
			}
		}
	}

	public boolean isParticleOutOfRange(Particle p) {
		if (getDistance(p.getX(), p.getY(), pointOfExplosionX, pointOfExplosionY) > deactivationRange) {
			return true;
		}
		return false;
	}

	public double getDistance(float x1, float y1, float x2, float y2) {
		float xDistance = x2 - x1;
		float yDistance = y2 - y1;
		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public boolean isActive() {
		return active;
	}

	public void deactivate() {
		active = false;
	}
}
