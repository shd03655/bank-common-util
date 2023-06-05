package com.core.bank.util.validator;

import org.junit.Test;

public class TestCardValidator {

    private static class CardValidatorImpl extends CardValidator{

        @Override
        protected boolean checkLength(String cardNo) {
            return true;
        }

        @Override
        protected boolean checkVerifyCode(String cardNo) {
            return true;
        }

        @Override
        protected boolean checkOther(String cardNo) {
            return true;
        }
    }


    @Test
    public void testNullCardNo(){
        CardValidator validator = new CardValidatorImpl();
        validator.verify(null);
    }

    @Test
    public void testSpaceCardNo(){
        CardValidator validator = new CardValidatorImpl();
        assert validator.verify("111  222\t333", 9, CardValidator.LEN_CHECK_MODE.EQ) ;
    }

}
