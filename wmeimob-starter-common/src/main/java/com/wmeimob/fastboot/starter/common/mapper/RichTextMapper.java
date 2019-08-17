package com.wmeimob.fastboot.starter.common.mapper;



import com.wmeimob.fastboot.core.orm.Mapper;
import com.wmeimob.fastboot.starter.common.entity.RichText;

/**
 * 有必要解释一下com.wmeimob.fastboot.core.orm.Mapper
 *  这个注解只是继承了
 *  tk.mybatis.mapper.common.Mapper<T>, MySqlMapper<T>
 *  这样就继承一个
 *
 */
public interface RichTextMapper extends Mapper<RichText> {

}