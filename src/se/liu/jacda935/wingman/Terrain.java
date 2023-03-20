package se.liu.jacda935.wingman;


import java.awt.*;

/**
 * The terrain class creates all the terrain that is found in the game.
 * Terrain is the object that is suppodes with the player as a normal
 * phycical object.
 * Dependent of what sides of the player has collided wit the terrain,
 * the terrain calculates exactly what type of collision has happened
 * in order to give back  The collision type.
 */


public class Terrain extends Collisionable
{
    public Terrain(final int leftX, final int rightX, final int upY, final int downY) {
	super(leftX, rightX, upY, downY);
    }

    /**
     * This method handles simple collisions
     */
    @Override public CollisionType getCollisionCategory(boolean left, boolean right, boolean  up, boolean down, Player pl) {
	if(!pl.isInAir() && (right || left)){
	    if(right){
		pl.setPosX(leftX - pl.getHeight());
	    }
	    else{
		pl.setPosX(rightX);
	    }
	}
	else if((up || down) && !(left || right)){
	    if(up){
		return CollisionType.ROOF;
	    }
	    else return CollisionType.GROUND;
	}
	else if (up && down) {
	    return CollisionType.WALL;
	}
	else if (right && left) {
	    if (up) {
		return CollisionType.ROOF;
	    }
	    if (down) {
		return CollisionType.GROUND;
	    }
	}
	else if (getCornerAffected(left, right, up, down)) {
	    return getCornerCollision(left, right, up, down, pl);
	}
	else if(right || left){
	    if(pl.isInAir()) {
		return CollisionType.WALL;
	    }
	}
	return CollisionType.AIR;
    }
    private boolean getCornerAffected(boolean left, boolean right, boolean up, boolean down) {
	return left && up || left && down || right && up || right && down;
    }

    /**
     *this method handles complex collision which all happens if only one corner has collided with terrain
     */
    private CollisionType getCornerCollision(boolean left, boolean right, boolean  up, boolean down, Player pl) {
	if (up) {
	    if (!pl.isAirRight() && !pl.isAirLeft() && pl.isInAir()) {
		return CollisionType.ROOF;
	    } else if (!pl.isAscending() && pl.isInAir()) {
		return CollisionType.WALL;
	    } else if (left) {
		if (pl.isAirRight()) {
		    return CollisionType.ROOF;
		} else if ((Math.abs(downY - (pl.getPosY() - pl.getHeight()))) > (Math.abs(rightX - (pl.getPosX())))) {
		    return CollisionType.WALL;
		} else
		    return CollisionType.ROOF;

	    } else if (right) {
		if (pl.isAirLeft()) {
		    return CollisionType.ROOF;
		} else if ((Math.abs(downY - (pl.getPosY() - pl.getHeight()))) > (Math.abs(leftX - (pl.getPosX() + pl.getHeight())))) {
		    return CollisionType.WALL;
		} else
		    return CollisionType.ROOF;
	    }
	} else if (down) {
	    if (!pl.isAirRight() && !pl.isAirLeft() && pl.isInAir()) {
		return CollisionType.GROUND;
	    } else if (pl.isAscending()) {
		return CollisionType.WALL;

	    } else if (left) {
		if (pl.isAirRight()) {
		    return CollisionType.GROUND;
		} else if (Math.abs(upY - pl.getPosY()) > Math.abs(rightX - pl.getPosX())) {
		    return CollisionType.WALL;
		} else
		    return CollisionType.GROUND;
	    } else if (right) {
		if (pl.isAirLeft()) {
		    return CollisionType.GROUND;
		} else if (Math.abs(upY - pl.getPosY()) > Math.abs(leftX - (pl.getPosX() + pl.getHeight()))) {
		    return CollisionType.WALL;
		} else
		    return CollisionType.GROUND;
	    }
	}
	return CollisionType.AIR;
    }

    @Override public Color getObjColor() {
	return Color.GREEN;
    }
}
