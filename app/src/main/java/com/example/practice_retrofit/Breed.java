package com.example.practice_retrofit;

import android.media.Image;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Breed {
File file;
String sub_id;

    public Breed(File file, String sub_id) {
        this.file = file;
        this.sub_id = sub_id;
    }

    public File getFile() {
        return file;
    }

    public String getSub_id() {
        return sub_id;
    }
}
