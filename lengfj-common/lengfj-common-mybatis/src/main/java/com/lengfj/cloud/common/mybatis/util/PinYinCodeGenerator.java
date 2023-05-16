package com.lengfj.cloud.common.mybatis.util;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.lengfj.cloud.common.core.exception.enums.DefaultExceptionEnum;
import com.lengfj.cloud.common.core.util.Assert;
import com.lengfj.cloud.common.mybatis.mapper.BaseMapperX;

import java.util.Locale;
import java.util.function.Function;

/**
 * 拼音code生成器，支持自定义生成规则{@link PinYinCodeGenerator.Generator}、自定义计数开始、自定义数字格式化位数
 * <p>
 * 生成规则：
 *       <br>0.处理名称，若计数>0则拼接计数，若格式化位数>0则格式化数字后拼接计数，否则直接拼接计数
 *       <br>1.根据处理后的字符串，按照生成器规则{@link PinYinCodeGenerator.Generator} 生成拼音
 *       <br>1.1.若不需要数据库中唯一{@param dbOnly = false}，则直接返回拼音
 *       <br>2.根据生成的拼音查询数据库，code是否已经存在
 *       <br>3.若存在则在拼音后拼接count
 *       <br>4.递归查询数据库，若还存在则count+1继续递归
 *       <br>5.数据库中不存在当前拼音code，则返回code
 * @author lester
 * @date 2022/2/17 3:19 下午
 */
public class PinYinCodeGenerator {

    /**
      计数开始
     */
    private int startCount = 0;
    /**
      格式化位数
     */
    private int format = 0;

    private PinYinCodeGenerator(){}

    private PinYinCodeGenerator(int startCount, int format){
        this.startCount = startCount;
        this.format = format;
    }

    /**
     * 默认创建默认规则拼音code生成器
     * <p>
     *      <br>计数开始：1
     *      <br>格式化位数：3
     *      <br>例子：测试=CS001
     */
    public static final PinYinCodeGenerator COMMON = new PinYinCodeGenerator(1,3);

    /**
     * 创建默认的生成器
     * <p>
     *      计数从0开始
     *      不进行格式化
     * @author lester
     * @date 2022/2/17 7:54 下午
     * @return {@link PinYinCodeGenerator}
     */
    public static PinYinCodeGenerator create(){
        return new PinYinCodeGenerator();
    }

    /**
     * 创建生成器
     * <p>
     *      不进行格式化
     * @param startCount 计数开始
     * @return {@link PinYinCodeGenerator}
     */
    public static PinYinCodeGenerator create(int startCount){
        return new PinYinCodeGenerator(startCount, 0);
    }

    /**
     * 创建生成器
     * @param startCount 计数开始
     * @param format 格式化位数，需大于1
     * @return {@link PinYinCodeGenerator}
     */
    public static PinYinCodeGenerator create(int startCount, int format){
        Assert.isTrue(format < 1, DefaultExceptionEnum.ASSERT_NOT_ZERO_EXCEPTION, "格式化位数");
        return new PinYinCodeGenerator(startCount, format);
    }


    /**
     * 生成拼音code，不需要数据库唯一; 同{@link PinyinUtil}工具生成
     * @author lester
     * @date 2022/2/17 4:18 下午
     * @param str 字符串
     * @param generator 生成器
     * @return {@link String} 拼音code
     */
    public String generate(String str, Generator generator){
        return generate(str, 0, generator,false , null, false, null);
    }

    /**
     * 生成大写首字母拼音code，数据库唯一；不对is_del验证
     * @author lester
     * @date 2022/2/17 4:18 下午
     * @param str 字符串
     * @param mapperX 数据库mapper
     * @param fieldName 字段名称
     * @return {@link String} 拼音code
     */
    public String generateFirstUp(String str, BaseMapperX mapperX, String fieldName){
        return generate(str, Generator.FIRST_LETTER_UPPER, mapperX, fieldName);
    }

    /**
     * 生成大写首字母拼音code，数据库唯一；
     * @author lester
     * @date 2022/2/17 4:18 下午
     * @param str 字符串
     * @param mapperX 数据库mapper
     * @param fieldName 字段名称
     * @return {@link String} 拼音code
     */
    public String generateFirstUp(String str, BaseMapperX mapperX, boolean hasDel, String fieldName){
        return generate(str, Generator.FIRST_LETTER_UPPER, mapperX, hasDel, fieldName);
    }

    /**
     * 生成小写首字母拼音code，数据库唯一；不对is_del验证
     * @author lester
     * @date 2022/2/17 4:18 下午
     * @param str 字符串
     * @param mapperX 数据库mapper
     * @param fieldName 字段名称
     * @return {@link String} 拼音code
     */
    public String generateFirstLow(String str, BaseMapperX mapperX, String fieldName){
        return generate(str, Generator.FIRST_LETTER_LOWER, mapperX, fieldName);
    }

    /**
     * 生成小写首字母拼音code，数据库唯一；
     * @author lester
     * @date 2022/2/17 4:18 下午
     * @param str 字符串
     * @param mapperX 数据库mapper
     * @param fieldName 字段名称
     * @return {@link String} 拼音code
     */
    public String generateFirstLow(String str, BaseMapperX mapperX, boolean hasDel, String fieldName){
        return generate(str, Generator.FIRST_LETTER_LOWER, mapperX, hasDel, fieldName);
    }

    /**
     * 生成拼音code，数据库唯一，根据生成器规则；不对is_del验证
     * @author lester
     * @date 2022/2/17 4:18 下午
     * @param str 字符串
     * @param generator 生成器规则
     * @param mapperX 数据库mapper
     * @param fieldName 字段名称
     * @return {@link String} 拼音code
     */
    public String generate(String str, Generator generator, BaseMapperX mapperX, String fieldName){
        return generate(str, generator, mapperX, false, fieldName);
    }

    /**
     * 生成拼音code，数据库唯一，根据生成器规则；自定义is_del验证规则
     * @author lester
     * @date 2022/2/17 4:18 下午
     * @param str 字符串
     * @param generator 生成器规则
     * @param mapperX 数据库mapper
     * @param hasDel 是否对is_del字段验证
     * @param fieldName 字段名称
     * @return {@link String} 拼音code
     */
    public String generate(String str, Generator generator, BaseMapperX mapperX, boolean hasDel, String fieldName){
        return generate(str, this.startCount, generator,true , mapperX, hasDel, fieldName);
    }

    /**
     * 根据字符串生成拼音code，可以是在数据库中是唯一的
     * <p>
     * 生成规则：<br>
     *       0.处理名称，若计数>0则拼接计数，若格式化位数>0则格式化数字后拼接计数，否则直接拼接计数
     *       1.根据处理后的字符串，按照生成器规则{@link PinYinCodeGenerator.Generator} 生成拼音
     *       11.若不需要数据库中唯一{@param dbOnly = false}，则直接返回拼音
     *       2.根据生成的拼音查询数据库，code是否已经存在
     *       3.若存在则在拼音后拼接count
     *       4.递归查询数据库，若还存在则count+1继续递归
     *       5.数据库中不存在当前拼音code，则返回code
     * @author lester
     * @date 2022/2/17 3:49 下午
     * @param str 字符串
     * @param count 计数
     * @param generator 拼音生成器规则
     * @param dbOnly 数据库中唯一
     * @param mapperX 数据库mapper
     * @param hasDel 是否判断is_del字段 = 0
     * @param fieldName 字段名称
     * @return {@link String} 数据库中拼音code
     */
    private String generate(String str, int count, Generator generator, boolean dbOnly, BaseMapperX mapperX, boolean hasDel, String fieldName){
        //处理名称
        String newStr = count > 0 ? str + (this.format > 0 ? String.format("%0" + this.format + "d", count) : count) : str;
        //生成pinyin
        String code = generator.get(newStr);
        if (dbOnly) {
            boolean flag = mapperX.selectCount(hasDel, fieldName, code) > 0;
            if (flag) {
                count += 1;
                return generate(str, count, generator, true, mapperX, hasDel, fieldName);
            }
        }
        return code;
    }

    /**
     * 生成规则
     */
    public enum Generator{
        /**
         * 拼音首字母-大写
         */
        FIRST_LETTER_UPPER(str -> PinyinUtil.getFirstLetter(str,"").toUpperCase(Locale.getDefault())),
        /**
         * 拼音首字母-小写
         */
        FIRST_LETTER_LOWER(str -> PinyinUtil.getFirstLetter(str,"").toLowerCase(Locale.getDefault())),
        /**
         * 拼音全拼-大写
         */
        PINYIN_UPPER(str -> PinyinUtil.getPinyin(str,"").toUpperCase(Locale.getDefault())),
        /**
         * 拼音全拼-小写
         */
        PINYIN_LOWER(str -> PinyinUtil.getPinyin(str,"").toLowerCase(Locale.getDefault()));

        private final Function<String, String> fun;

        Generator(Function<String, String> fun){
            this.fun = fun;
        }

        /**
         * 获取生成结果
         * @author lester
         * @date 2022/2/17 8:32 下午
         * @param str 字符串
         * @return {@link String} 拼音结果
         */
        public String get(String str){
            return this.fun.apply(str);
        }
    }
}
