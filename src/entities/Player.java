package entities;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean up, down, left, right, jump;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 1.0f * Game.SCALE; // otherwise different scales = different speeds
    private int[][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE; // where the actual pixels of the sprite starts
    private float yDrawOffset = 4 * Game.SCALE; // where the actual pixels of the sprite starts

    // Jumping and gravity
    private float airSpeed = 0.0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);

        loadAnimations();
        // width and height measured from the actual pixels of the sprite,
        // not the transparent surroundings
        initHitbox(x, y, (int)(20 * Game.SCALE), (int)(27 * Game.SCALE)); // int casting to avoid decimal numbers
    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();

    }

    public void render(Graphics g) {

        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        //drawHitbox(g);

    }

    private void updateAnimationTick() {

        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }

    }


    private void setAnimation() {

        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir){
            if (airSpeed<0){
                playerAction = JUMP;
            } else {
                playerAction = FALLING;
            }
        }

        if (attacking) {
            playerAction = ATTACK_1;
        }

        if (startAni != playerAction) {
            // check if the animation was changed by e.g. button click
            // and reset the index and tick
            resetAniTick();
        }

    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {

        moving = false;

        if (jump){
            jump();
        }
        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }

        if (!inAir){
            if (!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }

        if (inAir) {

            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }

        } else {
            updateXPos(xSpeed);
        }

        moving = true;

    }

    private void jump() {

        if (inAir){
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {

        inAir = false;
        airSpeed = 0;

    }

    private void updateXPos(float xSpeed) {

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {

            hitbox.x += xSpeed;

        } else {

            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);

        }

    }

    private void loadAnimations() {

        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void loadLvlData(int[][] lvlData) {

        this.lvlData = lvlData;
        // check if the player is set in the air on start of level
        if (!IsEntityOnFloor(hitbox, lvlData)){
            inAir = true;
        }

    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
