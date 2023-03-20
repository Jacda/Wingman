package se.liu.jacda935.wingman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The display class handles the initialization of the window used to play.
 * It is also used to initialize what keys are can be used in the program
 * as well as their function.
 * Furthermore it controlls all of the details of the window as well
 * as the Tick frequency.
 */

public class Display
{
    private JFrame frame;
    private Operator operator;
    private boolean moveRight, moveLeft;
    public Display(Operator operator){
	this.operator = operator;
	frame = new JFrame("Wingman");

    }

    public void show(){
	DisplayComponent displayComp = new DisplayComponent(operator);
	operator.addBoardListener(displayComp);
	final int screenWidth = 1100;
	frame.setPreferredSize(new Dimension(screenWidth, operator.getScreenHeight()));
	frame.setLayout(new BorderLayout());
	frame.add(displayComp, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
	initializeInput();

	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		operator.tick();
	    }
	};

	final int tickTimer = 10;
	final Timer clockTimer = new Timer(tickTimer, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
    private void initializeInput(){
	JComponent pane = frame.getRootPane();
	InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

	in.put(KeyStroke.getKeyStroke("pressed SPACE"), "jump");
	in.put(KeyStroke.getKeyStroke("released SPACE"), "stop jump");
	in.put(KeyStroke.getKeyStroke("ESCAPE"), "quit");
	in.put(KeyStroke.getKeyStroke("pressed RIGHT"), "move right");
	in.put(KeyStroke.getKeyStroke("released RIGHT"), "stop right");
	in.put(KeyStroke.getKeyStroke("pressed LEFT"), "move left");
	in.put(KeyStroke.getKeyStroke("released LEFT"), "stop left");



	ActionMap act = pane.getActionMap();
	boolean correctDirection = true;
	act.put("jump", new JumpKey());
	act.put("stop jump", new StopJumpKey());
	act.put("quit", new Quit());
	act.put("move right", new Move(!correctDirection, correctDirection));
	act.put("move left", new Move(correctDirection, !correctDirection));
	act.put("stop right", new Stop(!correctDirection, correctDirection));
	act.put("stop left", new Stop(correctDirection, !correctDirection));

    }
    private class JumpKey extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    operator.chargeJump();
	}
    }
    private class StopJumpKey extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    operator.releaseJump();
	}
    }
    private class Quit extends AbstractAction{
	@Override public void actionPerformed(ActionEvent e){
	    final int exitStatus = 0;
	    System.exit(exitStatus);
	}
    }
    private class Move extends AbstractAction{
	private boolean left;
	private boolean right;
	private Move(boolean left, boolean right){
	    this.left = left;
	    this.right = right;
	}
	@Override public void actionPerformed(ActionEvent e){
	    if (right){
		operator.goRight();
	    } else if (left) {
		operator.goLeft();
	    }
	}
    }
    private class Stop extends AbstractAction{
	private boolean left;
	private boolean right;
	private Stop(boolean left, boolean right){
	    this.left = left;
	    this.right = right;
	}
	@Override public void actionPerformed(ActionEvent e){
	    if (right){
		operator.stopRight();
	    } else if (left) {
		operator.stopLeft();
	    }
	}
    }
}
