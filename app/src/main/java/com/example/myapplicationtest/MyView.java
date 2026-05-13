package com.example.myapplicationtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MyView extends View {

    private Bitmap bitmap;
    private ArrayList array_x, array_y;
    private ArrayList array_status;

    public MyView(Context context) {
        super(context);
        bitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.logo
        );
        array_x      = new ArrayList();
        array_y      = new ArrayList();
        array_status = new ArrayList();

        try {
            FileInputStream in = new FileInputStream("test.txt");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String str = reader.readLine();
            reader.close();
        } catch(IOException e) { /* エラー処理 */ }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                array_x.add(new Integer(x));
                array_y.add(new Integer(y));
                array_status.add(new Boolean(false));
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                array_x.add(new Integer(x));
                array_y.add(new Integer(y));
                array_status.add(new Boolean(true));
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                array_x.add(new Integer(x));
                array_y.add(new Integer(y));
                array_status.add(new Boolean(true));

                try {
                    FileOutputStream out = new FileOutputStream("test.txt");
                    PrintWriter writer =
                            new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
                    writer.write("岐阜大");
                    writer.close();
                } catch(IOException e) { /* エラー処理 */ }

                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0, 0,
                canvas.getWidth(), canvas.getHeight()), p);

        canvas.drawBitmap(bitmap, 0, 10, null);

        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);

        for (int i = 1; i < array_status.size(); i++) {
            if ((Boolean) array_status.get(i)) {
                int x1 = (Integer) array_x.get(i - 1);
                int x2 = (Integer) array_x.get(i);
                int y1 = (Integer) array_y.get(i - 1);
                int y2 = (Integer) array_y.get(i);
                canvas.drawLine(x1, y1, x2, y2, p);
            }
        }
    }
}