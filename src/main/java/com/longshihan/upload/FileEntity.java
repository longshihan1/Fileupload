package com.longshihan.upload;

import java.io.Serializable;

public class FileEntity implements Serializable {
    private String filePath;

    public FileEntity(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "文件名称："+getFilePath();
    }
}
