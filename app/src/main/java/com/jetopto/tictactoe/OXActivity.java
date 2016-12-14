/*
------------------
|  0  |  3  |  6  |
------------------
|  1  |  4  |  7  |
------------------
|  2  |  5  |  8  |
------------------

Position 0 = 0x1
Position 1 = 0x2
Position 2 = 0x4
Position 3 = 0x8
Position 4 = 0x10
Position 5 = 0x20
Position 6 = 0x40
Position 7 = 0x80
Position 8 = 0x100

Using the bit mask to decide the winner status.
There are 8 case to win this game.
Case1 (MASK1): 0 1 2 in a line
Case2 (MASK2): 3 4 5 in a line
Case3 (MASK3): 6 7 8 in a line
Case4 (MASK4): 0 3 6 in a line
Case5 (MASK5): 1 4 7 in a line
Case6 (MASK6): 2 5 8 in a line
Case7 (MASK7): 0 4 8 in a line
Case8 (MASK8): 2 4 6 in a line
*/

package com.jetopto.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

public class OXActivity extends AppCompatActivity {

    private int mTurns = 0;
    GridLayout mLayout;
    TextView mTextView;
    Button mButton;
    LinearLayout mLinearLayout;
    public int mRedPlayer = 0;
    public int mYellowPlayer = 0;
    private final String TAG = "OOXX";
    private final int MASK1 = 0x1 | 0x2 | 0x4;
    private final int MASK2 = 0x8 | 0x10 | 0x20;
    private final int MASK3 = 0x40 | 0x80 | 0x100;
    private final int MASK4 = 0x1 | 0x8 | 0x40;
    private final int MASK5 = 0x2 | 0x10 | 0x80;
    private final int MASK6 = 0x4 | 0x20 | 0x100;
    private final int MASK7 = 0x1 | 0x10 | 0x100;
    private final int MASK8 = 0x4 | 0x10 | 0x40;
    private int mTotal = 0;
    private boolean mGameFinish = false;

    public void dropIn(View view) {
        ImageView imageView = (ImageView) view;
        int id = Integer.parseInt(imageView.getTag().toString());
        int finishType = 0;

        if (((mRedPlayer & (1 << id)) == 0) && ((mYellowPlayer & (1 << id)) == 0) ) {
            imageView.setTranslationY(-300.0f);
            if (mTurns == 0) {
                imageView.setImageResource(R.drawable.red);
                mTurns = 1;
                mRedPlayer |= (1 << id);
            } else {
                imageView.setImageResource(R.drawable.yellow);
                mYellowPlayer |= (1 << id);
                mTurns = 0;
            }
            imageView.animate().translationYBy(300.0f).rotation(200).setDuration(1000);

            mTotal++;
        }

        if (winnerCheck(mRedPlayer)) {
            Log.e(TAG, "[mumin] Red win!!!!!!!!!");
            mGameFinish = true;
            mTextView.setText("Red Win!");
        } else if (winnerCheck(mYellowPlayer)) {
            Log.e(TAG, "[mumin] yellow win!!!!!!!!!");
            mGameFinish = true;
            mTextView.setText("Yellow win!");
        } else {
            if (mTotal == 9) {
                Log.e(TAG, "[mumin] Draw !!!!!!!!!");
                mGameFinish = true;
                mTextView.setText("Draw!");
            }
        }

        if (mGameFinish)
            mLinearLayout.setVisibility(View.VISIBLE);
    }

    public void resetAll(View view) {
        mRedPlayer = 0;
        mYellowPlayer = 0;
        mTotal = 0;
        mGameFinish = false;
        int count = mLayout.getChildCount();
        for(int i = 0 ; i <count ; i++) {
            View child = mLayout.getChildAt(i);
            ImageView imageView = (ImageView) child;
            imageView.setImageResource(0);
        }
        mLinearLayout.setVisibility(View.INVISIBLE);
    }

    public boolean winnerCheck(int player) {
       if (((player & MASK1) == MASK1) ||
               ((player & MASK2) == MASK2) ||
               ((player & MASK3) == MASK3) ||
               ((player & MASK4) == MASK4) ||
               ((player & MASK5) == MASK5) ||
               ((player & MASK6) == MASK6) ||
               ((player & MASK7) == MASK7) ||
               ((player & MASK8) == MASK8)) {
           return true;
       } else {
           return false;
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ox);

        mTextView = (TextView) findViewById(R.id.textView2);
        mLayout = (GridLayout) findViewById(R.id.gridLayout);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mButton = (Button) findViewById(R.id.button);
        mLinearLayout.setVisibility(View.INVISIBLE);
    }
}
