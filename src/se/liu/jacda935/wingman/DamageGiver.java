package se.liu.jacda935.wingman;

import java.awt.*;

/**
 * Damagergiver are enemies that deal damage to the player.
 * they inhertit from collisionable as the damage is only dealt
 * after the player has collided with the damageiver.
 */

public class DamageGiver extends Collisionable
{

    public DamageGiver(final int leftX, final int rightX, final int upY, final int downY) {
	super(leftX, rightX, upY, downY);
    }

    @Override public CollisionType getCollisionCategory(boolean left, boolean right, boolean  up, boolean down, Player pl)  {
	return CollisionType.DAMAGE_GIVER;
    }

    @Override public Color getObjColor() {
	return Color.BLACK;
    }
}
