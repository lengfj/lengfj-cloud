package com.lengfj.cloud.common.core.constant;

/**
 * 通用接口常量类
 *
 * @author lester
 * @version V1.0
 * @date 2021/10/21
 **/
public interface ApiConstant {

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 成功标记
     */
    String BUSINESS_SUCCESS = "0";
    /**
     * 失败标记
     */
    String BUSINESS_FAIL = "1";

    /**
     * 常用消息
     */
    interface Msg {

        String SUCCESS = "成功";

        String SUCCESS_OPERATE = "操作成功";

        String SUCCESS_UPDATE = "更新成功";

        String SUCCESS_ADD = "添加成功";

        String SUCCESS_DEL = "删除成功";


        String FAILURE = "失败";

        String FAILURE_OPERATE = "操作失败";

        String FAILURE_UPDATE = "更新失败";

        String FAILURE_ADD = "添加失败";

        String FAILURE_DEL = "删除失败";


    }

}
