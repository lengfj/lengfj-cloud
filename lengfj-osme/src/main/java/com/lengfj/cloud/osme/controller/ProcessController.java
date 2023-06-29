package com.lengfj.cloud.osme.controller;


import com.lengfj.cloud.osme.service.ProcessEventStateScenarioService;
import com.lengfj.cloud.osme.service.ProcessService;
import com.lengfj.cloud.osme.entity.ProcessEventStateScenario;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * 流程表(Process)表控制层
 *
 * @author lengfj
 * @since 2021-12-28 16:54:11
 */
@RestController
@RequestMapping("process")
public class ProcessController {
    /**
     * 服务对象
     */
    @Resource
    private ProcessService processService;

    @Resource
    private ProcessEventStateScenarioService processEventStateScenarioService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Process selectOne(@PathVariable Serializable id) {

        Process process = processService.selectById(id);
        return process;
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/ProcessEventStateScenario/{id}")
    public ProcessEventStateScenario selectOne1(@PathVariable Serializable id) {

        ProcessEventStateScenario processEventStateScenario = processEventStateScenarioService.selectById(id);
        return processEventStateScenario;
    }

}
