package se.liu.jacda935.wingman;

/**
 * This interface is used as a queue for the program to refresh visuals.
 * It's mainly called upon the the Operator class.
 */

public interface BoardListener
{
    public void changeBoard();
}