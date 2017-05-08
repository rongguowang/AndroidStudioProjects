package com.linli.mobile.acitivty;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.linli.mobile.R;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntryActivity extends ListActivity {
    private String TAGS = "EntryActivity: ";
    private final String INTENT_CATEGORY = "com.linli.mobile.acitivty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
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
        mainIntent.addCategory(INTENT_CATEGORY);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
            Log.d(TAGS, "prefixPath is null");
        } else {
            prefixPath = prefix.split("/");
            Log.d(TAGS, "prefixPath = " + prefixPath);
            prefixWithSlash = prefix + "/";
            Log.d(TAGS, "prefixWithSlash = " + prefixWithSlash);
        }

        int len = list.size();
        Log.d(TAGS, "list.size = " + len);

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            Log.d(TAGS, "labelSeq = " + labelSeq.toString());
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;
            Log.d(TAGS, "label = " + label);

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

                String[] labelPath = label.split("/");
                Log.d(TAGS, "labelPath = " + labelPath.toString());

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];
                Log.d(TAGS, "nextLabel = " + nextLabel);

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
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
        result.setClass(this, EntryActivity.class);
        result.putExtra("com.linli.mobile.acitivty", path);
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
