package se.liu.jacda935.wingman;

import javax.swing.*;
import java.awt.*;

/**
 * This class visuallizes all of the objects in the screen.
 * The class is used to place all the objects, show their hitbox,
 * show their collor and show score poionts like coins and health.
 */

public class DisplayComponent extends JComponent implements BoardListener
{

    private Operator operator;
    static private final int TEXT_SIZE = 20;

    public DisplayComponent(final Operator operator) {
	this.operator = operator;
    }

    @Override public void paintComponent(Graphics g2d){
	super.paintComponent(g2d);
	for (Collisionable cl: operator.getCollisionables()){
	    g2d.setColor(cl.getObjColor());
	    drawCollisionable(g2d, cl);
	}
	drawPlayer(g2d, operator.getPlayer());
	drawCoinPoints(g2d);
	drawHealth(g2d);
    }

    private void drawCollisionable(Graphics g2d, Collisionable tr){
	g2d.fillRect(tr.getLeftX(), tr.getUpY(),tr.getRightX() - tr.getLeftX(), tr.getDownY()- tr.getUpY());
    }
    private void drawPlayer(Graphics g2d, Player pl){
	g2d.setColor(Color.red);
	if (operator.jumpAnimation()){
	    g2d.fillRect(pl.getPosX(), pl.getPosY() - pl.getHeight(), pl.getWidth(), pl.getHeight());
	}
	else g2d.fillRect(pl.getPosX(), pl.getPosY() -pl.getHeight(), pl.getWidth(), pl.getHeight());
    }
    private void drawCoinPoints(Graphics g2d){
	g2d.setFont(new Font("Arial", Font.PLAIN, TEXT_SIZE));
	g2d.setColor(Color.BLACK);
	final int coinTextY = 30; final int coinTextX = 30;
	g2d.drawString("COINS: " + operator.getCoin(), coinTextX, coinTextY);
    }
    private void drawHealth(Graphics g2d){
	g2d.setFont(new Font("Arial", Font.PLAIN, TEXT_SIZE));
	g2d.setColor(Color.BLACK);
	final int hpTextX = 1000; final int hpTextY = 30;
	g2d.drawString("HP: " + operator.getHealth(), hpTextX, hpTextY);
    }

    public void changeBoard(){
	repaint();
    }
}