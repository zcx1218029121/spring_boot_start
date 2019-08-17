package com.wmeimob.fastboot.starter.common.service;

import com.wmeimob.fastboot.starter.common.entity.RichText;

public interface RichTextService {
    // 缓存key
    String CACHE_KEY_FORMAT = "richText:%s";

    default void add(RichText richText) {
    }

    default int delete(Integer id) {
        return -1;
    }

    default int update(RichText richText) {
        return -1;
    }

    default RichText findById(Integer id) {
        return new RichText();
    }

    default RichText securityGet(Integer id, RichTextAuthChecker richTextAuthChecker) {
        return new RichText();
    }
}
