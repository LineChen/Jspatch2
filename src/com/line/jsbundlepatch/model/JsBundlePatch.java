package com.line.jsbundlepatch.model;

/**
 * Created by chenliu on 2018/4/16.
 */

public class JsBundlePatch {
    private String name;
    private String version;
    private String downloadUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
