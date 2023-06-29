package com.lengfj.cloud.dynamic.pagination.starter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.lengfj.cloud.common.mybatis.page.PageParam;
import com.lengfj.cloud.common.mybatis.page.PageView;
import com.lengfj.cloud.common.mybatis.page.TableHeader;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class QueryPageHandler {

    private Executor executor = new ThreadPoolExecutor(
            10,
            50,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            factory -> new Thread("query-page-aop-executor"));

    @Autowired
    private PageUserConfigApi pageUserConfigApi;
    @Autowired
    private PageInfoApi pageInfoApi;

    @Around("@annotation(QueryPage)")
    public Object around(ProceedingJoinPoint joinPoint, QueryPage queryPage) throws Throwable {
        PageParam pageParam = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof PageParam){
                pageParam = (PageParam) arg;
                break;
            }
        }
        if (pageParam == null){
            return joinPoint.proceed();
        }
        //分页列表code 优先取后端配置的
        String pageCode = StrUtil.isNotEmpty(queryPage.pageCode()) ? queryPage.pageCode() : pageParam.getPageCode();
        //主体id
        Long userId = pageParam.getUserId();
        /*
            1. 若有主体id，则获取用户自定义列表
            2. 若无主体id，则获取默认列表
            3. 异步处理
            4. 填充返回结果PageView, 若返回对象不是PageView则不填充
         */
        CompletableFuture<List<PageListConfigVO>> future = CompletableFuture.supplyAsync(() -> {
            List<PageInfoVO> pageList = pageInfoApi.list(pageCode, null);
            if (CollUtil.isEmpty(pageList)){
                return new ArrayList<>();
            }
            Integer pageId = pageList.get(0).getId();
            if (userId == null){
                return pageUserConfigApi.configList(userId, pageId);
            }else{
                return pageUserConfigApi.defaultConfigList(pageId);
            }
        }, executor);
        //执行业务逻辑
        Object proceed = joinPoint.proceed();
        if (proceed instanceof PageView){
            //返回对象为PageView时 填充
            ArrayList<TableHeader> tableHeaders = new ArrayList<>();
            //获取异步结果
            for (PageListConfigVO configVO : future.getNow(new ArrayList<>())) {
                TableHeader tableHeader = new TableHeader();
                BeanUtils.copyProperties(configVO, tableHeader);
                tableHeaders.add(tableHeader);
            }
            ((PageView)proceed).setHeaders(tableHeaders);
        }
        return proceed;

    }

}
