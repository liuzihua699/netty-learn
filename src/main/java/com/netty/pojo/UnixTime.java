package com.netty.pojo;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName UnixTime
 * @Description TODO
 * @Author 刘子华
 * @Date 2019/7/13 18:13
 */

public class UnixTime {

    @Getter
    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "UnixTime{" +
                "value=" + new Date((getValue() - 2208988800L) * 1000L).toString() +
                '}';
    }
}
