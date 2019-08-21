package com.wmeimob.fastboot.starter.admin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.springframework.util.StringUtils;
/**
 * @author loafer
 */
@SuppressWarnings("unused")
@Table(
        name = "sys_menu"
)
public class SysMenu implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "parent_id"
    )
    private Integer parentId;
    private String icon;
    @Column(
            name = "menu_code"
    )
    private String menuCode;
    @Column(
            name = "menu_name"
    )
    private String menuName;
    @Transient
    private String excludeRole;
    @Column(
            name = "menu_describe"
    )
    private String menuDescribe;
    @Column(
            name = "menu_href"
    )
    private String menuHref;
    @Column(
            name = "query_string"
    )
    private String queryString;
    @Column(
            name = "created_at"
    )
    private Date createdAt;
    @Column(
            name = "updated_at"
    )
    private Date updatedAt;
    @Column(
            name = "order_no"
    )
    private Integer orderNo;
    @Column(
            name = "is_location"
    )
    private Boolean isLocation;
    private static final long serialVersionUID = 1L;
    @Transient
    private List<SysMenu> sysMenus;
    @Transient
    private Integer roleId;

    public SysMenu() {
    }

    public String getExcludeRole() {
        return this.excludeRole;
    }

    public void setExcludeRole(String excludeRole) {
        this.excludeRole = excludeRole;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMenuCode() {
        return this.menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescribe() {
        return this.menuDescribe;
    }

    public void setMenuDescribe(String menuDescribe) {
        this.menuDescribe = menuDescribe;
    }

    public String getMenuHref() {
        return this.menuHref;
    }

    public void setMenuHref(String menuHref) {
        this.menuHref = menuHref;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsLocation() {
        return this.isLocation;
    }

    public void setIsLocation(Boolean isLocation) {
        this.isLocation = isLocation;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<SysMenu> getSysMenus() {
        return this.sysMenus;
    }

    public void setSysMenus(List<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }

    public static Map<String, SysMenu> convertMenuIconMap(List<SysMenu> fnList) {
        Map<String, SysMenu> map = new HashMap();
        Iterator<SysMenu> iterator = fnList.iterator();
        Iterator<SysMenu> iteratorChild = null;
        SysMenu sysFnRoot = null;
        SysMenu sysFnChild = null;

        while (true) {
            do {
                do {
                    if (!iterator.hasNext()) {
                        return map;
                    }

                    sysFnRoot = (SysMenu) iterator.next();
                } while (sysFnRoot.getSysMenus() == null);
            } while (sysFnRoot.getSysMenus().size() <= 0);

            iteratorChild = sysFnRoot.getSysMenus().iterator();

            while (iteratorChild.hasNext()) {
                sysFnChild = (SysMenu) iteratorChild.next();
                map.put(sysFnChild.getMenuHref() + (StringUtils.isEmpty(sysFnChild.getQueryString()) ? "" : "?" + sysFnChild.getQueryString()), sysFnChild);
            }
        }
    }
}
