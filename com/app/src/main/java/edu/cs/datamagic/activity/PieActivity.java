package edu.cs.datamagic.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.res.Resources;


import java.util.Random;

import edu.cs.datamagic.R;
import edu.cs.datamagic.views.LadderView;
import edu.cs.datamagic.views.PieChart;

public class PieActivity extends AppCompatActivity {
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mcontext = this.getApplicationContext();
        setContentView(R.layout.activity_pie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Resources res = getResources();
        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
        pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
        pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
        pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
        pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
        pie.addItem("Ganymede", 3, res.getColor(R.color.slate));



//        String[] slist = {"we", "can", "beat", "them", "underground", "test", "again"};
        final LadderView ladder = (LadderView)findViewById(R.id.ladder);
//        ladder.setTextList(slist);

        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //pie.setCurrentItem(0);
//                Intent intent = new Intent(mcontext, RailHelpActivity.class);
//                startActivity(intent);

                String[] slist = new String[7];
                Random r = new Random();
                for(int i = 0; i < 7; i++) {
                    slist[i] = String.valueOf(r.nextInt(100 - 10) + 10);
                }
                ladder.setTextList(slist);
            }
        });
    }

}
