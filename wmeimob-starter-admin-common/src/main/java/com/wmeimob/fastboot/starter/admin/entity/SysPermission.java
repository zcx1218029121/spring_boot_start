package com.wmeimob.fastboot.starter.admin.entity;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.access.ConfigAttribute;

@Table(
        name = "sys_permission"
)
public class SysPermission implements ConfigAttribute {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "resource_id"
    )
    private Integer resourceId;
    @Column(
            name = "role_id"
    )
    private Integer roleId;
    @Column(
            name = "allow_get"
    )
    private Boolean allowGet;
    @Column(
            name = "allow_post"
    )
    private Boolean allowPost;
    @Column(
            name = "allow_put"
    )
    private Boolean allowPut;
    @Column(
            name = "allow_delete"
    )
    private Boolean allowDelete;
    @Column(
            name = "updated_at"
    )
    private Date updatedAt;
    @Column(
            name = "created_at"
    )
    private Date createdAt;
    private static final long serialVersionUID = 1L;
    @Transient
    private String roleCode;
    @Transient
    private String resourceUrl;
    @Transient
    private String roleName;
    @Transient
    private String resourceName;
    @Transient
    private boolean invalidPermission;
    @Transient
    private List<SysPermission> permissions;

    public SysPermission() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getAllowGet() {
        return this.allowGet;
    }

    public void setAllowGet(Boolean allowGet) {
        this.allowGet = allowGet;
    }

    public Boolean getAllowPost() {
        return this.allowPost;
    }

    public void setAllowPost(Boolean allowPost) {
        this.allowPost = allowPost;
    }

    public Boolean getAllowPut() {
        return this.allowPut;
    }

    public void setAllowPut(Boolean allowPut) {
        this.allowPut = allowPut;
    }

    public Boolean getAllowDelete() {
        return this.allowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
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

    public List<SysPermission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public boolean isInvalidPermission() {
        return this.invalidPermission;
    }

    public void setInvalidPermission(boolean invalidPermission) {
        this.invalidPermission = invalidPermission;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getResourceUrl() {
        return this.resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getAttribute() {
        return this.getRoleCode();
    }
}
