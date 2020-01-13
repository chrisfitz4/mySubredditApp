package com.example.android.reddit_java.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class RVDivider extends DividerItemDecoration {


    private final int OFFSET = 3;
    private int VERTICAL_OFFSET;
    private final int MARGIN = 64;
    private final int HORIZONTAL_OFFSET = 96;
    private Drawable mDivider ;

    public RVDivider(Context context, Drawable drawable) {
        super(context, RecyclerView.HORIZONTAL);
        mDivider = drawable;
        VERTICAL_OFFSET = MARGIN*5/6;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft()+HORIZONTAL_OFFSET;
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + VERTICAL_OFFSET;
            //int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();
            int dividerBottom = dividerTop + OFFSET;

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            //Log.d("TAG_X", String.format("onDraw: %s,%s,%s,%s",dividerLeft,dividerTop,dividerRight,dividerBottom));
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view)!=0) {
            outRect.top = MARGIN;
            Log.d("TAG_X", String.format("getItemOffsets: %d,%d,%d,%d", outRect.left, outRect.top, outRect.right, outRect.bottom));
        }
        outRect.bottom = 16;
    }

//    @Override
//    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        c.drawRect(rectangle,paint);
//        Log.d("TAG_X", String.format("onDraw: %d,%d,%d,%d",rectangle.left,rectangle.top,rectangle.right,rectangle.bottom));
//    }
}
