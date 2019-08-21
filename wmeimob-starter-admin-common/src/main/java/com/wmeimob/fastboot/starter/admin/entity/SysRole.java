package com.wmeimob.fastboot.starter.admin.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(
        name = "sys_role"
)
public class SysRole implements Serializable, GrantedAuthority {
    public static final Integer ROLE_TYPE_SUPER = 10003;
    public static final Integer ROLE_TYPE_NORMAL = 10004;
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "role_code"
    )
    private String roleCode;
    @Column(
            name = "role_name"
    )
    private String roleName;
    @Column(
            name = "role_type"
    )
    private Integer roleType;
    @Column(
            name = "is_enable"
    )
    private Boolean isEnable;
    @Column(
            name = "updated_at"
    )
    private Date updatedAt;
    @Column(
            name = "created_at"
    )
    private Date createdAt;
    @Column(
            name = "created_role"
    )
    private Integer createdRole;
    @Column(
            name = "created_user"
    )
    private Integer createdUser;
    private static final long serialVersionUID = 1L;

    public SysRole() {
    }

    public SysRole(SysRole sysRole) {
        this.id = sysRole.getId();
        this.roleCode = sysRole.getRoleCode();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleType() {
        return this.roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedRole() {
        return this.createdRole;
    }

    public void setCreatedRole(Integer createdRole) {
        this.createdRole = createdRole;
    }

    public Integer getCreatedUser() {
        return this.createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    public String getAuthority() {
        return this.getRoleCode();
    }
}
