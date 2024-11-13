package com.example.a5td4asyncsort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SortView extends View {

    private int[] array;
    private Paint paint = new Paint();
    public SortView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);


    }

    public void setArray(int[] array) {
        this.array = array;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (array == null || paint == null){
            return;
        }

        float width = getWidth();
        float height = getHeight();
        float barWidth = width / array.length;

        for (int i=0;i<array.length;i++){
            float barHeight = (float) ((array[i] / 100.0 ) * height);


            float left = i * barWidth;
            float right = left + barWidth;
            canvas.drawRect(left,height-barHeight,right,height,paint);


        }




    }
}
