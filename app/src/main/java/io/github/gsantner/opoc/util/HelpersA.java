/*
 * ---------------------------------------------------------------------------- *
 * Gregor Santner <gsantner.github.io> wrote this file. You can do whatever
 * you want with this stuff. If we meet some day, and you think this stuff is
 * worth it, you can buy me a coke in return. Provided as is without any kind
 * of warranty. No attribution required.                  - Gregor Santner
 *
 * License of this file: Creative Commons Zero (CC0 1.0)
 *  http://creativecommons.org/publicdomain/zero/1.0/
 * ----------------------------------------------------------------------------
 */
package io.github.gsantner.opoc.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;


@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue", "SpellCheckingInspection"})
public class HelpersA extends Helpers {
    //########################
    //## Members, Constructors
    //########################
    protected Activity _activity;

    public HelpersA(final Activity activity) {
        super(activity);
        _activity = activity;
    }

    //########################
    //##     Methods
    //########################

    /**
     * Animate to specified Activity
     *
     * @param to                 The class of the activity
     * @param finishFromActivity true: Finish the current activity
     * @param requestCode        Request code for stating the activity, not waiting for result if null
     */
    public void animateToActivity(Class to, Boolean finishFromActivity, Integer requestCode) {
        animateToActivity(new Intent(_activity, to), finishFromActivity, requestCode);
    }

    /**
     * Animate to Activity specified in intent
     * Requires animation resources
     *
     * @param intent             Intent to open start an activity
     * @param finishFromActivity true: Finish the current activity
     * @param requestCode        Request code for stating the activity, not waiting for result if null
     */
    public void animateToActivity(Intent intent, Boolean finishFromActivity, Integer requestCode) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (requestCode != null) {
            _activity.startActivityForResult(intent, requestCode);
        } else {
            _activity.startActivity(intent);

        }
        _activity.overridePendingTransition(getResId(ResType.DIMEN, "fadein"), getResId(ResType.DIMEN, "fadeout"));
        if (finishFromActivity != null && finishFromActivity) {
            _activity.finish();
        }
    }


    public void showSnackBar(@StringRes int stringId, boolean showLong) {
        Snackbar.make(_activity.findViewById(android.R.id.content), stringId,
                showLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) _activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (_activity.getCurrentFocus() != null && _activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(_activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showDialogWithHtmlTextView(@StringRes int resTitleId, String html) {
        showDialogWithHtmlTextView(resTitleId, html, true, null);
    }

    public void showDialogWithHtmlTextView(@StringRes int resTitleId, String text, boolean isHtml, DialogInterface.OnDismissListener dismissedListener) {
        AppCompatTextView textView = new AppCompatTextView(_context);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                _context.getResources().getDisplayMetrics());
        textView.setMovementMethod(new LinkMovementMethod());
        textView.setPadding(padding, 0, padding, 0);

        textView.setText(isHtml ? new SpannableString(Html.fromHtml(text)) : text);
        AlertDialog.Builder dialog = new AlertDialog.Builder(_context)
                .setPositiveButton(android.R.string.ok, null)
                .setOnDismissListener(dismissedListener)
                .setTitle(resTitleId)
                .setView(textView);
        dialog.show();
    }
}
