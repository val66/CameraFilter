package com.example.valentin.cartoonfilter.CustomViews;

/**
 * Created by Valentin on 14/07/2015.
 */
public class ListViewItem {
    private String username;
    private int type;
    private boolean clicked;

    public ListViewItem(String text, int type) {
        this.username = text;
        this.type = type;
        this.clicked = false;
    }

    public String getText() {
        return username;
    }

    public void setText(String text) {
        this.username = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getClicked() {
        return this.clicked;
    }

    public void setClicked() {
        if (this.clicked == false)
            this.clicked = true;
        else
            this.clicked = false;
    }
}
