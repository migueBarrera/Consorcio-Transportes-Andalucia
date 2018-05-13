package com.miguebarrera.consorciotransportesandalucia.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtil {private static final String FONTS_PATH = "fonts/";

    private static final String ROBOT_REGULAR_FONT = "Roboto-Regular.ttf";
    private static final String ROBOT_BOLD_FONT = "Roboto-Bold.ttf";
    private static final String ROBOT_LIGHT_FONT = "Roboto-Light.ttf";

    public static Typeface regularFont;
    public static Typeface boldFont;
    public static Typeface lightFont;

    public static Typeface getRegularFont(Context context) {
        if (regularFont == null) {
            regularFont = Typeface.createFromAsset(context.getAssets(),
                    FONTS_PATH + ROBOT_REGULAR_FONT);
        }
        return regularFont;
    }

    public static Typeface getBoldFont(Context context) {
        if (boldFont == null) {
            boldFont = Typeface.createFromAsset(context.getAssets(),
                    FONTS_PATH + ROBOT_BOLD_FONT);
        }
        return boldFont;
    }

    public static Typeface getLightFont(Context context) {
        if (lightFont == null) {
            lightFont = Typeface.createFromAsset(context.getAssets(),
                    FONTS_PATH + ROBOT_LIGHT_FONT);
        }
        return lightFont;
    }
}
