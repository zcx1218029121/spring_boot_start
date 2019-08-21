package com.wmeimob.fastboot.starter.admin.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(
        name = "sys_menu_role"
)
public class SysMenuRole implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "menu_id"
    )
    private Integer menuId;
    @Column(
            name = "role_id"
    )
    private Integer roleId;
    private static final long serialVersionUID = 1L;

    public SysMenuRole() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}