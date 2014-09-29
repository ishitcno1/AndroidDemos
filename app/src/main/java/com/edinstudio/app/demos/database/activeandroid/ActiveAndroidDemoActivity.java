package com.edinstudio.app.demos.database.activeandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.edinstudio.app.demos.R;

import java.util.List;

/**
 * Created by albert on 9/26/14.
 */
public class ActiveAndroidDemoActivity extends Activity implements View.OnClickListener {
    private int categoryIndex = 0;
    private int itemIndex = 0;
    private Category lastCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_active_android_demo);

        clearData();

        findViewById(R.id.btn_add_category).setOnClickListener(this);
        findViewById(R.id.btn_add_item).setOnClickListener(this);
        findViewById(R.id.btn_add_data_bulkly).setOnClickListener(this);
        findViewById(R.id.btn_clear_data).setOnClickListener(this);
        findViewById(R.id.btn_show_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add_category) {
            addCategory();
        } else if (id == R.id.btn_add_item) {
            addItem();
        } else if (id == R.id.btn_add_data_bulkly) {
            clearData();
            addDataBulky();
        } else if (id == R.id.btn_clear_data) {
            clearData();
        } else if (id == R.id.btn_show_data) {
            showData();
        }
    }

    private void addCategory() {
        Category category = new Category();
        category.setName("Category " + categoryIndex);
        category.save();
        categoryIndex++;
        lastCategory = category;
    }

    private void addItem() {
        if (lastCategory == null) {
            lastCategory = new Category();
            lastCategory.setName("Category " + categoryIndex);
            lastCategory.save();
            categoryIndex++;
        }
        Item item = new Item();
        item.setName("Item " + itemIndex);
        item.setCategory(lastCategory);
        item.save();
        itemIndex++;
    }

    private void addDataBulky() {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < 3; i++) {
                Category category = new Category();
                category.setName("Category " + i);
                category.save();
                categoryIndex++;
                lastCategory = category;

                for (int j = 0; j < 2; j++) {
                    Item item = new Item();
                    item.setName("Item " + i);
                    item.setCategory(category);
                    item.save();
                    itemIndex++;
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    private void clearData() {
        new Delete().from(Item.class).execute();
        new Delete().from(Category.class).execute();
        categoryIndex = 0;
        itemIndex = 0;
        lastCategory = null;
    }

    private void showData() {
        StringBuilder result = new StringBuilder();
        List<Category> categoryList = new Select().from(Category.class).execute();
        for (Category category : categoryList) {
            List<Item> items = new Select().from(Item.class).where("Category = ?", category.getId()).execute();
            result.append(category.getName() + "\n");
            for (Item item : items) {
                result.append("\t" + item.getName() + "\n");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(result.toString());
        builder.create().show();
    }
}
