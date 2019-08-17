package com.wmeimob.fastboot.starter.common.entity;

import javax.persistence.Table;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "simple_config"
)
public class SimpleConfig implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "config_name"
    )
    private String configName;
    @Column(
            name = "config_value"
    )
    private String configValue;
    @Column(
            name = "config_type"
    )
    private Integer configType;
    private static final long serialVersionUID = 1L;

    public SimpleConfig() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigName() {
        return this.configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return this.configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Integer getConfigType() {
        return this.configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }
}
