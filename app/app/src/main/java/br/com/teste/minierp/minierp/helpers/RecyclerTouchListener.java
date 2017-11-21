package br.com.teste.minierp.minierp.helpers;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private static final String TAG = "RecyclerTouchListener";

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SWIPE_MAX_OFF_PATH = 250;

    private OnTouchActionListener mOnTouchActionListener;
    private GestureDetectorCompat mGestureDetector;

    public static interface OnTouchActionListener {
        public void onLeftSwipe(View view, int position);
        public void onRightSwipe(View view, int position);
        public void onClick(View view, int position);
    }

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                 OnTouchActionListener onTouchActionListener){

        mOnTouchActionListener = onTouchActionListener;
        mGestureDetector = new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG, "On Single Tap Confirmed");
                // Find the item view that was swiped based on the coordinates
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int childPosition = recyclerView.getChildPosition(child);
                mOnTouchActionListener.onClick(child, childPosition);
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                Log.d(TAG, "onFling: " + e1.toString() + e2.toString());

                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                        return false;
                    }

                    // Find the item view that was swiped based on the coordinates
                    View child = recyclerView.findChildViewUnder(e1.getX(), e1.getY());
                    int childPosition = recyclerView.getChildPosition(child);

                    // right to left swipe
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                        Log.d(TAG, "Left Swipe");
                        if (mOnTouchActionListener != null && child != null) {
                            mOnTouchActionListener.onLeftSwipe(child, childPosition);
                        }

                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Log.d(TAG, "Right Swipe");
                        if (mOnTouchActionListener != null && child != null) {
                            mOnTouchActionListener.onRightSwipe(child, childPosition);
                        }
                    }
                } catch (Exception e) {
                    // nothing
                }

                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // do nothing
    }

}