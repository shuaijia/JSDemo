package com.jia.demo.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description: 有折叠效果的RecyclerView
 * Created by jia on 2017/3/30 0030.
 * 人之所以能，是相信能
 */
public class JsParallaxRecyclerView extends RecyclerView {

    public JsParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public JsParallaxRecyclerView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 如果是正在编辑的状态，就返回，不做处理
        if (isInEditMode()) {
            return;
        }

        // 纵向布局
        setLayoutManager(new LinearLayoutManager(context));

        // 添加分割线
        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                // item距离为负数
                outRect.bottom = -dp2px(context, 10);
            }
        });

        // 设置滑动监听
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstPosition = layoutManager.findFirstVisibleItemPosition();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                int visibleCount = lastPosition - firstPosition;
                //重置控件的位置及高度
                int elevation = 1;
                for (int i = firstPosition - 1; i <= (firstPosition + visibleCount) + 1; i++) {
                    View view = layoutManager.findViewByPosition(i);
                    if (view != null) {
                        if (view instanceof CardView) {
                            ((CardView) view).setCardElevation(dp2px(context, elevation));
                            elevation += 5;
                        }
                        float translationY = view.getTranslationY();
                        if (i > firstPosition && translationY != 0) {
                            view.setTranslationY(0);
                        }
                    }
                }

                View firstView = layoutManager.findViewByPosition(firstPosition);
                float firstViewTop = firstView.getTop();
                firstView.setTranslationY(-firstViewTop / 2.0f);
            }
        });
    }


    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
