package com.lengfj.cloud.osme.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程事件状态场景关联表(ProcessEventStateScenario)表实体类
 *
 * @author lengfj
 * @since 2021-12-28 16:54:20
 */
@Data
public class ProcessEventStateScenario extends Model<ProcessEventStateScenario> {

    private Long id;

    private Long processId;

    private Long stateId;

    private Long eventId;

    private Long scenarioId;
    //下一个状态
    private Integer nextState;

    private String processorName;



    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
