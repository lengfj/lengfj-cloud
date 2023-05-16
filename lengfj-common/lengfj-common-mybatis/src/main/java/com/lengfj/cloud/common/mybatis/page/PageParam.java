package com.lengfj.cloud.common.mybatis.page;

import cn.hutool.core.lang.Segment;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.db.sql.Order;

import java.io.Serializable;

/**
 * 分页请求参数
 * 重写了Hutool的Page对象
 * 新增字段pageCode、fields
 * 修改开始页码从1开始
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/25
 **/
public class PageParam implements Segment<Integer>, Serializable {

    /**
     * 主体id
     */
    private Long userId;
    /**
     * 视图页面code码
     */
    private String pageCode;
    /**
     * 页码，1表示第一页
     */
    private int pageNumber;
    /**
     * 每页结果数
     */
    private int pageSize;
    /**
     * 排序
     */
    private Order[] orders;
    /**
     * 查询的字段
     */
    private String[] fields;

    /**
     * 创建Page对象
     *
     * @param pageNumber 页码，1表示第一页
     * @param pageSize   每页结果数
     * @return PageParam
     */
    public static PageParam of(int pageNumber, int pageSize) {
        return new PageParam(pageNumber, pageSize);
    }

    /**
     * 创建Page对象
     *
     * @param pageNumber 页码，1表示第一页
     * @param pageSize   每页结果数
     * @param pageCode   页面code
     * @return PageParam
     */
    public static PageParam of(int pageNumber, int pageSize, String pageCode) {
        return new PageParam(pageNumber, pageSize, pageCode);
    }

    // ---------------------------------------------------------- Constructor start

    /**
     * 构造，默认第1页，每页20 条
     *
     */
    public PageParam() {
        this(PageUtil.DEFAULT_PAGE_NUMBER_START, PageUtil.DEFAULT_PAGE_SIZE);
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，1表示第一页
     * @param pageSize   每页结果数
     */
    public PageParam(int pageNumber, int pageSize) {
        this.pageNumber = Math.max(pageNumber, PageUtil.DEFAULT_PAGE_NUMBER_START);
        this.pageSize = pageSize <= 0 ? PageUtil.DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 构造
     *
     * @param pageCode 页面code
     */
    public PageParam(String pageCode){
        this();
        this.pageCode = pageCode;
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，1表示第一页
     * @param pageSize   每页结果数
     * @param order      排序对象
     */
    public PageParam(int pageNumber, int pageSize, Order order) {
        this(pageNumber, pageSize);
        this.orders = new Order[]{order};
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，1表示第一页
     * @param pageSize   每页结果数
     * @param pageCode   页面code
     */
    public PageParam(int pageNumber, int pageSize, String pageCode) {
        this(pageNumber, pageSize);
        this.pageCode = pageCode;
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，1表示第一页
     * @param pageSize   每页结果数
     * @param pageCode   页面code
     * @param order      排序对象
     */
    public PageParam(int pageNumber, int pageSize, String pageCode, Order order) {
        this(pageNumber, pageSize,order);
        this.pageCode = pageCode;
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，1表示第一页
     * @param pageSize   每页结果数
     * @param pageCode   页面code
     * @param order      排序对象
     * @param fields     查询字段
     */
    public PageParam(int pageNumber, int pageSize, String pageCode, Order order, String[] fields) {
        this(pageNumber, pageSize, pageCode, order);
        this.fields = fields;
    }

    // ---------------------------------------------------------- Constructor start

    // ---------------------------------------------------------- Getters and Setters start


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    /**
     * @return 页码，1表示第一页
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置页码，1表示第一页
     *
     * @param pageNumber 页码
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = Math.max(pageNumber, PageUtil.DEFAULT_PAGE_NUMBER_START);
    }

    /**
     * @return 每页结果数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页结果数
     *
     * @param pageSize 每页结果数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize <= 0) ? PageUtil.DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * @return 排序
     */
    public Order[] getOrders() {
        return this.orders;
    }

    public String getOrdersStr() {
        StringBuffer sb = new StringBuffer();
        if (orders != null && orders.length > 0) {
            for (int i = 0; i < orders.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(orders[i].toString());
            }
        }
        return sb.toString();
    }


    /**
     * 设置排序
     *
     * @param orders 排序
     */
    public void setOrder(Order... orders) {
        this.orders = orders;
    }

    /**
     * 设置排序
     *
     * @param orders 排序
     */
    public void addOrder(Order... orders) {
        this.orders = ArrayUtil.append(this.orders, orders);
    }
    // ---------------------------------------------------------- Getters and Setters end

    /**
     * @return 开始位置
     * @see #getStartIndex()
     */
    public int getStartPosition() {
        return getStartIndex();
    }

    @Override
    public Integer getStartIndex() {
        return this.pageNumber * this.pageSize;
    }

    /**
     * @return 结束位置
     * @see #getEndIndex()
     */
    public int getEndPosition() {
        return getEndIndex();
    }

    @Override
    public Integer getEndIndex() {
        Integer startIndex = getStartIndex();
        return startIndex + this.pageSize;
    }
}
