package com.hwhhhh.calculator.utils;

import java.math.BigDecimal;

public class ConvertVol {
    private BigDecimal a, result;
    private String dwa, dwb;

    private String hs = "毫升";
    private String s = "升";
    private String lflm = "立方厘米";
    private String lfm = "立方米";

    public ConvertVol() {
    }

    public String getResult(String num, String dwa, String dwb) {
        this.a = new BigDecimal(num);
        this.dwa = dwa;
        this.dwb = dwb;

        if (dwa.equals(hs) || dwa.equals(lflm)) {
            fromHs();
        } else {
            fromS();
        }
        return result.stripTrailingZeros().toPlainString();
    }

    private void fromHs() {
        if (dwb.equals(hs)) {
            result = a;
        } else if (dwb.equals(s)) {
            result = a.divide(new BigDecimal("1000"));
        } else if (dwb.equals(lflm)) {
            result = a;
        } else {
            result = a.divide(new BigDecimal("1000"));
        }
    }

    private void fromS() {
        if (dwb.equals(hs)) {
            result = a.multiply(new BigDecimal("1000"));
        } else if (dwb.equals(s)) {
            result = a;
        } else if (dwb.equals(lflm)) {
            result = a.multiply(new BigDecimal("1000"));
        } else {
            result = a;
        }
    }
}
