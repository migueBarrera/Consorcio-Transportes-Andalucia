package com.miguebarrera.consorciotransportesandalucia.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomePagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;

    public WelcomePagerAdapter() {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       /* layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);

        return view;*/
       return  null;
    }

    @Override
    public int getCount() {
        return 0;//layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
