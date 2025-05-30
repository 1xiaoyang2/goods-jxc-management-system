package com.yang.jxc.utils;

import java.util.UUID;

public class UUidUtils {

    /**
     * 生成 9位uuid
     * @return
     */
    public static Integer  uuid() {
        Integer uuid = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;//String.hashCode() 值会为空
          uuid = Integer.valueOf(uuid.toString().substring(0,9));
        return  uuid;
    }

    public static void main(String[] args) {
        Integer uuid = uuid();
        System.out.println(uuid+"\n长度："+uuid.toString().length());
    }
}
