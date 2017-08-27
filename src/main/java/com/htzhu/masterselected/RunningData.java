package com.htzhu.masterselected;

import java.io.Serializable;

/**
 * Created by htzhu on 2017/8/27.
 */
public class RunningData implements Serializable {

    private static final long serialVersionUID = -5073166427290163532L;

    private Long cid;

    private String name;

    public RunningData() {
    }

    public RunningData(Long cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
