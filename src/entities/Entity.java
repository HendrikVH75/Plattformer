package entities;
// Abstract classes can't be used as objects, just to inherit
// methods and variables, that other classes maybe have in
// common

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    // protected variable, so classes that extend this class
    // can use them directly and not by needing an object of
    // this class.
    public Entity(float x, float y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    protected void initHitbox(float x, float y, float width, float height) {

        hitbox = new Rectangle2D.Float(x, y, width, height);

    }

/*    protected void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }*/

    protected void drawHitbox(Graphics g) {
        // for debugging purposes only
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

}
