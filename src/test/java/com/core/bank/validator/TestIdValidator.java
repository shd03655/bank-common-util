package com.core.bank.validator;

import com.core.bank.util.validator.AbstractValidator;
import com.core.bank.util.validator.IdValidator;
import org.junit.Test;
public class TestIdValidator {

    AbstractValidator validator = new IdValidator();
    @Test
    public void testRightIdNo(){
        assert validator.verify("330623198210045734");
    }

}
