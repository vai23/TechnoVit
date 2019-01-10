package com.ask.vitevents.Classes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}
