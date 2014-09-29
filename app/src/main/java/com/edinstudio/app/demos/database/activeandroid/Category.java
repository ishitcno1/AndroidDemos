package com.edinstudio.app.demos.database.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by albert on 9/26/14.
 */
@Table(name = "Categories")
public class Category extends Model {
    @Column(name = "Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
