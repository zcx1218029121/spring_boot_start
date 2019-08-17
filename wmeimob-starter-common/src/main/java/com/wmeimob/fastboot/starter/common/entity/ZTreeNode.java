package com.wmeimob.fastboot.starter.common.entity;


import java.io.Serializable;

/**
 *  tree
 *  pid :  id of parents
 *  id: key
 */
public class ZTreeNode implements Serializable {

    private Integer id;
    private Integer pId;
    private String title;
    /** @deprecated */
    @Deprecated
    private String name;
    private boolean checked;
    private boolean disabled;
    private boolean expand;

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public ZTreeNode() {
    }

    /** @deprecated */
    @Deprecated
    public String getName() {
        return this.name;
    }

    /** @deprecated */
    @Deprecated
    public void setName(String name) {
        this.name = name;
    }

    public ZTreeNode(Integer id, Integer pId, String title, boolean checked, boolean disabled, boolean expand) {
        this.id = id;
        this.pId = pId;
        this.title = title;
        this.checked = checked;
        this.disabled = disabled;
        this.expand = expand;
    }

    public ZTreeNode(Integer id, boolean checked) {
        this.id = id;
        this.checked = checked;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPId() {
        return this.pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
