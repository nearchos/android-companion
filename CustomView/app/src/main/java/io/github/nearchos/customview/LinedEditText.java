package io.github.nearchos.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class LinedEditText extends AppCompatEditText {

    private Paint paint;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(); // create the Paint object and init its style and color
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0x80000000); // semi-transparent black
    }

    /**
     * This is called to draw the LinedEditText
     * @param canvas on which the background is drawn.
     */
    @Override
    protected void onDraw(Canvas canvas) {

        // number of lines to be drawn
        int numOfLines = getHeight() / getLineHeight();
        if(getLineCount() > numOfLines) {
            // this happens when there are more lines accessible when scrolling
            numOfLines = getLineCount();
        }

        // compute the baseline for the first line
        int baseline = getLineBounds(0, null);

        for (int i = 0; i < numOfLines; i++) { // draw each line
            canvas.drawLine(0, baseline, getWidth(), baseline, paint);
            baseline += getLineHeight(); // next line - simply increase baseline
        }

        super.onDraw(canvas); // handle drawing in parent class EditText
    }
}