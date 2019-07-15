package io.github.nearchos.fingerchase;

import java.util.Random;

/**
 * @author Nearchos
 * Created: 15-Jul-19
 */
public abstract class AbstractDrawable implements Drawable {

    private float cx, cy;
    private float canvasWidth, canvasHeight;
    private float sx, sy;
    private int color;

    private Random random = new Random();

    AbstractDrawable(float cx, float cy, float canvasWidth, float canvasHeight) {
        this.cx = cx;
        this.cy = cy;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.color = Drawable.COLORS[random.nextInt(Drawable.COLORS.length)]; // random color
        this.sx = random.nextFloat() * 10 - 5; // random speed over X
        this.sy = random.nextFloat() * 10 - 5; // random speed over Y
    }

    public float getCx() {
        return cx;
    }

    public float getCy() {
        return cy;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void updatePosition() {
        this.cx = cx + sx;
        this.cy = cy + sy;

        // speed correction when bouncing on the edge of the screen
        if(cx < 0) sx = -sx; // bounced left -> positive X speed
        if(cx > canvasWidth) sx = -sx; // bounced right -> negative X speed
        if(cy < 0) sy = -sy; // bounced top -> positive Y speed
        if(cy > canvasHeight) sy = -sy; // bounced bottom -> negative Y speed
    }
}