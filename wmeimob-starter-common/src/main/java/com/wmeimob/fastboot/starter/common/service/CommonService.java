package com.wmeimob.fastboot.starter.common.service;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;

public interface CommonService<T extends Serializable> {
    default T add(T object) {
        throw this.notImplementException("add");
    }

    default List<T> add(List<T> objectList) {
        throw this.notImplementException("add list");
    }

    default int delete(Integer id) {
        throw this.notImplementException("delete by id");
    }

    default int delete(Integer[] idArr) {
        throw this.notImplementException("delete by int id arr");
    }

    default int delete(String[] idArr) {
        throw this.notImplementException("delete by string id arr");
    }

    default int delete(String id) {
        throw this.notImplementException("delete by string id");
    }

    default int delete(Integer id, T condition) {
        throw this.notImplementException("delete with condition by id");
    }

    default int delete(String id, T condition) {
        throw this.notImplementException("delete with condition by string id");
    }

    default int update(T updateObject) {
        throw this.notImplementException("update");
    }

    default int update(T updateObject, T condition) {
        throw this.notImplementException("update with condition");
    }

    default T findById(Integer id) {
        throw this.notImplementException("find by id");
    }

    default T findById(String id) {
        throw this.notImplementException("find by string id");
    }

    default T findOneByCondition(T condition) {
        throw this.notImplementException("find one by condition");
    }

    default List<T> findByCondition(T condition) {
        throw this.notImplementException("find by condition");
    }

    default List<T> findByCondition(Object conditionDTO) {
        throw this.notImplementException("find by condition");
    }

    default List<T> findByCondition(List<T> conditions) {
        throw this.notImplementException("find by conditions");
    }

    default NotImplementedException notImplementException(String methodName) {
        return new NotImplementedException("The " + methodName + " method is not implemented in " + this.getClass().getName());
    }
}
