package com.wmeimob.fastboot.starter.common.mapper;

import com.wmeimob.fastboot.core.orm.Mapper;
import com.wmeimob.fastboot.starter.common.entity.CityInfo;
import com.wmeimob.fastboot.starter.common.entity.ZTreeNode;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 转换成全注解配置
 * @author loafer
 */

public interface CityInfoMapper extends Mapper<ZTreeNode> {
    @Select({"SELECT * FROM city_info WHERE  pid = #{pid} ORDER BY `id`"})
    @Results(
            id = "TreeMap",
            value = {
                    @Result(id = true,
                            column = "id", property = "id", jdbcType = JdbcType.INTEGER),
                    @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
                    @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
                    @Result(column = "pid", property = "pid", jdbcType = JdbcType.INTEGER),
                    @Result(property = "children", column = "id", javaType = CityInfo.class, many = @Many(select = "selectByParentId")),
            }
    )
    List<CityInfo> selectByParentId(Integer parentId);

    @Select({"SELECT * FROM city_info WHERE `type` = #{type} ORDER BY `id`"})
    @ResultMap({"TreeMap"})
    List<CityInfo> selectByType(Integer type);
}
