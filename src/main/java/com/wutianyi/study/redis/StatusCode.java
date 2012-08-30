package com.wutianyi.study.redis;

public enum StatusCode
{
    OK("OK", true);

    private String code;

    private boolean value;

    private StatusCode(String code, boolean value)
    {
        this.code = code;
        this.value = value;
    }

}
