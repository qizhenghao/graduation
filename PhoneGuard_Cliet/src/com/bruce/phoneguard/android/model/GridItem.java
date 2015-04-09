package com.bruce.phoneguard.android.model;

/**
 * @author qizhenghao
 * data: 2014年12月27日10:32:21
 * @version 1.0
 * function: 提供给Gridview使用的bean
 */
public class GridItem {

	private int id;
	private String name;
	private String EngName;
	private int imageId;
	private boolean checked;


	public GridItem(int id, String name, String EngName, int imageId, boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.EngName = EngName;
		this.imageId = imageId;
		this.checked = checked;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getEngName() {
		return EngName;
	}

	public void setEngName(String engName) {
		EngName = engName;
	}

	
}
