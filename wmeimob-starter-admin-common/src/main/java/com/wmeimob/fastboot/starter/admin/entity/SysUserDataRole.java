package com.wmeimob.fastboot.starter.admin.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(
        name = "sys_user_data_role"
)
public class SysUserDataRole implements Serializable {
    public static final String QUERY_DATA_ROLE_METHOD = "selectForDataRole";
    public static final Integer DATA_ROLE_TYPE_ADMIN = 1;
    public static final Integer DATA_ROLE_TYPE_CHILDREN = 0;
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "sys_user_id"
    )
    private Integer sysUserId;
    @Column(
            name = "prefix_name"
    )
    private String prefixName;
    @Transient
    private String queryUrl;
    @Column(
            name = "column_name"
    )
    private String columnName;
    @Column(
            name = "column_value"
    )
    private String columnValue;
    @Column(
            name = "data_role_code"
    )
    private String dataRoleCode;
    @Column(
            name = "extends_user"
    )
    private Integer extendsUser;
    @Transient
    private String roleName;
    @Transient
    private Integer level;
    @Transient
    private String url;
    @Transient
    private List<Object> dataSelects;
    @Transient
    private SysUserDataRole[] dataRoles;
    private static final long serialVersionUID = 1L;

    public SysUserDataRole() {
    }

    public SysUserDataRole[] getDataRoles() {
        return this.dataRoles;
    }

    public void setDataRoles(SysUserDataRole[] dataRoles) {
        this.dataRoles = dataRoles;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Object> getDataSelects() {
        return this.dataSelects;
    }

    public void setDataSelects(List<Object> dataSelects) {
        this.dataSelects = dataSelects;
    }

    public String getDataRoleCode() {
        return this.dataRoleCode;
    }

    public void setDataRoleCode(String dataRoleCode) {
        this.dataRoleCode = dataRoleCode;
    }

    public String getQueryUrl() {
        return this.queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysUserId() {
        return this.sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getPrefixName() {
        return this.prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return this.columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public Integer getExtendsUser() {
        return this.extendsUser;
    }

    public void setExtendsUser(Integer extendsUser) {
        this.extendsUser = extendsUser;
    }
}