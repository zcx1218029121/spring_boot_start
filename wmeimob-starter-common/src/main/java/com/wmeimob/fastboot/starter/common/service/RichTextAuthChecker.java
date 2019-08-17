package com.wmeimob.fastboot.starter.common.service;

//回调接口 根据传入的值返回 布尔值
//有必要解释一下 FunctionalInterface 这个接口是检查函数式编程的。
@FunctionalInterface
public interface RichTextAuthChecker {
    boolean assertHasAuth(Integer var1);
}
