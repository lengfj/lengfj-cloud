package com.lengfj.cloud.common.mybatis.page;

import com.github.pagehelper.PageHelper;

/**
 * 分页视图的工具类
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/26
 **/
public class PageUtil {
    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final int DEFAULT_PAGE_NUMBER_START = 1;


    /**
     * 根据总数计算总页数
     *
     * @param totalCount 总数
     * @param pageSize   每页数
     * @return 总页数
     */
    public static int totalPage(long totalCount, int pageSize) {
        if (pageSize == 0) {
            return 0;
        }
        return (int)(totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1));
    }

    /**
     * 开启分页
     * @param pageParam 分页请求参数
     */
    public static void startPage(PageParam pageParam){
        if (pageParam == null){
            PageHelper.startPage(DEFAULT_PAGE_NUMBER_START, DEFAULT_PAGE_SIZE);
        }else{
            startPage(pageParam.getPageNumber(),pageParam.getPageSize());
        }
    }

    /**
     * 开启分页
     * @param pageNumber 页码
     * @param pageSize 页面数量
     */
    public static void startPage(Integer pageNumber, Integer pageSize){
        PageHelper.startPage(pageNumber == null ? DEFAULT_PAGE_NUMBER_START : pageNumber,
                                pageSize == null ? DEFAULT_PAGE_SIZE : pageSize);
    }

}
