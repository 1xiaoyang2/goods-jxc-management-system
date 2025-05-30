package com.yang.jxc.utils;

/**
 * API返回码封装类
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录"),
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 进销管理中的按钮
     */
    LACK_STOCK(1000,"仓库容量不足"),
    REPEAT(1001,"不能出现重复入库的情况"),


    /**
     * 采购--------------->采购退货----------->出库
     */
    OVER_EXIT_GOODS(1002,"本订单已退货，请不要重复退货!"),

    /**
     * 销售----------------->销售退货-------->出库
     */

    OVER_EXIT_SALE(2002,"出库错误，请不要重复出库"),
    CREATE_S(3001,"已生成销售退货单"),
    NOT_EXIT(3002,"原出库清单不存在，回收异常"),

    ;

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
