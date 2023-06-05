package com.core.bank.util.validator;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class check rule as follows, only go through all rules, return true, other is false.
 * 1.length equals 18 ( don't support 15 len id)
 * 2.the 7~14th char are valid date and the year must equals or less than now.
 * 3.the last char matches ISO7064:1983.MOD11-2 algorithm.
 *
 * @author daniel.shan
 * @version 2023.6.5 1.0
 */
public class IdValidator extends AbstractValidator {

    /**
     *
     * please reference class specification.
     *
     * @param idNo The object will be verified.
     * @return true or false
     */
    @Override
    public boolean verify(String idNo) {

        String locId = trim(idNo);

        if (locId.length() == 0) {
            return false;
        }

        //1.length equals 18
        if (!checkLength(idNo, 18, LEN_CHECK_MODE.EQ)) {
            return false;
        }

        //2.the 7~14th char are valid date and the year must equals or less than now.
        if (!checkBirthday(idNo)) {
            return false;
        }

        return checkVerifyCode(idNo);

    }

    /**
     * Check the 7~14th char are valid date and the year must equals or less than now.
     *
     * @param idNo id no
     * @return true or false
     */
    private boolean checkBirthday(String idNo) {

        String yyyyMMdd = idNo.substring(6, 14);
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            df.parse(yyyyMMdd);
        } catch (ParseException e) {
            return false;
        }

        String now = df.format(new Date());

        return Integer.parseInt(now.substring(0, 4)) >= Integer.parseInt(yyyyMMdd.substring(0, 4));

    }

    /**
     *
     * Check the last char matches ISO7064:1983.MOD11-2 algorithm.
     *
     * @param idNo id no
     * @return true or false
     */
    private boolean checkVerifyCode(String idNo) {

        char verCode = idNo.charAt(17);

        int ti = 0;

        for (int i = 0; i < idNo.length() - 1; i++) {
            ti += (idNo.charAt(i) - 48)  * factor[i];
        }

        int remainder = ti % 11;

        char exp = exp_ver_code.get(remainder);

        return verCode == exp;

    }

    private final static int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private final static Map<Integer, Character> exp_ver_code = new HashMap<>();

    static {
        exp_ver_code.put(0, '1');
        exp_ver_code.put(1, '0');
        exp_ver_code.put(2, 'X');
        exp_ver_code.put(3, '9');
        exp_ver_code.put(4, '8');
        exp_ver_code.put(5, '7');
        exp_ver_code.put(6, '6');
        exp_ver_code.put(7, '5');
        exp_ver_code.put(8, '4');
        exp_ver_code.put(9, '3');
        exp_ver_code.put(10, '2');
    }

}
