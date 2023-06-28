package com.lengfj.cloud.osme.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.osme.dao.EventDao;
import com.lengfj.cloud.osme.entity.Event;
import com.lengfj.cloud.osme.service.EventService;
import org.springframework.stereotype.Service;

/**
 * 事件表(Event)表服务实现类
 *
 * @author lengfj
 * @since 2021-12-28 16:53:32
 */
@Service("eventService")
public class EventServiceImpl extends ServiceImpl<EventDao, Event> implements EventService {

}
