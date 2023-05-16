package com.lengfj.cloud.common.core.util.moeny;

import cn.hutool.core.math.Money;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;

/**
 * 金钱工具类 金钱相关更多操作见 {@link Money}
 * @author lester
 * @date 2022/2/15 10:57 上午
 */
public class MoneyUtil {

    /**
     * 金额元转换为分
     *
     * @param yuan 金额，单位元
     * @return 金额，单位分
     */
    public static long yuanToCent(BigDecimal yuan) {
        return new Money(yuan).getCent();
    }

    /**
     * 金额分转换为元
     *
     * @param cent 金额，单位分
     * @return 金额，单位元
     */
    public static BigDecimal centToYuan(long cent) {
        long yuan = cent / 100;
        int centPart = (int) (cent % 100);
        return new Money(yuan, centPart).getAmount();
    }


    /**
     * 金额分转换为元，并在千分位添加分隔符
     *
     * @param cent 分
     * @param symbol 分隔符  传空字符串则表示不分割
     * @return 元字符串
     */
    public static String centToYuan(long cent, String symbol){
        if (StrUtil.isEmptyIfStr(symbol)){
            return centToYuan(cent).toString();
        }

        int flag = 0;
        String amString = String.valueOf(cent);
        if(amString.charAt(0)=='-'){
            flag = 1;
            amString = amString.substring(1);
        }
        StringBuffer result = new StringBuffer();
        if(amString.length()==1){
            result.append("0.0").append(amString);
        }else if(amString.length() == 2){
            result.append("0.").append(amString);
        }else{
            String intString = amString.substring(0,amString.length()-2);
            for(int i=1; i<=intString.length();i++){
                if( (i-1)%3 == 0 && i !=1){
                    result.append(symbol);
                }
                result.append(intString.substring(intString.length()-i,intString.length()-i+1));
            }
            result.reverse().append(".").append(amString.substring(amString.length()-2));
        }
        if(flag == 1){
            return "-"+result.toString();
        }else{
            return result.toString();
        }
    }
}
