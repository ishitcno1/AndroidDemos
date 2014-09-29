package com.edinstudio.app.demos.database.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by albert on 9/26/14.
 */
@Table(name = "Items")
public class Item extends Model {
    @Column(name = "Name")
    private String name;

    @Column(name = "Category")
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
