package com.show.seckill.redis.keys;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 08:03
 * @Description:
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {//0默认为永久有效
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public int expireSeconds() {
        return expireSeconds;
    }

    public String getPrefix() {//获取前缀
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

}
