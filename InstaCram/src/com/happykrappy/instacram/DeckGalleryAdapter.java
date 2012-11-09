package com.happykrappy.instacram;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class DeckGalleryAdapter extends BaseAdapter {
    private Activity context;
    private static ImageView imageView;
    private List<Drawable> plotsImages;
    private static ViewHolder holder;
    
    public DeckGalleryAdapter(Activity context, List<Drawable> plotsImages) {
        this.context = context;
        this.plotsImages = plotsImages;
    }

    public int getCount() {
        return plotsImages.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            imageView = new ImageView(this.context);
            imageView.setPadding(3, 3, 3, 3);
            convertView = imageView;
            holder.imageView = imageView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageDrawable(plotsImages.get(position));
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView.setLayoutParams(new Gallery.LayoutParams(150, 90));

        return imageView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
