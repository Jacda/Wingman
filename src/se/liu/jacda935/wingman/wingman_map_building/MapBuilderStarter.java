package se.liu.jacda935.wingman.wingman_map_building;

public class MapBuilderStarter
{
    /**
     * This class intitalizes the whole game.
     * @param arg
     */
    public static void main (String[] arg){

	Layout layout = new Layout();
	layout.loadMap(1);
	Display ds = new Display(layout);
	ds.show();
    }
}
