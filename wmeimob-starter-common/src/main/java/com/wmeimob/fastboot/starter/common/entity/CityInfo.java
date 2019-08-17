package com.wmeimob.fastboot.starter.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(
        name = "city_info"
)
public class CityInfo implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private String name;
    private Integer type;
    private Integer pid;
    @Transient
    private Boolean checked;
    @Transient
    private Boolean checkAll;
    @Transient
    private List<CityInfo> children;
    private static final long serialVersionUID = 1L;

    public CityInfo() {
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getCheckAll() {
        return this.checkAll;
    }

    public void setCheckAll(Boolean checkAll) {
        this.checkAll = checkAll;
    }

    public List<CityInfo> getChildren() {
        return this.children;
    }

    public void setChildren(List<CityInfo> children) {
        this.children = children;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}