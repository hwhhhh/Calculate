package com.hwhhhh.calculator.utils;

import android.util.Log;

import java.math.BigDecimal;
import java.util.LinkedList;

import java.util.Stack;

public class Calculate {
    String exp;
    //判断是否是负数
    private static final String TAG = "Calculate";
    public Calculate() {
    }
    //计算左右括号
    private int leftKh = 0, rightKh = 0;

    //字符串转中缀表达式
    private LinkedList<String> strToInfix(String exp) {
        this.exp = exp;

        LinkedList<String> list = new LinkedList<>();
        //第一次分离 根据运算符号进行第一次分离，运算符与相邻后面的数字为一组。如何1+2 变为1， +2
        String regex_1 = "(?=\\+)|(?=\\-)|(?=\\*)|(?=\\/)|(?=\\()|(?=\\))|(?=\\^)|(?=!)|(?=√)";
        String[] allNum = exp.split(regex_1);
        //第二次分离 将运算符与数字彻底分离。
        String regex_2 = "(?<=\\+)|(?<=\\-)|(?<=\\*)|(?<=\\/)|(?<=\\()|(?<=\\))|(?<=\\^)|(?<=!)|(?<=√)";
        String[] someNum;
        for (int i = 0; i < allNum.length; i++) {
            someNum = allNum[i].split(regex_2);
            for (int j = 0; j < someNum.length; j++) {
                if (someNum[j].length() > 0) {
                    //处理类似9. 这种情况 将小数点去除
                    if (someNum[j].charAt(someNum[j].length()-1) == '.') {
                        someNum[j] = someNum[j].substring(0, someNum[j].length()-1);
                    } else if (someNum[j].charAt(0) == '.') { //处理.9这种情况
                        someNum[j] = someNum[j].replace(".", "0.");
                    } else if (someNum[j].equals("(")) {
                        leftKh++;
                    } else if (someNum[j].equals(")")) {
                        rightKh++;
                    }
                }
                if (!someNum[j].isEmpty()) {
                    list.add(someNum[j]);
                    Log.d(TAG, "strToInfix: " + someNum[j]);
                }
            }
        }
        while (leftKh != rightKh) {
            if (leftKh > rightKh) {
                list.add(")");
                rightKh++;
            } else {
                list.addFirst("(");
                leftKh++;
            }
        }
        return list;
    }

    //中缀表达式转后缀表达式
    private LinkedList<String> InfixToSuffix(LinkedList<String> infix) {
        //运算符栈
        LinkedList<String> list_oper = new LinkedList<>();
        //后缀表达式
        LinkedList<String> list_suffix = new LinkedList<>();

        for (int i = 0; i < infix.size(); i++) {
            String str = infix.get(i);
            //当遇到左括号直接进运算符栈
            if (str.equals("(")) {
                list_oper.add(str);
            }
            //直接进后缀表达式的栈
            else if (str.matches("[0-9]+") || str.matches("[0-9]+\\.?[0-9]+") || str.matches("(e)|(π)")) {
                if (str.equals("e")) {
                    str = Math.E + "";
                } else if (str.equals("π")) {
                    str = Math.PI + "";
                }
                list_suffix.add(str);
            }
            //当遇到右括号时，将运算符栈的运算符直接弹出添加至后缀表达式栈，直到遇到左括号
            else if (str.equals(")")) {
                String pop = list_oper.removeLast();
                while (!pop.equals("(") && !pop.isEmpty()) {
                    list_suffix.add(pop);
                    if (!list_oper.isEmpty()) {
                        pop = list_oper.removeLast();
                    }
                }
            }
            //运算符栈为空或者栈顶为左括号时直接进栈
            else if (list_oper.isEmpty() || list_oper.getLast().equals("(")) {
                //开头为负数或者 （-1）这种情况，在表达式中加0以免发生错误。
                if (str.equals("-") && (list_suffix.isEmpty() || (!list_oper.isEmpty() && list_oper.getLast().equals("(")))) {
                    list_suffix.add("0");
                }

                list_oper.add(str);
            }
            //匹配运算符
            else if (str.matches("[\\+\\-\\*\\/]") || str.matches("(sin)|(cos)|(tan)|(ln)|(log)|(√)|(\\^)")) {
                //与运算符栈中的匹配优先级，如果str为*或/而栈顶为+或-, 优先级高直接进栈。
                if (((str.matches("(\\*)|(\\/)"))
                        && (list_oper.getLast().equals("+") || list_oper.getLast().equals("-"))) || str.matches("(sin)|(cos)|(tan)|(ln)|(log)|(\\*)|(\\/)|(√)|(!)|(\\^)")) {
                    list_oper.add(str);
                } else { //其他情况优先级低或相同
                    while ( !list_oper.isEmpty() && (!(str.matches("(\\*)|(\\/)")
                            && list_oper.getLast().matches("(\\+)|(\\-)")))) {
                            list_suffix.add(list_oper.removeLast());
                            if (list_oper.isEmpty()||list_oper.getLast().equals("(")) {
                                break;
                            }
                    }
                    list_oper.add(str);
                }
            }
        }
        while (!list_oper.isEmpty()) {
            if (!list_oper.getLast().equals("(") && !list_oper.getLast().equals(")")) {
                list_suffix.add(list_oper.removeLast());
            }
        }
        for (int j = 0; j < list_suffix.size(); j ++){
            Log.d(TAG, "InfixToSuffix: " + list_suffix.get(j));
        }
        return list_suffix;
    }

    public BigDecimal getResult(String exp){
        //数字栈
        Stack<BigDecimal> nums = new Stack<>();
        BigDecimal result, a, b;
        try {
            LinkedList<String> linkedList = this.InfixToSuffix(this.strToInfix(exp));
            for (int i = 0; i < linkedList.size(); i++) {
                String str = linkedList.get(i);
                Log.d(TAG, "getResult: " + str);
                if (str.matches("[0-9]+") || str.matches("[0-9]+\\.?[0-9]+")) {
                    BigDecimal bgnum = new BigDecimal(str);
                    nums.push(bgnum);
                } else {
                    b = nums.pop();
                    if (str.equals("sin")) {
                        nums.push(new BigDecimal(Math.sin(b.doubleValue())+""));
                    } else if (str.equals("cos")) {
                        nums.push(new BigDecimal(Math.cos(b.doubleValue())+""));
                    } else if (str.equals("tan")) {
                        nums.push(new BigDecimal(Math.tan(b.doubleValue())+""));
                    } else if (str.equals("ln")) {
                        nums.push(new BigDecimal(Math.log(b.doubleValue())+""));
                    } else if (str.equals("log")) {
                        nums.push(new BigDecimal(Math.log10(b.doubleValue())+""));
                    } else if (str.equals("!")) {
                        nums.push(new BigDecimal(jiecheng(b.intValue())+""));
                    } else if (str.equals("√")){
                        nums.push(new BigDecimal(Math.sqrt(b.doubleValue())+""));
                    } else {
                        if (nums.size() >= 1) {
                            a = nums.pop();
                        } else if (nums.size() == 0){
                            a = new BigDecimal(0);
                        } else {
                            return null;
                        }

                        if (str.equals("+")) {
                            nums.push(a.add(b));
                        } else if (str.equals("-")) {
                            nums.push(a.subtract(b));
                        } else if (str.equals("*")) {
                            nums.push(a.multiply(b));
                        } else if (str.equals("/")) {
                            nums.push(a.divide(b, 13, BigDecimal.ROUND_HALF_UP).stripTrailingZeros());
                        } else if (str.equals("^")) {
                            nums.push(new BigDecimal(Math.pow(a.doubleValue(), b.doubleValue())+""));
                        }
                    }
                }
            }
            while (nums.size() != 1) {
                nums.push(nums.pop().multiply(nums.pop()));
            }
        } catch (Exception e) {
            return null;
        }
        result = nums.get(0).stripTrailingZeros();
        return result;
    }

    private int jiecheng(int i) {
        if (i == 1) {
            return 1;
        } else {
            return jiecheng(i-1) * i;
        }
    }

}
