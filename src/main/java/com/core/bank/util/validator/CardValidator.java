package com.core.bank.util.validator;

/**
 * This tool supports to :
 * Verify the card number is validation?
 * <p>
 * Because the rule of card is different between bank and bank,<br>
 * so this class references the template design model to support user implements the detail rule logic.
 *
 * @author daniel.shan
 * @version 2023.6.5 1.0
 */
public abstract class CardValidator extends AbstractValidator {

    public final boolean verify(String cardNo, int expLen, LEN_CHECK_MODE mode) {

        String locCardNo = trim(cardNo);

        //empty
        if (locCardNo.length() == 0) {
            return false;
        }

        return checkLength(locCardNo, expLen, mode) && checkVerifyCode(locCardNo) && checkOther(locCardNo);

    }

    /**
     * Checking cardNo's length、verify code、other(unusually)。
     *
     * @param cardNo card number
     * @return true or false
     */
    @Override
    public final boolean verify(String cardNo) {

        String locCardNo = trim(cardNo);

        //empty
        if (locCardNo.length() == 0) {
            return false;
        }

        return checkLength(locCardNo) && checkVerifyCode(locCardNo) && checkOther(locCardNo);

    }

    /**
     * Bank card is constructed number normally, and it's length always from 11 to 19.
     *
     * @param cardNo specific card number
     * @return true or false.
     */
    protected abstract boolean checkLength(String cardNo);

    /**
     * Verify code is calculated the remain part using 2121 or likely.
     * This method needs implements the real verify logic according to your bank card.
     *
     * @param cardNo specific card number
     * @return true or false.
     */
    protected abstract boolean checkVerifyCode(String cardNo);

    /**
     * This method needs implements the other verify logic according to your bank card except verify code and length.
     *
     * @param cardNo specific card number
     * @return true or false
     */
    protected abstract boolean checkOther(String cardNo);

}
