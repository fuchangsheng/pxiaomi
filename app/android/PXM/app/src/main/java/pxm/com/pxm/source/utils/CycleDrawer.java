package pxm.com.pxm.source.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dmtec on 2016/7/11.
 * To perform the characteristics of Canvas.
 */
public class CycleDrawer extends View {

    /**
     * Constructor with a Context Object
     *
     * @param context Context Object
     */
    public CycleDrawer(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    /**
     * Constructor with a Context Object and a AttributeSet Object
     *
     * @param context Context Object
     * @param attrs   AttributeSet Object
     */
    public CycleDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    /**
     * Constructor with 3 params
     *
     * @param context  context
     * @param attrs    AttributeSet
     * @param defStyle defStyle
     */
    public CycleDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
    }

    /**
     * the Ring Color
     */
    private final static int RingColor = Color.parseColor("#00C2C4");

    /**
     * the Color of current progress
     */
    private final static int PercentColor = Color.parseColor("#CF1918");

    /**
     * paint
     */
    private Paint paint;
    /**
     * whether it is the first time to initial this drawer
     */
    private boolean init = false;
    /**
     * background
     */
    private static final int BackGround = Color.parseColor("#FF0000");
    /**
     * the color of the part finished
     */
    private static final int CircleColor = Color.YELLOW;

    /**
     * startAngle
     */
    private static final float startAngle = 270;
    /**
     * x point
     */
    private float content_X;
    /**
     * y point
     */
    private float content_Y;
    /**
     * big radius
     */
    private float bigRadius;
    /**
     * small radius
     */
    private float smallRadius;
    /**
     * angel
     */
    private float SweepAngle = 270;
    /**
     * width
     */
    private int width;
    /**
     * height
     */
    private int height;
    /**
     * text
     */
    private String text;

    /**
     * textSize
     */
    private static final int TEXTSIZE = 60;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!init) {
            initPaint();
        }
    }

    /**
     * initialize the Paint
     */
    private void initPaint() {
        setPadding(0, 0, 0, 0);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(RingColor);//ring的颜色
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        bigRadius = ((float) width * 40 / 110);
        smallRadius = (float) width * 40 / 120;
        content_X = (float) width / 2;
        content_Y = (float) height / 2;
        init = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(RingColor);
        Path path = new Path();
        path.reset();
        path.moveTo(content_X, content_Y);
        //draw a circle
        path.addCircle(content_X, content_Y, bigRadius, Path.Direction.CCW);
        path.close();
        canvas.drawPath(path, paint);
        path.reset();
        path.moveTo(content_X, content_Y);
        paint.setColor(Color.WHITE);
        path.addCircle(content_X, content_Y, smallRadius, Path.Direction.CCW);
        path.close();
        canvas.drawPath(path, paint);
//
        getSectorClip(canvas, startAngle);
        path.reset();
        path.moveTo(content_X, content_Y);
        paint.setColor(Color.WHITE);
        path.addCircle(content_X, content_Y, smallRadius, Path.Direction.CCW);
        path.close();
        canvas.drawPath(path, paint);
        if (text != null) {
            paint.setColor(Color.GREEN);
            paint.setFakeBoldText(true);
            paint.setTextSize(TEXTSIZE);
            canvas.drawText(text, width / 4, height / 2, paint);
        }

    }


    public void setText(String text) {
        this.text = text;
    }

    /**
     * 返回一个扇形的剪裁区
     *
     * @param canvas     //画笔
     * @param startAngle //起始角度
     */
    private void getSectorClip(Canvas canvas, float startAngle) {
        paint.setColor(PercentColor);//进度的颜色
        Path path = new Path();
        // 下面是获得一个三角形的剪裁区
        path.moveTo(content_X, content_Y); // 圆心
        path.lineTo(
                (float) (content_X + bigRadius * Math.cos(startAngle * Math.PI / 180)), // 起始点角度在圆上对应的横坐标

                (float) (content_Y + bigRadius * Math.sin(startAngle * Math.PI / 180))); // 起始点角度在圆上对应的纵坐标
        path.lineTo(
                (float) (content_X + bigRadius * Math.cos(SweepAngle * Math.PI / 180)), // 终点角度在圆上对应的横坐标

                (float) (content_Y + bigRadius * Math.sin(SweepAngle * Math.PI / 180))); // 终点点角度在圆上对应的纵坐标
        path.close();
        // //设置一个正方形，内切圆
        RectF rectF = new RectF(content_X - bigRadius, content_Y - bigRadius, content_X + bigRadius,
                content_Y + bigRadius);
        // 下面是获得弧形剪裁区的方法
        path.addArc(rectF, startAngle, SweepAngle - startAngle);
        canvas.drawPath(path, paint);
    }

    /**
     * 返回一个扇形的剪裁区
     *
     * @param canvas     //画笔
     * @param startAngle //起始角度
     */
    private void getSmallSectorClip(Canvas canvas, float startAngle) {
        paint.setColor(Color.WHITE);
        Path path = new Path();
        // 下面是获得一个三角形的剪裁区
        path.moveTo(content_X, content_Y); // 圆心
        path.lineTo(
                (float) (content_X + smallRadius * Math.cos(startAngle * Math.PI / 180)), // 起始点角度在圆上对应的横坐标

                (float) (content_Y + smallRadius * Math.sin(startAngle * Math.PI / 180))); // 起始点角度在圆上对应的纵坐标
        path.lineTo(
                (float) (content_X + smallRadius * Math.cos(SweepAngle * Math.PI / 180)), // 终点角度在圆上对应的横坐标

                (float) (content_Y + smallRadius * Math.sin(SweepAngle * Math.PI / 180))); // 终点点角度在圆上对应的纵坐标
        path.close();
        // //设置一个正方形，内切圆
        RectF rectF = new RectF(content_X - smallRadius, content_Y - smallRadius, content_X + smallRadius,
                content_Y + smallRadius);
        // 下面是获得弧形剪裁区的方法
        path.addArc(rectF, startAngle, SweepAngle - startAngle);
        canvas.drawPath(path, paint);


    }

    public void setAngle(float startAngle) {
        SweepAngle = (360 * startAngle / 100 + 270);
    }
}