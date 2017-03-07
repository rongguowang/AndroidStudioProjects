package edu.cs.datamagic;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class WayPickerActivity extends AppCompatActivity {
    private EditText mWayText = null;
    private EditText mStopText = null;
    private EditText mLengthText = null;
    private Button mFindWayButton = null;
    private Button mFindWayByStopButton = null;
    private Button mFindShortestButton = null;
    private Button mFindWayByLengthButton = null;
    private TextView mResultText = null;
    private RoadManager mRoadManager = null;
    private Context mContext = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_picker);

        //initView();
    }

    public void initView() {
        mWayText = (EditText) findViewById(R.id.editText_way);
        mStopText = (EditText) findViewById(R.id.editText_stop);
        mLengthText = (EditText) findViewById(R.id.editText_length);
        mFindWayButton = (Button) findViewById(R.id.button_find_way);
        mResultText = (TextView) findViewById(R.id.textView_result);

        mFindWayByStopButton = (Button) findViewById(R.id.button_find_way_by_stop);
        mFindWayByLengthButton = (Button) findViewById(R.id.button_find_way_by_length);
        mFindShortestButton = (Button) findViewById(R.id.button_find_short_way);
        mRoadManager = RoadManager.getRoadManager();
        mContext = this.getApplicationContext();

        mFindWayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wayDesc = mWayText.getText().toString();
                String WayList = mRoadManager.findWay(wayDesc);
                mResultText.setText((CharSequence) WayList);
            }
        });

        mFindWayByStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wayDesc = mWayText.getText().toString();
                String stopStr = mStopText.getText().toString();
                int stopCount = Integer.valueOf(stopStr);

                String waylist = mRoadManager.findAllWaysByStop(wayDesc, stopCount);
                mResultText.setText((CharSequence) waylist);

            }
        });

        mFindShortestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wayDesc = mWayText.getText().toString();
                String WayList = mRoadManager.findShortestWay(wayDesc);
                mResultText.setText((CharSequence) WayList);
            }
        });

        mFindWayByLengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wayDesc = mWayText.getText().toString();
                String stopStr = mLengthText.getText().toString();
                int length = Integer.valueOf(stopStr);

                String waylist = mRoadManager.findAllWaysByLength(wayDesc, length);
                mResultText.setText((CharSequence) waylist);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_way_picker, menu);

        ActionMenuView bottomBar = (ActionMenuView) findViewById(R.id.bottom_tool_bar);
        Menu bottomMenu = bottomBar.getMenu();
        getMenuInflater().inflate(R.menu.menu_bottom_bar, bottomMenu);
        for (int i = 0; i < bottomMenu.size(); i++) {
            MenuItem item = bottomMenu.getItem(i);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Action Setting Menu selected!",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.bottom_toolbar_findway) {
            Toast.makeText(this, "Working on finding a way!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.bottom_toolbar_stop) {
            Toast.makeText(this, "Working on finding by stop1", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.bottom_toolbar_shortest) {
            Toast.makeText(this, "Working on finding shortest way!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.bottom_toolbar_allways) {
            Toast.makeText(this, "Finding all the ways!", Toast.LENGTH_SHORT).show();
            return true;
        } else {}


        return super.onOptionsItemSelected(item);
    }
}
