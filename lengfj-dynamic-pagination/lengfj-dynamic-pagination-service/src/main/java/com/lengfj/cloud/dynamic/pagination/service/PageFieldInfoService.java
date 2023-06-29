package com.lengfj.cloud.dynamic.pagination.service;

import com.lengfj.cloud.dynamic.pagination.model.dto.PageFieldInfoSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageFieldInfoVO;

import java.util.List;

/**
 * 页面字段信息
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:52 PM
 */
public interface PageFieldInfoService {

    /***
     * 列表 根据pageId
     *
     * @param pageId 页面id
     * @return {@link List<com.lengfj.cloud.dynamic.pagination.model.vo.PageFieldInfoVO>}
     * @author lengfj
     * @date 2022/1/11 9:40 AM
     */
    List<PageFieldInfoVO> list(Integer pageId, String fieldCode);

    /***
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 主键id
     * @author lengfj
     * @date 2022/1/11 2:53 PM
     */
    Integer save(PageFieldInfoSaveDTO saveDTO);

    /***
     * 修改根据页面和字段code
     *
     * @param modifyDto 修改参数
     * @author lengfj
     * @date 2022/1/11 2:53 PM
     */
    void modify(PageFieldInfoSaveDTO modifyDto);

    /***
     * 删除
     *
     * @param pageId 页面id
     * @param fieldCode 字段code
     * @author lengfj
     * @date 2022/1/11 3:35 PM
     */
    void delete(Integer pageId, String fieldCode);

    /***
     * 根据id删除
     *
     * @param id 主键id
     * @author lengfj
     * @date 2022/1/12 9:31 AM
     */
    void delete(Integer id);

    /***
     * 修改页面时处理字段
     *
     * @param pageId 页面id
     * @param fieldDtos 字段信息
     * @return
     * @author lengfj
     * @date 2022/1/12 9:15 AM
     */
    void modifyPageFields(Integer pageId, List<PageFieldInfoSaveDTO> fieldDtos);
}
