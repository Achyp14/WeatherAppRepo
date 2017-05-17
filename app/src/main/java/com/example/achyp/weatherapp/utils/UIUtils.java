package com.example.achyp.weatherapp.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public final class UIUtils {

    private UIUtils() {
    }

    public static void hideKeyboard(final Context context) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
