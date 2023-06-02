package com.core.bank.util.transfer;

import org.junit.Test;
import java.math.BigInteger;

public class TestAmountTransferUtil {

    @Test
    public void testCh2NumNormal1(){

        BigInteger result = com.core.bank.util.transfer.AmountTransferUtil.chs2Num("壹仟亿玖佰万陆仟肆佰零叁");
        System.out.println(result);

        result = AmountTransferUtil.chs2Num("叁");
        System.out.println(result);

    }
}
