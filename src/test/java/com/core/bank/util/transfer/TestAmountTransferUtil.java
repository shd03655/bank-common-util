package com.core.bank.util.transfer;

import org.junit.Test;
import java.math.BigInteger;

public class TestAmountTransferUtil {

    @Test
    public void testCh2NumNormal1(){

        BigInteger result = AmountTransferUtil.chs2Num("壹仟亿玖佰万陆仟肆佰零叁");
        assert result.equals(BigInteger.valueOf(100009006403L));

        result = AmountTransferUtil.chs2Num("叁");
        assert result.equals(BigInteger.valueOf(3L));

    }

    @Test
    public void testNum2CHSNormal1(){

        String result = AmountTransferUtil.num2CHS(900999090009L);
        System.out.println(result);
        assert result.equals("玖仟零玖亿玖仟玖佰零玖万零玖");

        result = AmountTransferUtil.num2CHS(99L);
        System.out.println(result);
        assert result.equals("玖拾玖");

        result = AmountTransferUtil.num2CHS(0L);
        System.out.println(result);
        assert result.equals("零");

        result = AmountTransferUtil.num2CHS(19L);
        System.out.println(result);
        assert result.equals("壹拾玖");

        result = AmountTransferUtil.num2CHS(109L);
        System.out.println(result);
        assert result.equals("壹佰零玖");

    }
}
