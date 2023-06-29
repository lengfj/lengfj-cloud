package com.lengfj.cloud.dynamic.pagination.service;


import com.lengfj.cloud.common.mybatis.page.PageView;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageInfoQueryDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageInfoSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoFieldConfigVO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoVO;

import java.util.List;

/**
 * 页面信息
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:46 PM
 */
public interface PageInfoService {

    /***
     * 列表
     *
     * @param pageCode 页面code
     * @param pageName 页面名称
     * @return {@link List<com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoVO>}
     * @author lengfj
     * @date 2022/1/11 9:14 AM
     */
    List<PageInfoVO> list(String pageCode, String pageName);

    /***
     * 列表
     *
     * @param queryDTO 分页参数
     * @author lengfj
     * @date 2022/1/11 9:47 AM
     */
    PageView<PageInfoVO> pageList(PageInfoQueryDTO queryDTO);

    /***
     * 详情
     *
     * @param id 页面id
     * @param pageCode 页面code
     * @return {@link com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoFieldConfigVO}
     * @author lengfj
     * @date 2022/1/11 3:46 PM
     */
    PageInfoFieldConfigVO detail(Integer id, String pageCode);

    /***
     * 新增
     *
     * @param saveDTO 保存参数
     * @author lengfj
     * @date 2022/1/11 2:50 PM
     */
    void save(PageInfoSaveDTO saveDTO);

    /***
     * 修改
     *
     * @param saveDTO 保存参数
     * @author lengfj
     * @date 2022/1/11 2:50 PM
     */
    void modify(PageInfoSaveDTO saveDTO);

    /***
     * 删除
     *
     * @param id 主键id
     * @author lengfj
     * @date 2022/1/11 7:10 PM
     */
    void delete(Integer id);


}
