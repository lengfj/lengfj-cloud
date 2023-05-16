package com.lengfj.cloud.common.mybatis.page;

import com.github.pagehelper.PageInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


/**
 * 分页响应结果
 * @author lester
 * @version V1.0
 * @date 2021/11/26
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class PageView<T> extends TableInfo<T> {

    /**
     * 页码，1表示第一页
     */
    private int page;
    /**
     * 每页结果数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总数
     */
    private long total;


    /**
     * 创建一个PageView根据PageInfo
     * @param pageInfo pageHelper返回的
     * @param <T> 泛型
     * @return PageView
     */
    public static <T> PageView<T> of(@NotNull PageInfo<T> pageInfo){
        return new PageView<T>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 创建一个PageView根据PageInfo
     * @param pageInfo pageHelper返回的
     * @param headers 表格头信息
     * @param <T> 泛型
     * @return PageView
     */
    public static <T> PageView<T> of(@NotNull PageInfo<T> pageInfo, List<TableHeader> headers){
        return new PageView<T>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), headers, pageInfo.getList());
    }

    /**
     * 创建一个空的PageView
     * @param <T>
     * @return 空的PageView
     */
    public static <T> PageView<T> ofEmpty(){
        return new PageView<T>();
    }

    /**
     * 构造
     */
    public PageView() {
        this(PageUtil.DEFAULT_PAGE_NUMBER_START, PageUtil.DEFAULT_PAGE_SIZE, 0, new ArrayList<>());
    }

    /**
     * 构造
     *
     * @param page     页码，1表示第一页
     * @param pageSize 每页结果数
     * @param total    结果总数
     * @param list     数据列表
     */
    public PageView(int page, int pageSize, long total, List<T> list) {
        this.page = Math.max(page, PageUtil.DEFAULT_PAGE_NUMBER_START);
        this.pageSize = pageSize <= 0 ? PageUtil.DEFAULT_PAGE_SIZE : pageSize;
        this.total = total;
        this.totalPage = PageUtil.totalPage(total, pageSize);
        super.setBody(list);
    }

    /**
     * 构造
     *
     * @param page     页码，1表示第一页
     * @param pageSize 每页结果数
     * @param total    结果总数
     * @param headers  表头
     * @param list     数据列表
     */
    public PageView(int page, int pageSize, long total, List<TableHeader> headers, List<T> list) {
        this.page = Math.max(page, PageUtil.DEFAULT_PAGE_NUMBER_START);
        this.pageSize = pageSize <= 0 ? PageUtil.DEFAULT_PAGE_SIZE : pageSize;
        this.total = total;
        this.totalPage = PageUtil.totalPage(total, pageSize);
        super.setHeaders(headers);
        super.setBody(list);
    }

    /**
     * @return 是否第一页
     */
    public boolean isFirst() {
        return this.page == PageUtil.DEFAULT_PAGE_NUMBER_START;
    }

    /**
     * @return 是否最后一页
     */
    public boolean isLast() {
        return this.page >= this.totalPage;
    }



}
