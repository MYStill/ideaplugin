package com.utils.log.service;

public interface LogService {

    public int level = 0; // 日志等级

    public String getName();

    public void writeLog();
}
