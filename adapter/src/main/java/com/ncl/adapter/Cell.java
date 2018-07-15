package com.ncl.adapter;

import java.util.List;


public class Cell {

    public List<Cell> children;
    public boolean isSelected;
    public boolean isExpanded;

    public Cell() {
        isSelected = false;
        isExpanded = false;
    }

    public boolean isSticky() {
        return false;
    }

    public boolean isGroup() {
        return children != null && children.size() > 0;
    }

}
