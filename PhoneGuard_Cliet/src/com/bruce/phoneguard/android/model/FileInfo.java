package com.bruce.phoneguard.android.model;

import android.graphics.drawable.Drawable;
import com.bruce.phoneguard.android.utils.FileUtils;

import java.io.File;

/**
 * 进程信息
 * 
 * @author qizhenghao	
 * data: 2015年3月4日21:07:43
 * @version 1.0
 */
public class FileInfo {
	private int icon_id;
	private boolean checked;
    private String size;
    private String name;
    private long length;
    private String absolutePath;

    public FileInfo(File item) {
        this.name = item.getName();
        this.size = FileUtils.getFileSize(item.length());
        this.checked = false;
        this.length = item.length();
        this.icon_id = FileUtils.getDrawableByExtension(FileUtils.getFileExtension(item));
        absolutePath = item.getAbsolutePath();
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
