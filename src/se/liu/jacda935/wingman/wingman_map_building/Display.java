package se.liu.jacda935.wingman.wingman_map_building;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import java.io.*;
/**
 * The display class handles the initialization of the window used to Edit.
 * It is also used to initialize what keys are can be used in the program
 * as well as their function.
 * Furthermore it controlls all of the details of the window.
 */

public class Display
{

    private JFrame frame;
    private Layout layout;
    private boolean firstTap, secondTap, creatingTerrain, removeTerrain, creatingCoin, creatingDamageGiver;
    private int firstCordX, firstCordY;
    private int secondCordX, secondCordY;
    private int currentMap = 1;

    public Display(Layout layout) {
	this.layout = layout;
	frame = new JFrame("Wingman Map Builder");
    }

    public void show() {
	initializeInput();
	DisplayComponent displayComp = new DisplayComponent(layout);
	layout.addBoardListener(displayComp);
	frame.setPreferredSize(new Dimension(1100, 800));
	frame.setLayout(new BorderLayout());
	frame.add(displayComp, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);



    }
    private void initializeInput(){
	JComponent pane = frame.getRootPane();
	InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

	in.put(KeyStroke.getKeyStroke("pressed Q"), "q");
	in.put(KeyStroke.getKeyStroke("pressed Z"), "z");
	in.put(KeyStroke.getKeyStroke("pressed E"), "e");
	in.put(KeyStroke.getKeyStroke("pressed S"), "s");
	in.put(KeyStroke.getKeyStroke("pressed D"), "d");
	in.put(KeyStroke.getKeyStroke("pressed A"), "a");
	in.put(KeyStroke.getKeyStroke("pressed C"), "c");
	in.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "esc");

	ActionMap act = pane.getActionMap();
	act.put("q", new CreateTerrain());
	act.put("z", new CreateDamageGiver());
	act.put("c", new CreateCoin());
	act.put("e", new RemoveTerrain());
	act.put("s", new SaveFile());
	act.put("d", new NextMap());
	act.put("a", new PrevMap());

	act.put("esc", new EscapeExit());

	frame.addMouseListener(new MouseAd());
    }
    private class MouseAd extends MouseAdapter
    {
	@Override public void mousePressed(MouseEvent e){
	    final int xAdjustment = -8;
	    final int yAdjustment = -30;
	    System.out.println((e.getX() + xAdjustment) + "," + (e.getY() + yAdjustment));
	    if(creatingTerrain) {
		if (!firstTap && !secondTap) {
		    firstCordX = e.getX();
		    firstCordY = e.getY();
		    firstTap = true;
		} else if (firstTap && !secondTap) {
		    secondCordX = e.getX();
		    secondCordY = e.getY();
		    secondTap = true;
		}
		if (firstTap && secondTap) {
		    layout.addTerrain(firstCordX + xAdjustment, firstCordY + yAdjustment, secondCordX + xAdjustment, secondCordY + yAdjustment);
		    firstTap = false;
		    secondTap = false;
		    creatingTerrain = false;
		}
	    }
	    else if(creatingCoin){
		layout.addCoin(e.getX() + xAdjustment, e.getY() + yAdjustment);
		creatingCoin = false;
	    }
	    else if(creatingDamageGiver){
		layout.addDamageGiver(e.getX() + xAdjustment, e.getY() + yAdjustment);
		creatingDamageGiver = false;
	    }
	    else if(removeTerrain){
		layout.removeTerrain(e.getX() + xAdjustment, e.getY() + yAdjustment);
		removeTerrain = false;
	    }

	}
    }
    private class CreateTerrain extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    creatingTerrain = true;
	}
    }
    private class CreateCoin extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    creatingCoin = true;
	}
    }
    private class CreateDamageGiver extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    creatingDamageGiver = true;
	}
    }
    private class RemoveTerrain extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    removeTerrain = true;
	}
    }
    private class SaveFile extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e) {
	    /* saves the maps into the txt files*/

	    File map = layout.getMapToSave();
	    System.out.println(map);
	    try {
		FileWriter wr = new FileWriter(map);
		for (TerrainCreate tr : layout.getTerrainCreated()) {
		    wr.write(tr.getLeftX() + "," + tr.getRightX() + "," + tr.getUpY() + "," + tr.getDownY() + "," + tr.getTerrainType() +
			     "," + currentMap);
		    wr.write("\n");
		}
		wr.flush();
		wr.close();
	    } catch (IOException s) {
		s.printStackTrace();
		System.out.println("Someting went wrong when the files where loaded...");
		int exitCode = 0;
		System.exit(exitCode);
	    }
	}
    }
    private class NextMap extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    if (currentMap < 10) {
		currentMap += 1;
	    }
	    layout.loadMap(currentMap);
	}
    }
    private class PrevMap extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    if (currentMap > 1) {
		currentMap -= 1;
	    }
	    layout.loadMap(currentMap);
	}
    }
    private class EscapeExit extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    System.exit(0);
	}
    }
}
