package se.liu.jacda935.wingman;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * This class is the main proccessing program for the interactions of the player.
 * It helps communication between relevant classes and saves relevant iformation.
 * It has the fucntions that make the game seem like a game by having fucktions
 * that are performed every tick as well as notifying listeners to update the
 * visuals.
 * It also loads relevant files and has information about the player during
 * certain circumstances and what level is in play.
 */

public class Operator
{
    static private final int TOP_LEFT_X_ID = 0;
    static private final int TOP_LEFT_Y_ID = 1;
    static private final int BOT_RIGHT_X_ID = 2;
    static private final int BOT_RIGHT_Y_ID = 3;
    static private final int TERRAIN_TYPE_ID = 4;
    static private final int LEVEL_NUMBER_ID = 5;
    private List<List<List<Integer>>> maps = new ArrayList<>();
    private int screenHeight = 800;
    private int startPosY = 600;
    private int startPosX = 600;
    private int level = 1;
    private int coin = 0;
    private List<Collisionable> collisionables = new ArrayList<>();
    private List<Collisionable> removedObjects = new ArrayList<>();
    private Player player;
    private List<BoardListener> boardListeners = new ArrayList<>();

    public Operator() {
	this.player = new Player(startPosX, startPosY);
    }

    public Player getPlayer() {
	return player;
    }

    public List<Collisionable> getCollisionables() {
	return collisionables;
    }

    public void createTerrain(int leftX, int rightX, int upY, int downY) {
	collisionables.add(new Terrain(leftX, rightX, upY, downY));
    }

    /**
     * the tick functions calls on all the necessary checks and
     * changes that are supposed to be done every tick of the game
     */
    public void tick() {
	player.passPlayerTick();
	beginCollisionHandler();
	if (!player.isInAir()) {
	    player.checkGravity();
	    beginCollisionHandler();
	}
	changeScreen();
	if (player.checkDead()) {
	    reloadGame();
	}
	notifyListeners();

    }

    public void changeScreen() {
	/**
	 * changes level depending on if the player has cleard a level
	 * or fallen down to one.
	 */
	int minHeight = 0;
	if (player.getPosY() < minHeight) {
	    player.setPosY(screenHeight);
	    level += 1;
	    changeLevel();
	} else if (player.getPosY() > screenHeight) {
	    player.setPosY(minHeight);
	    level -= 1;
	    changeLevel();
	}

    }


    public void goLeft() {
	player.setMoveLeft(true);
    }

    public void goRight() {
	player.setMoveRight(true);

    }

    public void stopLeft() {
	player.setMoveLeft(false);

    }

    public void stopRight() {
	player.setMoveRight(false);
    }

    /**
     * start the chain of collision checks
     */
    public void beginCollisionHandler() {
	Collisionable removeToBe = null;
	for (Collisionable cl : collisionables) {
	    switch (getCollisionCheck(cl)) {
		case GROUND:
		    hitGround(cl);
		    break;
		case ROOF:
		    hitRoof(cl);
		    break;
		case WALL:
		    player.reverseAirTrajectory();
		    break;
		case COIN:
		    collectCoin(cl);
		    removeToBe = cl;
		    break;
		case DAMAGE_GIVER:
		    takeDamage(cl);
		    removeToBe = cl;
		    break;
		default:
		    break;
	    }
	}
	if (removeToBe != null) {
	    collisionables.remove(removeToBe);
	}
    }

    /**
     * mathematical functions to check exactly which side of
     * the player tha has collided
     * @param cl
     * @return
     */

    public CollisionType getCollisionCheck(Collisionable cl) {

	boolean down, up, right, left;
	down = up = right = left = false;
	if (cl.getLeftX() < (player.getPosX() + player.getHeight()) && (cl.getRightX() > player.getPosX())) {
	    if (cl.getUpY() < player.getPosY() && cl.getDownY() > player.getPosY()) {
		down = true;
	    }
	    if (cl.getUpY() < (player.getPosY() - player.getHeight()) && cl.getDownY() > (player.getPosY() - player.getHeight())) {
		up = true;
	    }
	}
	if (cl.getUpY() < player.getPosY() && cl.getDownY() > player.getPosY() - player.getHeight()) {
	    if (cl.getLeftX() < (player.getPosX() + player.getHeight()) && (cl.getRightX() > (player.getPosX() + player.getHeight()))) {
		right = true;
	    }
	    if (cl.getLeftX() < (player.getPosX()) && (cl.getRightX() > (player.getPosX()))) {
		left = true;
	    }
	}
	if (down || up || right || left) {
	    return cl.getCollisionCategory(left, right, up, down, player);
	}
	return CollisionType.AIR;
    }

    public void hitGround(Collisionable cl) {
	player.setPosY(cl.getUpY());
	player.stopJump();
    }

    public void hitRoof(Collisionable cl) {
	player.setPosY(cl.getDownY() + player.getHeight());
	player.setVelocity(0);
    }

    public void collectCoin(Collisionable cl) {
	coin++;
	removedObjects.add(cl);
    }

    public void takeDamage(Collisionable cl) {
	player.setHealth(player.getHealth() - 1);
	removedObjects.add(cl);
    }

    /**
     * makes sure coins and damagegiver that have dissapeared
     * is still gone when the map is loaded again
     */
    public boolean takenObject(int leftX, int upY, int mapLevel) {
	for (Collisionable cl : removedObjects) {
	    if (cl.leftX == leftX && cl.rightX == upY && mapLevel == level) {
		return true;
	    }
	}
	return false;
    }

    public void chargeJump() {
	player.setChargingJump(true);
    }

    public void releaseJump() {
	player.setChargingJump(false);
    }

    public boolean jumpAnimation() {
	return player.isChargingJump() && !player.isInAir();
    }

    /**
     * reloads the game incase the player dies.
     */
    public void reloadGame() {
	level = 1;
	coin = 0;
	final int startHealth = 3;
	player.setHealth(startHealth);
	player.setPosX(startPosX);
	player.setPosY(startPosY);
	final int zeroVelocity = 0;
	player.setVelocity(zeroVelocity);
	player.setAirLeft(false);
	player.setAirRight(false);
	removedObjects.clear();
	changeLevel();
    }

    public void addBoardListener(BoardListener bl) {
	boardListeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener bl : boardListeners) {
	    bl.changeBoard();
	}
    }

    /**
     * changes the level that is show on the screen by adding
     * the saved positions of the collisionable objects.
     */
    public void changeLevel(){
	collisionables.clear();
	int indexLevel = level - 1;
	List<List<Integer>> replacingMap = maps.get(indexLevel);
	for(List<Integer> mapInformation : replacingMap){
	    final int terrain = 0;
	    final int coin = 1;
	    final int damageGiver = 2;

	    int terrainTypeID = mapInformation.get(TERRAIN_TYPE_ID);
	    if (terrainTypeID == terrain){
		collisionables.add(new Terrain(mapInformation.get(TOP_LEFT_X_ID), mapInformation.get(TOP_LEFT_Y_ID), mapInformation.get(BOT_RIGHT_X_ID), mapInformation.get(BOT_RIGHT_Y_ID)));
	    }
	    else if (terrainTypeID == coin && !takenObject(mapInformation.get(TOP_LEFT_X_ID), mapInformation.get(TOP_LEFT_Y_ID), mapInformation.get(LEVEL_NUMBER_ID))){
		collisionables.add(new Coin(mapInformation.get(TOP_LEFT_X_ID), mapInformation.get(TOP_LEFT_Y_ID), mapInformation.get(BOT_RIGHT_X_ID), mapInformation.get(BOT_RIGHT_Y_ID)));
	    }
	    else if (terrainTypeID == damageGiver && !takenObject(mapInformation.get(TOP_LEFT_X_ID), mapInformation.get(TOP_LEFT_Y_ID), mapInformation.get(LEVEL_NUMBER_ID))){
		collisionables.add(new DamageGiver(mapInformation.get(TOP_LEFT_X_ID), mapInformation.get(TOP_LEFT_Y_ID), mapInformation.get(BOT_RIGHT_X_ID), mapInformation.get(BOT_RIGHT_Y_ID)));
	    }
	}
    }
    public int getCoin() {
	return coin;
    }

    public int getHealth() {
	return player.getHealth();
    }

    public int getSize() {
	return player.getHeight();
    }

    public int getScreenHeight() {
	return screenHeight;
    }

    /**
     * loads in all the level files
     */
    public void loadLevels() {
	int amountOfMaps = new File("resources" + File.separator + "maps").listFiles().length;
	for (int i = 0; i < amountOfMaps; i++) {
	    int mapNum = i + 1;
	    String filePath = "maps" + File.separator + "map" + mapNum + ".txt";
	    URL map = ClassLoader.getSystemResource(filePath);
	    maps.add(organizeLevelInformation(map, filePath));
	}
    }

    /**
     * saves all the information in nestled lists so that
     * all the information from the txt files only has to
     * be loaded once the game is started.
     */
    private List<List<Integer>> organizeLevelInformation(URL map, String filePath) {
	List<List<Integer>> mapInformation = new ArrayList<>();
	try  {
	    final BufferedReader reader = new BufferedReader(new InputStreamReader(map.openStream()));
	    String line = reader.readLine();
	    while (line != null) {
		String[] res = line.split("[,]", 0);
		List<Integer> terrainInformation = new ArrayList<>();
		terrainInformation.add(parseInt(res[TOP_LEFT_X_ID]));
		terrainInformation.add(parseInt(res[TOP_LEFT_Y_ID]));
		terrainInformation.add(parseInt(res[BOT_RIGHT_X_ID]));
		terrainInformation.add(parseInt(res[BOT_RIGHT_Y_ID]));
		terrainInformation.add(parseInt(res[TERRAIN_TYPE_ID]));
		terrainInformation.add(parseInt(res[LEVEL_NUMBER_ID]));
		mapInformation.add(terrainInformation);
		line = reader.readLine();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    System.out.println("Someting went wrong when the files where loaded...");
	    int exitCode = 0;
	    System.exit(exitCode);
	}
	return mapInformation;
    }
}
