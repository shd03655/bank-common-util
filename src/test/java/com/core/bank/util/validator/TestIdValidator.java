package com.core.bank.util.validator;

import org.junit.Test;
public class TestIdValidator {

    AbstractValidator validator = new IdValidator();
    @Test
    public void testRightIdNo(){
        assert validator.verify("330623198210045734");
    }

}
