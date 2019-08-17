package com.wmeimob.fastboot.starter.common.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/** 又到了我最喜欢的增删改查
 *   tk.mybatis 的标准用法
 *   用的是自动生成的
 *
 * @author loafer
 */
@Table(
        name = "rich_text"
        //如果实体类名字与数据库不一致又不使用注解会报错
)
public class RichText implements Serializable {
    public RichText(){

    }


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "data_id"
    )
    private Integer dataId;
    @Column(
            name = "gmt_create"
    )
    private Date gmtCreate;
    @Column(
            name = "gmt_modified"
    )
    private Date gmtModified;
    private String content;
    private static final long serialVersionUID = 1L;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public Integer getDataId() {
        return dataId;
    }

    public Integer getId() {
        return id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
