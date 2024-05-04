package com.mashibing.internalcommon.utils;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-26 20:42
 **/
public class SsePrefixUtils {
    public static final String separator = "$";

    public static String generatorSseKey(Long userId,String identity){
        return userId + separator + identity;
    }
}
