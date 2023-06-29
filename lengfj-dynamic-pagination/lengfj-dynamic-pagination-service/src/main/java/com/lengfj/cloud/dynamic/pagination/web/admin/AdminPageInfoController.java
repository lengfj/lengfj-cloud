package com.lengfj.cloud.dynamic.pagination.web.admin;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.common.mybatis.page.PageView;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageInfoQueryDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageInfoSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoFieldConfigVO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoVO;
import com.lengfj.cloud.dynamic.pagination.service.PageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin/pageInfo")
@RestController
public class AdminPageInfoController {

    @Autowired
    private PageInfoService pageInfoService;

    /***
     * 列表
     *
     * @param pageCode 页面code
     * @param pageName 页面名称
     * @author lengfj
     * @date 2022/1/11 9:14 AM
     */
    @GetMapping("/list")
    public R<List<PageInfoVO>> list(@RequestParam(value = "pageCode", required = false) String pageCode,
                                    @RequestParam(value = "pageName", required = false) String pageName){
        return R.data(pageInfoService.list(pageCode, pageName));
    }

    /***
     * 分页列表
     *
     * @param queryDTO 分页参数
     * @author lengfj
     * @date 2022/1/11 9:47 AM
     */
    @PostMapping("/pageList")
    public R<PageView<PageInfoVO>> pageList(@RequestBody PageInfoQueryDTO queryDTO){
        return R.data(pageInfoService.pageList(queryDTO));
    }

    /***
     * 详情
     *
     * @param id 页面id
     * @param pageCode 页面code
     * @author lengfj
     * @date 2022/1/11 3:46 PM
     */
    @GetMapping("/detail")
    public R<PageInfoFieldConfigVO> detail(@RequestParam(value = "id", required = false) Integer id,
                                        @RequestParam(value = "pageCode", required = false) String pageCode){
        return R.data(pageInfoService.detail(id, pageCode));
    }

    /***
     * 新增
     *
     * @param saveDTO 保存参数
     * @author lengfj
     * @date 2022/1/11 2:50 PM
     */
    @PostMapping("/save")
    public R<Object> save(@RequestBody PageInfoSaveDTO saveDTO){
        pageInfoService.save(saveDTO);
        return R.success("保存成功");
    }

    /***
     * 修改
     *
     * @param saveDTO 保存参数
     * @author lengfj
     * @date 2022/1/11 2:50 PM
     */
    @PostMapping("modify")
    public R<Object> modify(@RequestBody PageInfoSaveDTO saveDTO){
        pageInfoService.modify(saveDTO);
        return R.success("修改成功");
    }

    /***
     * 删除
     *
     * @param id 主键id
     * @author lengfj
     * @date 2022/1/11 7:10 PM
     */
    @PostMapping("/delete/{id}")
    public R<Object> delete(@PathVariable("id") Integer id){
        pageInfoService.delete(id);
        return R.success("删除成功");
    }

}
