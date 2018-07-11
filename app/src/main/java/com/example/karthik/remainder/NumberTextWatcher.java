package com.example.karthik.remainder;

import android.icu.text.DecimalFormat;
import android.net.ParseException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by karthik on 09-07-2018.
 */
class NumberTextWatcher implements TextWatcher{

   EditText editText;
    public NumberTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
            String userInput = "" + s.toString().replaceAll("[^\\d]", "");
            StringBuilder cashAmountBuilder = new StringBuilder(userInput);

            while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                cashAmountBuilder.deleteCharAt(0);
            }
            while (cashAmountBuilder.length() < 3) {
                cashAmountBuilder.insert(0, '0');
            }
            cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

            editText.removeTextChangedListener(this);
            editText.setText(cashAmountBuilder.toString());

            editText.setTextKeepState("$" + cashAmountBuilder.toString());
            Selection.setSelection(editText.getText(), cashAmountBuilder.toString().length() + 1);

            editText.addTextChangedListener(this);
        }
    }
}