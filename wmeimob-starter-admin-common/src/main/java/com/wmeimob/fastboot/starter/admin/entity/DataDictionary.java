package com.wmeimob.fastboot.starter.admin.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(
        name = "data_dictionary"
)
public class DataDictionary implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "dict_name"
    )
    private String dictName;
    private String description;
    private static final long serialVersionUID = 1L;

    public DataDictionary() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictName() {
        return this.dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }
}
