package se.liu.jacda935.wingman;

/**
 * This class is usede to create the player object.
 * This object goes through a lot of positional changes
 * depending on what is happening in the game.
 * Most functioins are made to calculate what the position
 * should be next tick and is called every tick på the Operator class.
 */

public class Player
{
    private int posX, posY;
    private int velocity = 0;
    private int health = 3;
    private int speed = 3;
    private int airSpeed = speed * 2;
    private boolean inAir = false;
    private boolean ascending;
    private boolean airLeft, airRight;
    private boolean moveRight, moveLeft;
    private boolean chargingJump;
    private int chargeVelocity;
    private int jumpTickCount;
    private int tickCounter;


    public Player(final int posX, final int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public boolean checkDead(){
        final int minHP = 0;
        if (health <= minHP){
            return true;
        }
        else return false;
    }

    public int getWidth(){
        final int width = 40;
        return width;
    }

    public int getHeight() {
        if (chargingJump && !inAir)
        {
            final int chargingSize = 27;
            return chargingSize;
        }
        final int normalSize = 40;
        return normalSize;
    }
    public void passPlayerTick(){
        tickCounter++;
        checkJump();
        changeVelocity();
    }

    private void startJump(){
        velocity = chargeVelocity;
        inAir = true;
        ascending = true;
        setAirTrajectory();
        chargeVelocity = 0;
    }
    private void setAirTrajectory() {
        if (moveLeft) {
            airLeft = true;
        } else if (moveRight) {
            airRight = true;
        }
    }
    private void chargeJump(){
        final int maxChargingVelocity = 17;
        if(chargeVelocity >= maxChargingVelocity){
            chargingJump = false;
        }
        else{
            if (!inAir){
                jumpTickCount++;
            }
            final int plusOneChargeTickCounter = 3;
            if(jumpTickCount % plusOneChargeTickCounter == 0){
                chargeVelocity++;
                jumpTickCount = 0;
            }
        }
    }
    public void stopJump(){
        velocity = 0;
        inAir = false;
        stopAirTrajectory();
    }
    private void stopAirTrajectory(){
        airRight = false;
        airLeft = false;
    }
    private void checkJump(){
        if(!chargingJump && chargeVelocity > 0){
            startJump();
        }
        else if(chargingJump && !inAir) {
            chargeJump();
        }
        else horizontalMove();
    }
    private void horizontalMove(){
        if(!inAir){
            if(moveLeft){
                posX -= speed;
            }
            if(moveRight) {
                posX += speed;
            }
        }
        else{
            if(airLeft){
                posX -= airSpeed;
            }
            if(airRight) {
                posX += airSpeed;
            }
        }
    }
    public void reverseAirTrajectory(){
        if(airLeft){
            airRight = true;
            airLeft = false;
            posX += airSpeed;
        }
        else if(airRight){
            airLeft = true;
            airRight = false;
            posX -= airSpeed;
        }
    }

    private void changeVelocity(){
        if(inAir){
            posY -= velocity;
            final int velocityIncreaseTickInterval = 2;
            if (tickCounter % velocityIncreaseTickInterval == 0) {
                velocity -= 1;
                tickCounter = 0;
            }
            checkAscension();
            controlVelocityLimit();
        }
    }
    private void checkAscension(){
        if(ascending && velocity < 0){
            ascending = false;
        }
    }

    public void checkGravity(){
        posY += 1;
        inAir = true;
        setAirTrajectory();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(final int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(final int posY) {
        this.posY = posY;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(final int velocity) {
        this.velocity = velocity;
    }

    public boolean isInAir() {
        return inAir;
    }

    public boolean isAscending() {
        return ascending;
    }


    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(final boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(final boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isAirLeft() {
        return airLeft;
    }

    public void setAirLeft(final boolean airLeft) {
        this.airLeft = airLeft;
    }

    public boolean isAirRight() {
        return airRight;
    }

    public void setAirRight(final boolean airRight) {
        this.airRight = airRight;
    }

    public boolean isChargingJump() {
        return chargingJump && !inAir;
    }

    public void setChargingJump(final boolean chargingJump) {
        this.chargingJump = chargingJump;
    }

    public void controlVelocityLimit(){
        final int maxFallingSpeed = -20;
        if(velocity < maxFallingSpeed){
            velocity = maxFallingSpeed;
        }
    }
}
