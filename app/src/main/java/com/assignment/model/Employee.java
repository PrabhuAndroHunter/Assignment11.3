package com.assignment.model;

import android.graphics.Bitmap;

/**
 * Created by prabhu on 24/1/18.
 */

public class Employee {
    private int id;
    private String name;
    private String age;
    private Bitmap photo;

    public Employee(String name, String age, Bitmap photo) {
        this.name = name;
        this.age = age;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}
