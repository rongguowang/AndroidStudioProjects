package edu.cs.datamagic.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.cs.datamagic.R;
import edu.cs.datamagic.graphics.point;
import edu.cs.datamagic.views.LadderView;
import edu.cs.datamagic.views.MyAnimationView;
import edu.cs.datamagic.views.SquareView;

public class ArrayListStack extends AppCompatActivity implements View.OnClickListener{
    private SquareView mSquare = null;
    private LadderView mLadder = null;
    private TextView mText = null;
    private ImageButton mClear = null;
    private EditText mEditText = null;
    private ImageButton mPush = null;
    private ImageButton mPop = null;
    private List mList = new ArrayList();
    private Stack<String> mStack = new Stack<String>();
    private int mStackIndex = 0;
    private static MyAnimationView animView = null;
    private Animator.AnimatorListener animatorListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSquare = (SquareView)findViewById(R.id.array_square);
        mLadder = (LadderView)findViewById(R.id.array_ladder);
        mText = (TextView)findViewById(R.id.array_text);
        mClear = (ImageButton)findViewById(R.id.array_clear);
        mEditText = (EditText)findViewById(R.id.array_edittext);
        mPush = (ImageButton)findViewById(R.id.array_push);
        mPop = (ImageButton)findViewById(R.id.array_pop);

        mSquare.setIndexText("Top");
        mSquare.setInnerText(String.valueOf(mStackIndex));

        mClear.setOnClickListener(this);
        mPush.setOnClickListener(this);
        mPop.setOnClickListener(this);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private String[] stack2Array(Stack<String> a){
        if (a.size() == 0)
            return null;
        String[] b = a.toArray(new String[a.size()]);
        return b;
    }


    public void onClick(View v) {
        final String text;
        switch (v.getId()) {
            case R.id.array_clear:
                try {
                    mStack.clear();
                    mLadder.setTextList(null);
                    mStackIndex = 0;
                    mSquare.setInnerText(String.valueOf(mStackIndex));
                    mText.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.array_push:
                text = mEditText.getText().toString();
                if( text != null && (mStackIndex) < mLadder.getLadderSteps()  && mStackIndex >= 0) {
                    mStack.push(text);
                    mStackIndex++;
                    animatorListener = new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mPush.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLadder.setTextList(stack2Array(mStack));
                            mSquare.setInnerText(String.valueOf(mStackIndex));

                            animView.setAlpha(0);
                            animView.clearAnimation();
                            mPush.setEnabled(true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    };
                    RelativeLayout rl = (RelativeLayout)findViewById(R.id.array_stack_fragment);
                    animView = new MyAnimationView(this);
                    rl.addView(animView);
                    final int[] mSquareLocation = new int[2];
                    final int[] mLadderLocation = new int[2];
                    mSquare.getLocationOnScreen(mSquareLocation);
                    mLadder.getLocationOnScreen(mLadderLocation);
                    final point p = mSquare.getInnerTextPosition();
                    final point pL = mLadder.getIndexPosition(mStackIndex);
                    animView.startAnimation(new point(mSquareLocation[0] + p.getX(), mSquareLocation[1] + p.getY()), new point(mLadderLocation[0] + pL.getX(), mLadderLocation[1] + pL.getY()), animatorListener);

                } else {

                }
                break;
            case R.id.array_pop:
                try {
                    if (mStack.size() > 0) {
                        text = (String)mStack.pop();
                        mStackIndex--;
                        animatorListener = new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                mPop.setEnabled(false);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mLadder.setTextList(stack2Array(mStack));
                                mText.setText("Value poped: " + text);
                                mSquare.setInnerText(String.valueOf(mStackIndex));
                                mPop.setEnabled(true);
                                animView.setAlpha(0);
                                animView.clearAnimation();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        };
                        RelativeLayout rl = (RelativeLayout)findViewById(R.id.array_stack_fragment);
                        animView = new MyAnimationView(this);
                        rl.addView(animView);
                        int[] mTextLocation = new int[2];
                        int[] mLadderLocation = new int[2];
                        mText.getLocationOnScreen(mTextLocation);
                        mLadder.getLocationOnScreen(mLadderLocation);
                        point p = mSquare.getInnerTextPosition();
                        point pL = mLadder.getTextPosition(mStackIndex);
                        animView.startAnimation( new point(mLadderLocation[0] + pL.getX(), mLadderLocation[1] + pL.getY()),new point(mTextLocation[0] + 160, mTextLocation[1] - 128), animatorListener);
                    } else {

                    }
                } catch (Exception nullE) {
                    nullE.printStackTrace();
                }
                break;
            default:
                break;

        }
    }
}
