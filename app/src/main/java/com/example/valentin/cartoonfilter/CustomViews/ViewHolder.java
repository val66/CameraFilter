package com.example.valentin.cartoonfilter.CustomViews;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Valentin on 14/07/2015.
 */
public class ViewHolder {
    private TextView text;
    private ImageView logo;

    public ViewHolder(TextView text) {
        this.text = text;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

}

