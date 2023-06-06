package com.core.bank.util.validator;

import java.util.Optional;

/**
 *
 * This tool supports to :
 * Verify subject like card、account、phone、id is or is not validation ,etc.
 * Because the rule of subject is different between bank and bank,<br>
 * so that there has no good idea to do more but defining the interface and method.
 *
 * @version 2023.6.5 1.0
 * @author daniel.shan
 *
 */
public abstract class AbstractValidator {

    public enum LEN_CHECK_MODE{
        EQ, // equals
        LT, // less than
        GT // greater than
    }

    /**
     *
     * Verify the object whether it satisfies all the rules.
     *
     * @param objectNo The object will be verified.
     * @return true or false
     */
    public abstract boolean verify(String objectNo);

    /**
     *
     * delete char like space\tab and return new str.
     *
     * @param objectNo card number
     * @return new string doesn't contain space and tab chars.
     */
    protected String trim(String objectNo){

        Optional<String> opt = Optional.ofNullable(objectNo);

        if(!opt.isPresent()){//convert to empty.
            return "";
        }

        StringBuilder temp = new StringBuilder();

        for(int i = 0; i < objectNo.length(); i++){
            char c = objectNo.charAt(i);

            if(c == ' ' || c =='\t'){
                continue;
            }

            temp.append(c);
        }

        return temp.toString();

    }

    /**
     *
     * Bank card is constructed number normally, and it's length always from 11 to 19.
     *
     * @param objectNo specific card number
     * @param expLen expect length
     * @param mode length check mode
     * @return true or false.
     *
     */
    protected boolean checkLength(String objectNo, int expLen, CardValidator.LEN_CHECK_MODE mode){

        int realLen =  objectNo.length();

        switch (mode){
            case LT:
                return realLen < expLen;
            case GT:
                return realLen > expLen;
            default:
                return realLen == expLen;
        }

    }

}
