package com.otw.asd.jjdt.entity;

/**
 * listview item类型
 * Created by Administrator on 2016/5/26.
 */
public class PinnedSectionItem {
    public static final int SECTION = 0;
    public static final int ITEM = 1;

    //item 类型
    private int itemType = 1;
    private String sectionString;


    /**
     * item 类型
     * 默认为PinnedSectionItem.ITEM
     *
     * @return 0:PinnedSectionItem.SECTION   1:PinnedSectionItem.ITEM
     */
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getSectionString() {
        return sectionString;
    }

    public void setSectionString(String sectionString) {
        this.sectionString = sectionString;
    }
}
