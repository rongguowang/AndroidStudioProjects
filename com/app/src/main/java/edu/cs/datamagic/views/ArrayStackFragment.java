package edu.cs.datamagic.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.cs.datamagic.R;
import edu.cs.datamagic.activity.StackActivity;

/**
 * Created by GoWang on 2017/3/14.
 */

public class ArrayStackFragment extends Fragment {
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

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ArrayStackFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ArrayStackFragment newInstance(int sectionNumber) {
        ArrayStackFragment fragment = new ArrayStackFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.array_stack_fragment, container, false);

        mSquare = (SquareView)rootView.findViewById(R.id.array_square);
        mLadder = (LadderView)rootView.findViewById(R.id.array_ladder);
        mText = (TextView)rootView.findViewById(R.id.array_text);
        mClear = (ImageButton)rootView.findViewById(R.id.array_clear);
        mEditText = (EditText)rootView.findViewById(R.id.array_edittext);
        mPush = (ImageButton)rootView.findViewById(R.id.array_push);
        mPop = (ImageButton)rootView.findViewById(R.id.array_pop);

        mSquare.setIndexText("Top");
        mSquare.setInnerText(String.valueOf(mStackIndex));

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mStack.clear();
                    mLadder.setTextList(null);
                    mStackIndex = 0;
                    mSquare.setInnerText(String.valueOf(mStackIndex));
                    mText.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                if( text != null) {
                    mStack.push(text);
                    mLadder.setTextList(stack2Array(mStack));
                    mStackIndex++;
                    mSquare.setInnerText(String.valueOf(mStackIndex));
                } else {
//                    Toast.makeText(this.)
                }
            }
        });
        mPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mStack.size() > 0) {
                        String text = (String)mStack.pop();
                        mLadder.setTextList(stack2Array(mStack));
                        mText.setText("Value poped: " + text);
                        mStackIndex--;
                        mSquare.setInnerText(String.valueOf(mStackIndex));
                    } else {
//                        Toast.makeText();
                    }
                } catch (Exception nullE) {
                    nullE.printStackTrace();
//                    Toast.makeText();
                }
            }
        });

        return rootView;
    }

    private String[] stack2Array(Stack<String> a){
        if (a.size() == 0)
            return null;
        String[] b = a.toArray(new String[a.size()]);
        String[] c = new String[b.length];
        for (int i = 0, j = (b.length - 1) ; i < c.length; i++) {
            c[i] = b[j];
            j--;
        }
        return c;
    }
}
