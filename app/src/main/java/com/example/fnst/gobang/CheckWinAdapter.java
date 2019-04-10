package com.example.fnst.gobang;

import android.content.Context;
import android.graphics.Point;

import java.util.List;

public class CheckWinAdapter {
    private final static String TAG = "CheckWinAdapter";
    private Context mContext;
    private List<Point> mPoints;
    private static int MAX_CHECK_IN_LINE = 5;

    public CheckWinAdapter(Context context, List<Point> points){
        mContext = context;
        mPoints = points;
    }

    public boolean checkFiveInLine() {
        for (Point p : mPoints) {
            int x = p.x;
            int y = p.y;

            boolean win = checkHorizontal(x, y, mPoints);
            if (win) return true;
            win = checkVertical(x, y, mPoints);
            if (win) return true;
            win = checkLeftDiagonal(x, y, mPoints);
            if (win) return true;
            win = checkRightDiagonal(x, y, mPoints);
            if (win) return true;

        }

        return false;
    }

    private static boolean checkHorizontal(int x, int y, List<Point> points) {
        int countL = 1;
        int countR = 1;
        // left
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x - i, y))) {
                countL++;
            } else if (points.contains(new Point(x + i, y))) {
                countR++;
            }
            else {
                break;
            }
        }
//
//        // right
//        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
//            if(points.contains(new Point(x + i, y))) {
//                count++;
//            }
//            else {
//                break;
//            }
//        }
        return (checkFive(countL + countR - 1));
    }

    private static boolean checkVertical(int x, int y, List<Point> points) {
        int countUp = 1;
        int countDown = 1;
        // up
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x, y + i))) {
                countUp++;
            } else if(points.contains(new Point(x, y - i))) {
                countDown++;
            }
            else {
                break;
            }
        }
//
//        // down
//        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
//            if(points.contains(new Point(x, y - i))) {
//                count++;
//            }
//            else {
//                break;
//            }
//        }
        return (checkFive(countUp + countDown - 1));
    }

    private static boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int countUp = 1;
        int countDown = 1;
        // up
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x - i, y + i))) {
                countUp++;
            } else if(points.contains(new Point(x + i, y - i))) {
                countDown++;
            }
            else {
                break;
            }
        }
//
//        // down
//        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
//            if(points.contains(new Point(x + i, y - i))) {
//                count++;
//            }
//            else {
//                break;
//            }
//        }
        return (checkFive(countUp + countDown - 1));
    }

    private static boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int countUp = 1;
        int countDown = 1;
        // up
        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
            if(points.contains(new Point(x + i, y + i))) {
                countUp++;
            } else if(points.contains(new Point(x - i, y - i))) {
                countDown++;
            }
            else {
                break;
            }
        }
//
//        // down
//        for (int i = 1; i < MAX_CHECK_IN_LINE; i++) {
//            if(points.contains(new Point(x - i, y - i))) {
//                count++;
//            }
//            else {
//                break;
//            }
//        }
        return (checkFive(countUp + countDown - 1));
    }

    // check if count is five
    private static boolean checkFive(int count) {
        if ( count == MAX_CHECK_IN_LINE) {
            return true;
        }

        return false;
    }
}
