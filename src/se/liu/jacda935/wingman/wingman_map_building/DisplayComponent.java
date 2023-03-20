package se.liu.jacda935.wingman.wingman_map_building;

import javax.swing.*;
import java.awt.*;

/**
 * This class visuallizes all of the objects in the screen.
 * The class is used to place out all the objects and show their hitbox.
 * It also shows which level is currently being viewed
 */

public class DisplayComponent extends JComponent implements BoardListener
{
    private Layout layout;

    public DisplayComponent(Layout la) {
	this.layout = la;
    }

    @Override public void paintComponent(Graphics g2d){
	super.paintComponent(g2d);

	for (TerrainCreate tr: layout.getTerrainCreated()){

	    drawTerrain(g2d, tr);
	}
	int fontSize = 20;
	int levelTextY = 30;
	int levelTextX = 10;
	g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
	g2d.setColor(Color.BLACK);
	g2d.drawString("LEVEL: " + layout.getLevel(), levelTextX, levelTextY);

    }

    private void drawTerrain(Graphics g2d, TerrainCreate tr){
	g2d.setColor(Color.green);
	g2d.fillRect(tr.getLeftX(), tr.getUpY(),tr.getRightX() - tr.getLeftX(), tr.getDownY()- tr.getUpY());
    }

    public void changeBoard() {
	repaint();
    }
}
