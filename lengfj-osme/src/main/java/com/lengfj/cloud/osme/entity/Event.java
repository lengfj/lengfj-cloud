package com.lengfj.cloud.osme.entity;



import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 事件表(Event)表实体类
 *
 * @author lengfj
 * @since 2021-12-28 16:53:30
 */
@SuppressWarnings("serial")
public class Event extends Model<Event> {

    private Long id;

    private String code;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
