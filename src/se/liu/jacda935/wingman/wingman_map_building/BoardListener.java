package se.liu.jacda935.wingman.wingman_map_building;

/**
 * This interface is used as a queue for the program to refresh visuals.
 * It's mainly called upon the the Layout class.
 */
public interface BoardListener
{
    public void changeBoard();
}
