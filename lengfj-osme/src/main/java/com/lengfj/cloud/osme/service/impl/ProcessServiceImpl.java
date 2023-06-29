package com.lengfj.cloud.osme.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.osme.dao.ProcessDao;
import com.lengfj.cloud.osme.entity.Process;
import com.lengfj.cloud.osme.service.ProcessService;
import org.springframework.stereotype.Service;


/**
 * 流程表(Process)表服务实现类
 *
 * @author lengfj
 * @since 2021-12-28 16:54:11
 */
@Service("processService")
public class ProcessServiceImpl extends ServiceImpl<ProcessDao, Process> implements ProcessService {

}
