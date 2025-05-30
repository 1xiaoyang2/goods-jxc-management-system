package com.yang.jxc.constant;

/**
 * @author 李博洋
 * @date 2025/5/27
 * 系统常量
 */
public class Constant {

    // 认证Token的前缀
    public static final String SYS_TOKEN_PREFIX = "jxc";

    public static final Integer USER_STATUS_DISABLE = 0; // 账号禁用

    public static final Integer USER_STATUS_NORMAL = 1; // 账号正常

    public static  final  String SUCCESS ="成功";

    public static  final  String FAIL ="失败";

    public  static  final  Integer STOCK_FLAG_ADD =0;  //库存增加

    public  static  final  Integer STOCK_FLAG_REDUCE =1;  //库存减少

    public  static  final Integer DENOMINATOR= 6000;  // denominator 国际容积重量换算公式分母

    public  static  final  Integer  SQUARE_METER_TO_CENTIMETER=1000000;     //SquareMeterToCentimeter   立方米转立方厘米
}
