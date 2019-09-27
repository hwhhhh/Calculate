package com.hwhhhh.calculator.utils;

import java.math.BigDecimal;

public class ConvertLen {
    private BigDecimal a;
    private String dwa, dwb;

    private String nm = "纳米";
    private String um = "微米";
    private String mm = "毫米";
    private String cm = "厘米";
    private String m = "米";
    private String km = "千米";

    public ConvertLen() {
    }

    public String getResult(String a, String dwa, String dwb) {
        this.a = new BigDecimal(a);
        this.dwa = dwa;
        this.dwb = dwb;
        String result = "";

        if (dwa.equals(nm)) {
            result = fromNm();
        } else if (dwa.equals(um)) {
            result = fromUm();
        } else if (dwa.equals(mm)) {
            result = fromMm();
        } else if (dwa.equals(cm)) {
            result = fromCm();
        } else if (dwa.equals(m)) {
            result = fromM();
        } else if (dwa.equals(km)) {
            result = fromKm();
        }
        return result;
    }

    public String fromNm() {
        BigDecimal result = new BigDecimal("0");
        if (dwb.equals(nm)) {
            result = a;
        } else if (dwb.equals(um)) {
            result = a.divide(new BigDecimal("1000"));
        } else if (dwb.equals(mm)) {
            result = a.divide(new BigDecimal("1000000"));
        } else if (dwb.equals(cm)) {
            result = a.divide(new BigDecimal("10000000"));
        } else if (dwb.equals(m)) {
            result = a.divide(new BigDecimal("1000000000"));
        } else if (dwb.equals(km)) {
            result = a.divide(new BigDecimal("1000000000000"));
        }
        return result.stripTrailingZeros().toPlainString();
    }

    public String fromUm() {
        BigDecimal result = new BigDecimal("0");
        if (dwb.equals(nm)) {
            result = a.multiply(new BigDecimal("1000"));
        } else if (dwb.equals(um)) {
            result = a;
        } else if (dwb.equals(mm)) {
            result = a.divide(new BigDecimal("1000"));
        } else if (dwb.equals(cm)) {
            result = a.divide(new BigDecimal("10000"));
        } else if (dwb.equals(m)) {
            result = a.divide(new BigDecimal("1000000"));
        } else if (dwb.equals(km)) {
            result = a.divide(new BigDecimal("1000000000"));
        }
        return result.stripTrailingZeros().toPlainString();
    }

    public String fromMm() {
        BigDecimal result = new BigDecimal("0");
        if (dwb.equals(nm)) {
            result = a.multiply(new BigDecimal("1000000"));
        } else if (dwb.equals(um)) {
            result = a.multiply(new BigDecimal("1000"));
        } else if (dwb.equals(mm)) {
            result = a;
        } else if (dwb.equals(cm)) {
            result = a.divide(new BigDecimal("10"));
        } else if (dwb.equals(m)) {
            result = a.divide(new BigDecimal("1000"));
        } else if (dwb.equals(km)) {
            result = a.divide(new BigDecimal("1000000"));
        }
        return result.stripTrailingZeros().toPlainString();
    }

    public String fromCm() {
        BigDecimal result = new BigDecimal("0");
        if (dwb.equals(nm)) {
            result = a.multiply(new BigDecimal("10000000"));
        } else if (dwb.equals(um)) {
            result = a.multiply(new BigDecimal("10000"));
        } else if (dwb.equals(mm)) {
            result = a.multiply(new BigDecimal("10"));
        } else if (dwb.equals(cm)) {
            result = a;
        } else if (dwb.equals(m)) {
            result = a.divide(new BigDecimal("100"));
        } else if (dwb.equals(km)) {
            result = a.divide(new BigDecimal("100000"));
        }
        return result.stripTrailingZeros().toPlainString();
    }

    public String fromM() {
        BigDecimal result = new BigDecimal("0");
        if (dwb.equals(nm)) {
            result = a.multiply(new BigDecimal("1000000000"));
        } else if (dwb.equals(um)) {
            result = a.multiply(new BigDecimal("1000000"));
        } else if (dwb.equals(mm)) {
            result = a.multiply(new BigDecimal("1000"));
        } else if (dwb.equals(cm)) {
            result = a.multiply(new BigDecimal("100"));
        } else if (dwb.equals(m)) {
            result = a;
        } else if (dwb.equals(km)) {
            result = a.divide(new BigDecimal("1000"));
        }
        return result.stripTrailingZeros().toPlainString();
    }

    public String fromKm() {
        BigDecimal result = new BigDecimal("0");
        if (dwb.equals(nm)) {
            result = a.multiply(new BigDecimal("1000000000000"));
        } else if (dwb.equals(um)) {
            result = a.multiply(new BigDecimal("1000000000"));
        } else if (dwb.equals(mm)) {
            result = a.multiply(new BigDecimal("1000000"));
        } else if (dwb.equals(cm)) {
            result = a.multiply(new BigDecimal("100000"));
        } else if (dwb.equals(m)) {
            result = a.multiply(new BigDecimal("1000"));
        } else if (dwb.equals(km)) {
            result = a;
        }
        return result.stripTrailingZeros().toPlainString();
    }
}
