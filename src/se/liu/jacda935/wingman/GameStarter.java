package se.liu.jacda935.wingman;

public class GameStarter
{
    /**
     * This class intitalizes the whole game through an operator
     * object and display object.
     * @param arg
     */
    public static void main (String[] arg){
        /*this class initializes the gmae*/
        Operator operator = new Operator();
        operator.loadLevels();
        operator.changeLevel();
        Display ds = new Display(operator);
        ds.show();
    }
}
