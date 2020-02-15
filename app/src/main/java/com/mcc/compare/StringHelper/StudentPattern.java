package com.mcc.compare.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @CreateDate: 2020/2/7 12:03
 * @Author MCC
 */
public class StudentPattern {

    public static List<String> toStudentList(String string) {
        List<String> list = new ArrayList<>();
        //对输入做初步处理,将标点符号化为统一的‘,’
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] <='9'&& chars[i] >='0') {
                chars[i] = ',';
            }
            if (isChinesePunctuation(chars[i])
                    ||chars[i] == '.'
                    ||chars[i]==' '
                    ||chars[i]==',') {
                chars[i] = ',';
            }
        }
        //然后按规则分割
        Pattern pattern = Pattern.compile("\\,+");
        String[] strings = pattern.split(String.valueOf(chars));

        for (String s:strings) {
            if (s.length() > 0) {
                list.add(s);
            }
        }

        return list;
    }

    private static boolean isChinesePunctuation(char a) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(a);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else {
            return false;
        }
    }
}
