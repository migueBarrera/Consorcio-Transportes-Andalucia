package com.miguebarrera.consorciotransportesandalucia.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by migueBarreraBluumi on 24/01/2018.
 */

public class LinearLayoutManagerNoScroll extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public LinearLayoutManagerNoScroll(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
