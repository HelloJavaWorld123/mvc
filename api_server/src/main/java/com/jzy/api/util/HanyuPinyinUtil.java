package com.jzy.api.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字取拼音转换工具
 * @author <a href="mailto:aparatrooper@163.com.com">aparatrooper</a>
 * @since JDK1.8
 */
public class HanyuPinyinUtil {

    /**
     * 字符串第一个字符首字母大写
     * @param ChineseLanguage 字符串
     * @return
     */
    public static String getFirstLettersUp(String ChineseLanguage) {
        return getFirstLetter(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE);
    }

    /**
     * 字符串第一个字符首字母小写
     * @param ChineseLanguage 字符串
     * @return
     */
    public static String getFirstLettersLo(String ChineseLanguage) {
        return getFirstLetter(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

    /**
     * 字符串每个字符首字母大写
     * @param ChineseLanguage 字符串
     * @return
     */
    public static String getSpllLetterUp(String ChineseLanguage) {
        return getSpllLetter(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE);
    }

    /**
     * 字符串每个字符首字母大写
     * @param ChineseLanguage 字符串
     * @return
     */
    public static String getSpllLetterLo(String ChineseLanguage) {
        return getSpllLetter(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

    /**
     * 将文字转为汉语拼音(小写)
     * @param chineseaLanguage 要转成拼音的中文
     */
    public static String toHanyuPinyin(String chineseaLanguage) {
        char[] cl_chars = chineseaLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                } else {// 如果字符不是中文,则不转换
                    hanyupinyin += cl_chars[i];
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

    /**
     * 取所有汉字的第一个拼音字符
     * @param ChineseLanguage
     * @param caseType
     * @return
     */
    public static String getSpllLetter(String ChineseLanguage, HanyuPinyinCaseType caseType) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                String str = String.valueOf(cl_chars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0].substring(0, 1);
                } else if (str.matches("[0-9]+")) {
                    // 如果字符是数字,取数字
                    hanyupinyin += cl_chars[i];
                } else if (str.matches("[a-zA-Z]+")) {
                    // 如果字符是字母,取字母
                    hanyupinyin += cl_chars[i];
                    // 否则不转换
                } else {
                    //如果是标点符号的话，带着
                    hanyupinyin += cl_chars[i];
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

    /**
     * 取第一个汉字的第一个拼音字符
     * @param ChineseLanguage 字符串
     * @param caseType        大小写
     * @return
     */
    public static String getFirstLetter(String ChineseLanguage, HanyuPinyinCaseType caseType) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String str = String.valueOf(cl_chars[0]);
            if (str.matches("[\u4e00-\u9fa5]+")) {
                // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
                        cl_chars[0], defaultFormat)[0].substring(0, 1);
                ;
            } else if (str.matches("[0-9]+")) {
                // 如果字符是数字,取数字
                hanyupinyin += cl_chars[0];
            } else if (str.matches("[a-zA-Z]+")) {
                // 如果字符是字母,取字母
                hanyupinyin += cl_chars[0];
            } else {// 否则不转换

            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

}
