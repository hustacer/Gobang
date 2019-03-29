package com.example.fnst.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GobangPanel extends View {
    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;
    private int MAX_CHECK_IN_LINE = 5;

    private Paint mPaint = new Paint();

    private Bitmap mWhiteP;
    private Bitmap mBlackP;

    private float RATE_OF_PIECE = 1.0f * 3 / 4;

    private boolean mIsWhite = true;
    // get location of the chess
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    private boolean mIsGameOver;
    private boolean mIsWhiteWinner;

    public GobangPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mWhiteP = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackP = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // get width
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);

        // get height
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(wideSize, heightSize);
        if (wideMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = wideSize;
        }

        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int pieceRate = (int) (mLineHeight * RATE_OF_PIECE);

        mWhiteP = Bitmap.createScaledBitmap(mWhiteP, pieceRate, pieceRate, false);
        mBlackP = Bitmap.createScaledBitmap(mBlackP, pieceRate, pieceRate, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // if game is over, return false
        if (mIsGameOver) {
            return false;
        }

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int)event.getX();
            int y = (int)event.getY();

            Point p = new Point((int)(x/mLineHeight), (int)(y/mLineHeight));
            if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                return false;
            }

            if (mIsWhite) {
                mWhiteArray.add(p);
            } else {
                mBlackArray.add(p);
            }
            invalidate();
            mIsWhite = !mIsWhite;

            return true;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw Board
        drawBoard(canvas);

        // draw Pieces
        drawPiece(canvas);

        // check if winner is born
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(mWhiteArray);
        boolean blackWin = checkFiveInLine(mBlackArray);

        if (whiteWin || blackWin) {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;

            String text = mIsWhiteWinner ? "White Win!" : "Black Win!";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(List<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;

            boolean win = checkHorizontal(x, y, points);
            if (win) return true;
            win = checkVertical(x, y, points);
            if (win) return true;
            win = checkLeftDiagonal(x, y, points);
            if (win) return true;
            win = checkRightDiagonal(x, y, points);
            if (win) return true;

        }

        return false;
    }

    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        // left
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x - i, y))) {
                count++;
            }
            else {
                break;
            }
        }

        // right
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x + i, y))) {
                count++;
            }
            else {
                break;
            }
        }
        return (checkFive(count));
    }

    private boolean checkVertical(int x, int y, List<Point> points) {
        int count = 1;
        // up
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x, y + i))) {
                count++;
            }
            else {
                break;
            }
        }

        // down
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x, y - i))) {
                count++;
            }
            else {
                break;
            }
        }
        return (checkFive(count));
    }

    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        // up
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x - i, y + i))) {
                count++;
            }
            else {
                break;
            }
        }

        // down
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x + i, y - i))) {
                count++;
            }
            else {
                break;
            }
        }
        return (checkFive(count));
    }

    private boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        // up
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x + i, y + i))) {
                count++;
            }
            else {
                break;
            }
        }

        // down
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x - i, y - i))) {
                count++;
            }
            else {
                break;
            }
        }
        return (checkFive(count));
    }

    // check if count is five
    private boolean checkFive(int count) {
        if ( count == MAX_CHECK_IN_LINE) {
            return true;
        }

        return false;
    }

    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int)lineHeight / 2;
            int endX = (int)(w - lineHeight / 2);
            int y = (int) ((0.5 + i)*lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    private void drawPiece(Canvas canvas) {
        // white chess
        for (int i = 0, n = mWhiteArray.size(); i < n; i++ ) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhiteP,
                    (whitePoint.x + ( 1 - RATE_OF_PIECE) / 2) * mLineHeight,
                    (whitePoint.y + ( 1 - RATE_OF_PIECE) / 2) * mLineHeight, null);
        }

        // black chess
        for (int i = 0, n = mBlackArray.size(); i < n; i++ ) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackP,
                    (blackPoint.x + ( 1 - RATE_OF_PIECE) / 2) * mLineHeight,
                    (blackPoint.y + ( 1 - RATE_OF_PIECE) / 2) * mLineHeight, null);
        }
    }

    public void reStart() {
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsGameOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BALCK_ARRAY = "instance_black_array";

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BALCK_ARRAY, mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle)state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray =  bundle.getParcelableArrayList(INSTANCE_BALCK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }

        super.onRestoreInstanceState(state);
    }
}
