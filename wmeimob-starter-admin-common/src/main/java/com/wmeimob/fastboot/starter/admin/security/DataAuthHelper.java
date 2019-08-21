package com.wmeimob.fastboot.starter.admin.security;

import com.alibaba.fastjson.JSONArray;
import com.wmeimob.fastboot.starter.admin.entity.SysUser;
import com.wmeimob.fastboot.starter.admin.entity.SysUserDataRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @author
 */
@Component
public class DataAuthHelper {
    public static final String DATA_AUTH_BY_MP_ID = "BY_MP_ID";
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public DataAuthHelper(StringRedisTemplate stringRedisTemplate){
        this.redisTemplate=stringRedisTemplate;
    }

    /**
     * 从springSecurity context 获取 userDetails
     *
     * 命名非常糟糕 应该改成 getUserDataRoleValue
     * 但是为了避免现有项目调用上的不兼容 不改动项目名称。
     * @param authCode authCode
     * @return  权限値 （如果没有权限就返回空）
     */
    @Nullable
    public String getByUserDetails(String authCode) {
        // 从springSecurity 上下文中获取userDetails
        SysUser userDetails = (SysUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        // 从redis 中获取当前用户的 权限表
        List<SysUserDataRole> sysUserDataRoleList = JSONArray.parseArray(this.redisTemplate.opsForValue().get("userDataRoles:" + userDetails.getUsername()), SysUserDataRole.class);
        return getUserDataRoleValue(sysUserDataRoleList,authCode);
    }

    /**
     * 从redis 中获取userDetails
     * @param userDetails userDetails
     * @param authCode  authCode
     * @return sysUserDataRole.getColumnValue
     */
    @Nullable
    public String getByUserDetails(UserDetails userDetails, String authCode) {
        List<SysUserDataRole> sysUserDataRoleList = JSONArray.parseArray((String)this.redisTemplate.opsForValue().get("userDataRoles:" + userDetails.getUsername()), SysUserDataRole.class);
       return  getUserDataRoleValue(sysUserDataRoleList,authCode);
    }

    @Nullable
    private  String getUserDataRoleValue(List<SysUserDataRole> sysUserDataRoleList,String authCode){
        if (sysUserDataRoleList == null) {
            return null;
        } else {
            Iterator iterator = sysUserDataRoleList.iterator();

            SysUserDataRole sysUserDataRole;
            do {
                if (!iterator.hasNext()) {
                    return null;
                }

                sysUserDataRole = (SysUserDataRole)iterator.next();
            } while(!authCode.equals(sysUserDataRole.getDataRoleCode()));

            return sysUserDataRole.getColumnValue();
        }
    }


    public String getMpId(UserDetails userDetails) {
        return this.getByUserDetails(userDetails, DATA_AUTH_BY_MP_ID);
    }

    public String getMpId() {
        return this.getByUserDetails(DATA_AUTH_BY_MP_ID);
    }

}
