package railwayhelp.tw.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class RailHelpActivity extends AppCompatActivity {
    private Button mGraphButton = null;
    private EditText mGraphText = null;
    private Context mContext = null;
    private RoadManager mRoadManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
