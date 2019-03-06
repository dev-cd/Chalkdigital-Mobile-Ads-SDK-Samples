package com.chalkdigital.android.compoundviews;


import android.text.Editable;
import android.view.View;

/**
 * Created by arungupta on 04/07/16.
 */

public interface CDEditTextListener {

    public void onTextCleared(CDEditText cdEditText);
    public void beforeTextChanged(CDEditText cdEditText, CharSequence charSequence, int i, int i1, int i2);
    public void onTextChanged(CDEditText cdEditText, CharSequence charSequence, int i, int i1, int i2);
    public void afterTextChanged(CDEditText cdEditText, Editable editable);
    public void onFocusChange(CDEditText cdEditText, View view, boolean b);

}
