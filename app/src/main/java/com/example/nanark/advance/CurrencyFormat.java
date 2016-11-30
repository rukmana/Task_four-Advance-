package com.example.nanark.advance;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by nanark on 11/28/16.
 */

public class CurrencyFormat {

    public static String setCurrency(Context context, int bilangan){
        String currency = context.getResources().getString(R.string.currency);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol(" "+currency+" ");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        return decimalFormat.format(bilangan);
    }
}
