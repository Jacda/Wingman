package se.liu.jacda935.wingman.wingman_map_building;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;
import java.net.URL;

import static java.lang.Integer.parseInt;

/**
 * This class is the main proccessing program.
 * It helps commpunication between relevant classes and saves relevant iformation.
 * It also loads relevant files.
 */

public class Layout
{

    private List<BoardListener> boardListeners = new ArrayList<>();
    private List<TerrainCreate> terrainCreated = new ArrayList<>();
    private List<TerrainCreate> terrainRemoved = new ArrayList<>();
    private int screenAdjuster =30;
    private int level;



    public List<TerrainCreate> getTerrainCreated(){
	return terrainCreated;
    }
    public void addTerrain(int x1, int y1, int x2, int y2){
	int leftX, rightX, upY, downY;
	if(x1 > x2){
	    rightX = x1;
	    leftX = x2;
	}
	else{
	    rightX = x2;
	    leftX = x1;
	}
	if(y1 > y2){
	    downY = y1;
	    upY = y2;
	}
	else{
	    downY = y2;
	    upY = y1;
	}
	terrainCreated.add(new TerrainCreate(leftX, rightX, upY, downY));

	notifyListeners();
    }
    public void addCoin(int x,int y){
	terrainCreated.add(new CoinCreate(x, x + screenAdjuster, y, y + screenAdjuster));
	notifyListeners();
    }
    public void addDamageGiver(int x,int y){
	terrainCreated.add(new DamageGiverCreate(x, x + screenAdjuster, y, y + screenAdjuster));
	notifyListeners();
    }
    public void removeTerrain(int x, int y){
	for(TerrainCreate tr: terrainCreated){
	    if(tr.getLeftX() < x && tr.getRightX() > x && tr.getUpY() < y && tr.getDownY() > y){
		terrainRemoved.add(tr);
	    }
	}
	for(TerrainCreate terrain: terrainRemoved){
	    terrainCreated.remove(terrain);
	}
	terrainRemoved.clear();
	notifyListeners();
    }
    public int getLevel(){
	return level;
    }
    public void loadMap(int mapNumber) {
	level = mapNumber;
	terrainCreated.clear();
	String filePath = "maps" + File.separator + "map" + level + ".txt";
	System.out.println(filePath);
	URL map = ClassLoader.getSystemResource(filePath);
	try (final BufferedReader reader = new BufferedReader(new InputStreamReader(map.openStream()))) {
	    final int topLeftX = 0;
	    final int topLeftY = 1;
	    final int botRightX = 2;
	    final int botRightY = 3;
	    final int terrainType = 4;

	    final int terrain = 0;
	    final int coin = 1;
	    final int damageGiver = 2;
	    String line = reader.readLine();
	    while (line != null) {
		System.out.println(line);
		String[] res = line.split("[,]", 0);
		int terrainTypeID = parseInt(res[terrainType]);
		if (terrainTypeID == terrain){
		    terrainCreated.add(new TerrainCreate(parseInt(res[ topLeftX]), parseInt(res[topLeftY]), parseInt(res[botRightX]), parseInt(res[botRightY])));

		}
		else if(terrainTypeID == coin) {
		    terrainCreated.add(new CoinCreate(parseInt(res[ topLeftX]), parseInt(res[topLeftY]), parseInt(res[botRightX]), parseInt(res[botRightY])));
		}
		else if(terrainTypeID == damageGiver){
		    terrainCreated.add(new DamageGiverCreate(parseInt(res[ topLeftX]), parseInt(res[topLeftY]), parseInt(res[botRightX]), parseInt(res[botRightY])));
		}
		line = reader.readLine();
	    }
	}
	catch(IOException e){
	    e.printStackTrace();
	    System.out.println("Someting went wrong when the files where loaded...");
	    int exitCode = 0;
	    System.exit(exitCode);
	}

	notifyListeners();
    }
    public File getMapToSave(){
	return new File("resources"+ File.separator + "maps"+ File.separator + "map" + level +".txt");
    }
    public void addBoardListener(BoardListener bl){
	boardListeners.add(bl);
    }

    private void notifyListeners(){
	for(BoardListener bl: boardListeners){
	    bl.changeBoard();
	}
    }
}
