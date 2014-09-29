package com.edinstudio.app.demos;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
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

public class MainActivity extends ListActivity {
    private static final String KEY_PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String path = intent.getStringExtra(KEY_PATH);
        if (path == null) {
            path = "";
        }

        setListAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
    }

    private List<Map<String, Object>> getData(String path) {
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);
        mainIntent.setPackage(getPackageName());

        PackageManager pm = getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(mainIntent, 0);
        String[] splitPath = path.split("/");
        String nextLabel = "";

        for (ResolveInfo info : infos) {
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;
            String[] splitLabel = label.split("/");

            if (splitPath[0].length() == 0) {
                if (!nextLabel.equals(splitLabel[0])) {
                    nextLabel = splitLabel[0];

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(KEY_PATH, splitLabel[0] + "/");
                    addItem(data, nextLabel, intent);
                }
            } else {
                if (splitLabel.length > splitPath.length) {
                    boolean match = true;
                    for (int i = 0; i < splitPath.length; i++) {
                        if (!splitPath[i].equals(splitLabel[i])) {
                            match = false;
                            break;
                        }
                    }

                    if (match) {
                        if (splitLabel.length - splitPath.length == 1) {
                            Intent intent = new Intent();
                            intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                            addItem(data, splitLabel[splitPath.length], intent);
                        } else {
                            Intent intent = new Intent(this, MainActivity.class);
                            StringBuilder builder = new StringBuilder();
                            for (int i = 0; i < splitPath.length + 1; i++) {
                                builder.append(splitLabel[i]);
                                builder.append("/");
                            }
                            intent.putExtra(KEY_PATH, builder.toString());
                            addItem(data, splitLabel[splitPath.length], intent);
                        }
                    }
                }

            }
        }
        Collections.sort(data, sDisplayNameComparator);
        return data;
    }

    private static final Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                Collator collator = Collator.getInstance();

                @Override
                public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
                    return collator.compare(lhs.get("title"), rhs.get("title"));
                }
            };

    private void addItem(List<Map<String, Object>> data, String title, Intent intent) {
        Map<String, Object> tmp = new HashMap<String, Object>();
        tmp.put("title", title);
        tmp.put("intent", intent);
        data.add(tmp);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
        startActivity((Intent) map.get("intent"));
    }
}
