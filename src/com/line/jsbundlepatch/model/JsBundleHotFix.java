package com.line.jsbundlepatch.model;

import java.util.List;

/**
 * Created by chenliu on 2018/4/16.
 */
@Deprecated
public class JsBundleHotFix {
    private String minAppVersion;
    private List<Module> moduleList;//每个模块最新的jsbundle版本

    public String getMinAppVersion() {
        return minAppVersion;
    }

    public void setMinAppVersion(String minAppVersion) {
        this.minAppVersion = minAppVersion;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
}
