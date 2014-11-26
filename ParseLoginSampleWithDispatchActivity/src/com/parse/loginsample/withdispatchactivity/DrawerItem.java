package com.parse.loginsample.withdispatchactivity;

/**
 * Created by Patrick on 09/10/2014.
 */

public class DrawerItem {

    String ItemName;
    int imgResID;
    boolean selected;

    public DrawerItem(String itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
        this.selected = false;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public boolean isSelected() { return selected; }
    public void setSelected() { this.selected = true; }

}