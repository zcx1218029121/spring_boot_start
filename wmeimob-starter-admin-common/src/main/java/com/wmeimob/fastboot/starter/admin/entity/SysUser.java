package com.wmeimob.fastboot.starter.admin.entity;

import com.wmeimob.fastboot.util.InputValidator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(
        name = "sys_user"
)
public class SysUser implements Serializable, UserDetails {
    public static final Integer USER_TYPE_SUPER = 10001;
    public static final Integer USER_TYPE_CHILD = 10002;
    public static final Integer USER_TYPE_ROOT = 10005;
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private Integer uid;
    private String username;
    private String nickname;
    private String pwd;
    private String salt;
    private String tel;
    @Column(
            name = "user_type"
    )
    private Integer userType;
    @Column(
            name = "role_id"
    )
    private Integer roleId;
    @Column(
            name = "role_name"
    )
    private String roleName;
    @Column(
            name = "is_enabled"
    )
    private Boolean isEnabled;
    @Column(
            name = "is_locked"
    )
    private Boolean isLocked;
    @Column(
            name = "created_user"
    )
    private Integer createdUser;
    @Column(
            name = "enable_data_role"
    )
    private Boolean enableDataRole;
    @Column(
            name = "created_at"
    )
    private Date createdAt;
    @Column(
            name = "last_login_date"
    )
    private Date lastLoginDate;
    @Column(
            name = "last_login_ip"
    )
    private String lastLoginIp;
    private static final long serialVersionUID = 1L;
    @Transient
    private List<SysRole> roles;
    @Transient
    private List<SysUserDataRole> dataRoles;
    @Transient
    private List<GrantedAuthority> authorities;

    public SysUser(Integer id, Integer uid, String username, Integer userType, List<GrantedAuthority> authorities, List<SysUserDataRole> dataRoles) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.userType = userType;
        this.authorities = authorities;
        this.dataRoles = dataRoles;
    }

    public SysUser() {
    }

    public List<SysUserDataRole> getDataRoles() {
        return this.dataRoles;
    }

    public void setDataRoles(List<SysUserDataRole> dataRoles) {
        this.dataRoles = dataRoles;
    }

    public List<SysRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.getPwd();
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return this.getIsLocked() != null && !this.getIsLocked();
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.getIsEnabled() == null ? false : this.getIsEnabled();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(Boolean isEnable) {
        this.isEnabled = isEnable;
    }

    public Boolean getIsLocked() {
        return this.isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getCreatedUser() {
        return this.createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    public Boolean getEnableDataRole() {
        return this.enableDataRole;
    }

    public void setEnableDataRole(Boolean enableDataRole) {
        this.enableDataRole = enableDataRole;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public static String validSave(SysUser user) {
        if (!InputValidator.isUsername(user.getUsername())) {
            return "用户名含有非法字符";
        } else if ("system".equals(user.getUsername())) {
            return "您不能使用这个名字";
        } else if (!user.getNickname().contains("<") && !user.getNickname().contains(">")) {
            if (!Objects.equals(user.getTel(), "") && !InputValidator.isMobile(user.getTel())) {
                return "联系电话不合法 ";
            } else if (user.getRoleId() == null) {
                return "角色没有指定";
            } else {
                if (user.getIsLocked() == null) {
                    user.setIsLocked(false);
                }

                if (user.getIsEnabled() == null) {
                    user.setIsEnabled(false);
                }

                if (user.getEnableDataRole() == null) {
                    user.setEnableDataRole(false);
                }

                return null;
            }
        } else {
            return "昵称含有非法字符";
        }
    }
}
