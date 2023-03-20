package se.liu.jacda935.wingman.wingman_map_building;

/**
 * CoinCreate is used to mimic a creation of the Coin object.
 */

public class CoinCreate extends TerrainCreate
{
    public CoinCreate(final int leftX, final int rightX, final int upY, final int downY) {
	super(leftX, rightX, upY, downY);

    }

    @Override public int getTerrainType() {
	return 1;
    }
}
