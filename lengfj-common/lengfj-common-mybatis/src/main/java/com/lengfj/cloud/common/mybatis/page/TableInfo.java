package com.lengfj.cloud.common.mybatis.page;

import lombok.Data;

import java.util.List;

/**
 * 数据表信息
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/26
 **/
@Data
public class TableInfo<T> {

    /**
     * 数据表头
     */
    private List<TableHeader> headers;

    /**
     * 数据表内容信息
     */
    private List<T> body;

}
