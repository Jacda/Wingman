package se.liu.jacda935.wingman.wingman_map_building;
/**
 * TerrainCreate is used to mimic a creation of the Terrain object.
 * It is also a parrent class to similiar classes such as damagegivers
 * and coins.
 */

public class TerrainCreate
{
    protected int leftX;
    protected int rightX;
    protected int upY;
    protected int downY;

    public TerrainCreate(final int leftX, final int rightX, final int upY, final int downY) {
	this.leftX = leftX;
	this.rightX = rightX;
	this.upY = upY;
	this.downY = downY;
    }
    public int getTerrainType() {
	return 0;
    }

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
}
