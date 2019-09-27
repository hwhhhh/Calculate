package com.hwhhhh.calculator.utils;

import android.util.Log;

import java.math.BigDecimal;

public class ConvertSys {
    private String num, result;
    private String dwa, dwb;

    private String binary = "二进制";
    private String octal = "八进制";
    private String ten = "十进制";
    private String hex = "十六进制";
    private static final String TAG = "ConvertSys";

    public ConvertSys() {

    }

    public String getResult(String num, String dwa, String dwb) {
        this.num = num;
        this.dwa = dwa;
        this.dwb = dwb;

        try {
            toTen();
            if (dwb.equals(binary)) {
                toBinary();
            } else if (dwb.equals(octal)) {
                toOctal();
            } else if (dwb.equals(ten)) {
                result = this.num;
            } else {
                toHex();
            }
        } catch (Exception e) {
            return null;
        }
        Log.d(TAG, "getResult: " + result);
        return result;
    }

    private void toBinary() {
        result = Integer.toBinaryString(Integer.parseInt(num));
        Log.d(TAG, "toBinary: " + num );
    }

    private void toOctal() {
        result = Integer.toOctalString(Integer.parseInt(num));
        Log.d(TAG, "toOctal: " + num);
    }

    private void toTen() {
        if (dwa.equals(binary)) {
            this.num = Integer.valueOf(num, 2).toString();
        } else if (dwa.equals(octal)) {
            this.num = Integer.valueOf(num, 8).toString();
        } else if (dwa.equals(hex)) {
            this.num = Integer.valueOf(num, 16).toString();
        }
        Log.d(TAG, "toTen: " + num);
    }

    private void toHex() {
        result = Integer.toHexString(Integer.parseInt(num));
        Log.d(TAG, "toHex: " + num);
    }
}
