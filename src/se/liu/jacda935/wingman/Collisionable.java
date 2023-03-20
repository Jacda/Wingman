package se.liu.jacda935.wingman;

import java.awt.*;

/**
 * This is an abstract class for all the objects that the player can interact with.
 * All collisionable objects the player can interact with inherits from this class.
 */

public abstract class Collisionable
{

    protected int leftX;
    protected int rightX;
    protected int upY;
    protected int downY;

    protected Collisionable(final int leftX, final int rightX, final int upY, final int downY) {
	this.leftX = leftX;
	this.rightX = rightX;
	this.upY = upY;
	this.downY = downY;
    }
    public abstract Color getObjColor();

    public int getLeftX() {
	return leftX;
    }

    public int getRightX() {
	return rightX;
    }

    public int getUpY() {
	return upY;
    }

    public int getDownY() {
	return downY;
    }
    public abstract CollisionType getCollisionCategory(boolean left, boolean right, boolean  up, boolean down, Player pl);
}
