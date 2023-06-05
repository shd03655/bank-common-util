package com.core.bank.util.transfer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * This util supports to:
 * <br> 1. use num2CHS to transfer number amount to chinese number(simple chinese) amount;
 * <br> 2. use chs2Num to transfer chinese number(simple chinese) amount to number amount;
 *
 * @version 2023.6.1 1.0
 * @author daniel.shan
 *
 */
public class AmountTransferUtil {

    final static Map<Character, Integer> chUnit = new HashMap<>();
    final static Map<Character, Integer> chNum = new HashMap<>();
    static {
        chUnit.put('拾', 10);
        chUnit.put('佰', 100);
        chUnit.put('仟', 1000);
        chUnit.put('万', 10000);
        chUnit.put('亿', 100000000);

        chNum.put('零', 0);
        chNum.put('壹', 1);
        chNum.put('贰', 2);
        chNum.put('叁', 3);
        chNum.put('肆', 4);
        chNum.put('伍', 5);
        chNum.put('陆', 6);
        chNum.put('柒', 7);
        chNum.put('捌', 8);
        chNum.put('玖', 9);

    }

    final static Map<Integer, Character> numCh = new HashMap<>();
    static {
        numCh.put(10, '拾');
        numCh.put(100, '佰');
        numCh.put(1000, '仟');
        numCh.put(10000, '万');
        numCh.put(100000000, '亿');

        numCh.put(0, '零');
        numCh.put(1, '壹');
        numCh.put(2, '贰');
        numCh.put(3, '叁');
        numCh.put(4, '肆');
        numCh.put(5, '伍');
        numCh.put(6, '陆');
        numCh.put(7, '柒');
        numCh.put(8, '捌');
        numCh.put(9, '玖');

    }

    /**
     * <pre>
     *  input specific:
     *   （0）input is simple chinese characters
     *   （1）valid characters are：零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿
     *   （2）unit can be modified the unit less than itself, for example:如拾 万、仟 万、佰 亿、千 亿等
     *   （3）continuous zeroes is represented only one simple chinese character: "零" , for example:6007 should be showing:"陆仟零柒"
     *  output specific：
     *     positive integer or zero.
     * </pre>
     *
     * @param sAmount  chinese number.
     * @return positive integer or zero according to param.
     */
    public static BigInteger chs2Num(String sAmount){

        if(sAmount == null || sAmount.length() < 1){
            throw new IllegalArgumentException("Illegal amount expression.");
        }

        int sLen = sAmount.length();

        char c;
        for(int i = sLen-1; i >= 0; i--){
            c = sAmount.charAt(i);
            if(!chUnit.containsKey(c) && !chNum.containsKey(c)){
                throw new IllegalArgumentException("Illegal amount expression[" + sAmount + "]");
            }
        }

        int hIdx = sLen;
        char lastChar = 0x00;

        List<String> sects = new ArrayList<>();
        
        for(int i = sLen-1; i >= 0; i--){

            c = sAmount.charAt(i);
            if(chUnit.containsKey(c) && !chUnit.containsKey(lastChar)){
                String sect = sAmount.substring(i+1, hIdx);
                sects.add(sect);
                hIdx = i+1;
            }

            lastChar = c;

        }

        if(hIdx > 0){
            sects.add(sAmount.substring(0, hIdx));
        }

        BigInteger result = BigInteger.ZERO;

        for(String sec : sects){

            int len = sec.length();
            BigInteger secBd = BigInteger.ONE;
            for(int i = len - 1 ; i >= 0; i--){
                c = sec.charAt(i);
                if(chUnit.containsKey(c)){
                    secBd = secBd.multiply(BigInteger.valueOf(chUnit.get(c)));
                }else{
                    int d = chNum.get(c);
                    if(d > 0){
                        secBd = secBd.multiply(BigInteger.valueOf(d));
                    }
                }
            }

            result = result.add(secBd);

        }

        return result;
    }

    /**
     * <pre>
     *  input specific:
     *   （0）input is simple chinese characters
     *   （1）valid characters are：零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿
     *   （2）unit can be modified the unit less than itself, for example:如拾 万、仟 万、佰 亿、千 亿等
     *   （3）continuous zeroes is represented only one simple chinese character: "零" , for example:6007 should be showing:"陆仟零柒"
     *  output specific：
     *     positive integer or zero.
     * </pre>
     *
     * @param num  positive integer or zero.
     * @return chinese number according to param.
     */
    public static String num2CHS(long num){

        if(num < 0){
            throw new IllegalArgumentException("Illegal amount, number should great than 0.");
        }

        String result;
        StringBuilder mid = new StringBuilder();

        int divisor;

        if(num < 10){
            mid.append(numCh.get((int)num)); // simple chinese number
        }else{
            if(num >= 100000000){
                divisor = 100000000;
            }else if(num >= 10000){
                divisor = 10000;
            }else if(num >= 1000){
                divisor = 1000;
            }else if(num >= 100){
                divisor = 100;
            }else {
                divisor = 10;
            }

            long remainder = num % divisor;

            mid.append(num2CHS(num / divisor));//quotient

            mid.append(numCh.get(divisor));//unit

            if(remainder > 0){

                if(remainder * 10 < divisor){ //append zero
                    mid.append(numCh.get(0));
                }

                mid.append(num2CHS(remainder));//remainder
            }

        }
        result = mid.toString();

        return result;
    }

}
