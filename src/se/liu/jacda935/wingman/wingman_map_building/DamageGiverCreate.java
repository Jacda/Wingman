package se.liu.jacda935.wingman.wingman_map_building;

/**
 * DamageGiverCreate is used to mimic a creation of the damage giver object.
 */

public class DamageGiverCreate extends TerrainCreate
{

    public DamageGiverCreate(final int leftX, final int rightX, final int upY, final int downY) {
	super(leftX, rightX, upY, downY);
    }
    @Override public int getTerrainType() {
	return 2;
    }
}
