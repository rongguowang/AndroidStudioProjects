package edu.cs.datamagic.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs.datamagic.R;

public class LauncherActivity extends ListActivity {
    private final String TAGS = "LauncherActivity: ";
    private final String INTENT_CATEGORY = "android.intent.category.LAUNCHER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String path = intent.getStringExtra("edu.cs.datamagic.Path");
        Log.d(TAGS, "path = " + path);

        if (path == null) {
            path = "";
            Log.d(TAGS, "path is null ");
        }

        setListAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
    }


    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            Log.d(TAGS, "labelSeq = " + labelSeq.toString());
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;
            Log.d(TAGS, "label = " + label);
            addItem(myData, label, activityIntent(
                    info.activityInfo.applicationInfo.packageName,
                    info.activityInfo.name));
        }

        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator collator = Collator.getInstance();

                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    protected Intent activityIntent(String pkg, String componentName) {
        Log.d(TAGS, "function activityIntent-> pkg = " + pkg + " componentName ");
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Log.d(TAGS, "function browseIntent-> path = " + path );
        Intent result = new Intent();
        result.setClass(this, MagicActivity.class);
        result.putExtra("edu.cs.datamagic.Path", path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Log.d(TAGS, "function addItem-> name = " + name );
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }
    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);

        Intent intent = new Intent((Intent) map.get("intent"));
        intent.addCategory(Intent.CATEGORY_SAMPLE_CODE);
        startActivity(intent);
    }
}
