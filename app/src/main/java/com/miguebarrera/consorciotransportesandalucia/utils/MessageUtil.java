package com.miguebarrera.consorciotransportesandalucia.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.miguebarrera.consorciotransportesandalucia.R;

/**
 * Created by migueBarreraBluumi on 09/01/2018.
 */

public class MessageUtil {
    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    public static void showSnackbar(View view, Context context, final int mainTextStringId, final int actionStringId,
                                    View.OnClickListener listener) {
        Snackbar.make(
                view.findViewById(android.R.id.content),
                context.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(actionStringId), listener).show();
    }

    public static void showSnackbar(View view, Context context, final int mainTextStringId) {
        Snackbar.make(
                view.findViewById(android.R.id.content),
                context.getString(mainTextStringId),
                Snackbar.LENGTH_LONG).show();
    }

    public static  void  showNeedInternet(Activity parentActivity){
        new MaterialDialog.Builder(parentActivity)
                .title(R.string.dialog_title_warning)
                .icon(parentActivity.getResources().getDrawable(R.drawable.warning))
                .content(R.string.dialog_title_need_internet)
                .positiveText(R.string.dialog_txt_ok)
                .positiveColorRes(R.color.colorPrimary)
                .show();
    }

    public static  void  showNoServerWork(Activity parentActivity){
        new MaterialDialog.Builder(parentActivity)
                .title(R.string.dialog_title_ups)
                .icon(parentActivity.getResources().getDrawable(R.drawable.warning))
                .content(R.string.dialog_title_content_ups)
                .positiveText(R.string.dialog_txt_ok)
                .positiveColorRes(R.color.colorPrimary)
                .show();
    }
}
