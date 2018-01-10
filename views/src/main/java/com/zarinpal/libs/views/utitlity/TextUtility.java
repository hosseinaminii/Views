package com.zarinpal.libs.views.utitlity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.NumberFormat;
import java.util.Locale;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/17/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class TextUtility {

    /**
     * Divide a String with a specific character in specific interval
     *
     * @param text     is a string for dividing with a specific character
     * @param divider  a character for dividing value
     * @param interval for adding divider
     * @return value with added dividers
     */
    public static String divideBySpecificChar(@NonNull String text, @NonNull String divider, int interval) {
        StringBuilder result = new StringBuilder(text);
        // counter is number off dividers that added
        int counter = 0;

        for (int i = 0; i < text.length(); i++) {
            if (i != 0 && i % interval == 0 && i < text.length()) {
                result.insert(i + counter, divider);
                counter++;
            }
        }

        return result.toString();
    }

    /**
     * Convert a number to pan format EXP: 123-456-7890-1234
     *
     * @param text is a string for converting to pan format
     * @return text in pan format
     */
    public static String convertToPan(@NonNull String text) {
        if (text.isEmpty()) {
            return "";
        }
//        if ((text.length() != 16) && (text.length() != 8)) {
//            return "";
//        }

        text = text.replace("-", "");


        return divideBySpecificChar(text, "-", 4);
    }

    /**
     * Convert a text to currency format
     *
     * @param text is a string for converting to currency format
     * @return text in currency format
     */
    public static String convertToCurrency(@NonNull String text) {
        if (text.isEmpty() || text.length() <= 3) {
            return text;
        }

        text = text.replace(",", "");
        String reverse = new StringBuilder(text).reverse().toString();
        reverse = divideBySpecificChar(reverse, ",", 3);

        return new StringBuilder(reverse).reverse().toString();
    }

    /**
     * Convert to jalali date format
     * @param text date in Gregorian date
     */
    public static String convertToJalaliDate(@NonNull String text) {
        if (text.isEmpty()) {
            return "";
        }

        try {
            String[] splitString = text.split("/");

            int year  = Integer.parseInt(splitString[0]);
            int month = Integer.parseInt(splitString[1]);
            int day   = Integer.parseInt(splitString[2]);

            return new DateConverter().setGregorianDate(year, month, day).getIranianDate();
        } catch (Exception e) {
            return text;
        }
    }

    /**
     * Remove a specific character from a string
     * @param value a string
     * @param removeChar is the character for removing
     * @return a string after removing all specific characters
     */
    public static String getRawValue(String value, String removeChar) {
        return value.replace(removeChar, "");
    }

    public static void copyTextIntoClipboard(Context context, String value, OnTextCopyListener listener) {

        try {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("copy", value));
            if(listener != null) {
                listener.onTextCopy();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static long getCurrencyValue(String text) {
        String value = TextUtility.getRawValue(text, ",");
        return Long.parseLong(value.replaceAll("\\D+", ""));
    }

    public interface OnTextCopyListener {
        void onTextCopy();
    }

}
