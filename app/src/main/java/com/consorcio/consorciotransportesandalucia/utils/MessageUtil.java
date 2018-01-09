package com.consorcio.consorciotransportesandalucia.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

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
}
