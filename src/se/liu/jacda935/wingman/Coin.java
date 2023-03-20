package se.liu.jacda935.wingman;

import java.awt.*;

/**
 * Collectable coins spread through out the game.
 * These coins can collide with the player.
 * When a player touches them, they dissapear.
 * They also give the player a coin point.
 */

public class Coin extends Collisionable
{
    public Coin(final int leftX, final int rightX, final int upY, final int downY) {
	super(leftX, rightX, upY, downY);
    }
    @Override public CollisionType getCollisionCategory(boolean left, boolean right, boolean  up, boolean down, Player pl)  {
	return CollisionType.COIN;
    }

    @Override public Color getObjColor() {
	return Color.ORANGE;
    }
}
