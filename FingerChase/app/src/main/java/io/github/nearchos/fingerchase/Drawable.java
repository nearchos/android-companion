package io.github.nearchos.fingerchase;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public interface Drawable {

    int [] COLORS = { Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.YELLOW, Color.CYAN, Color.MAGENTA };

    enum Shape { CIRCLE, TRIANGLE, RECTANGLE_LANDSCAPE, RECTANGLE_PORTRAIT, SQUARE }

    void draw(Canvas canvas, Paint paint);

    void updatePosition();
}