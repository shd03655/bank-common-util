package com.core.bank.amount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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

    public static BigDecimal chinese2Number(String sAmount){

        int sLen = sAmount.length();
        int hIdx = sLen;
        char lastUnit = 0x00;

        List<String> sects = new ArrayList<>();
        
        for(int i = sLen-1; i > 0; i--){

            char c = sAmount.charAt(i);
            if(chUnit.containsKey(c) && !chUnit.containsKey(lastUnit)){
                String sect = sAmount.substring(i+1, hIdx);
                sects.add(sect);

                hIdx = i+1;
            }

            lastUnit = c;

        }

        if(hIdx > 0){
            sects.add(sAmount.substring(0, hIdx));
        }

        BigDecimal result = new BigDecimal(0);

        for(String sec : sects){

            int len = sec.length();
            BigDecimal secBd = new BigDecimal(1);
            for(int i = len - 1 ; i >= 0; i--){
                char c = sec.charAt(i);
                if(chUnit.containsKey(c)){
                    secBd = secBd.multiply(new BigDecimal(chUnit.get(c)));
                }else{
                    int d = chNum.get(c);
                    if(d > 0){
                        secBd = secBd.multiply(new BigDecimal(d));
                    }
                }
            }

            result = result.add(secBd);

        }

        return result;
    }

    public static void main(String[] args){
        BigDecimal result = chinese2Number("壹仟亿玖佰万陆仟肆佰零叁");
        System.out.println(result);
    }

}
