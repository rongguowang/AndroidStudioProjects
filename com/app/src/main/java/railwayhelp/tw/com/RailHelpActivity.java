package railwayhelp.tw.com;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RailHelpActivity extends AppCompatActivity {
    private Button mGraphButton = null;
    private EditText mGraphText = null;
    private Context mContext = null;
    private RoadManager mRoadManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rail_help);
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        View contentView = this.getLayoutInflater().inflate(R.layout.content_rail_help, null);
        setContentView(contentView);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setupActionBar();
        initView();
    }

    public void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        TabContentFragment fragment1 = new TabContentFragment();
        fragment1.setText("Tab 1");
        actionBar.addTab(actionBar.newTab().setText("Tab 1").setTabListener(new AppTabListner(fragment1)));
        TabContentFragment fragment2 = new TabContentFragment();
        fragment2.setText("Tab 2");
        actionBar.addTab(actionBar.newTab().setText("Tab 2").setTabListener(new AppTabListner(fragment2)));
        TabContentFragment fragment3 = new TabContentFragment();
        fragment3.setText("Tab 3");
        actionBar.addTab(actionBar.newTab().setText("Tab 3").setTabListener(new AppTabListner(fragment3)));

    }
    public void initView() {
        mContext = this.getApplicationContext();
        mGraphText = (EditText)findViewById(R.id.graph_field);
        mGraphButton = (Button)findViewById(R.id.fileSelectButton);
        mGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String graph = (String)mGraphText.getText().toString();
                mRoadManager = RoadManager.getRoadManager(mContext, graph);

                Intent intent = new Intent(mContext, WayPickerActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rail_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AppTabListner implements TabListener {
        private TabContentFragment mFragment;

        public AppTabListner(TabContentFragment fragment) {
            mFragment = fragment;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            ft.add(R.id.fragment_content, mFragment,mFragment.getText());
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(RailHelpActivity.this, "Reslected", Toast.LENGTH_LONG).show();
        }
    }

    public static class TabContentFragment extends Fragment {
        private String mText;

        public TabContentFragment() {}
//        public TabContentFragment(String text) {
//            mText = text;
//        }

        public void setText(String text) {
            mText = text;
        }
        public String getText() {
            return mText;
        }
        public View onCreatView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_content, container, false);
            TextView text = (TextView)fragView.findViewById(R.id.text);
            text.setText(mText);

            return fragView;
        }

    }
}
