package com.line.jsbundlepatch.model;

import java.util.List;

/**
 * Created by chenliu on 2018/4/17.
 */

public class Module {
    /**
     * 模块名称，eg:m1
     */
    private String moduleName;
    /**
     * 是否是全量设备 true 全量设备 false开发设备
     */
    private boolean allDevice;

    /**
     * 每个最小支持版本对应的最新bundle
     */
    private List<JsBundle> latestBundles;

    /**
     * 所有patch文件
     */
    private List<JsBundlePatch> jsBundlePatches;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<JsBundle> getLatestBundles() {
        return latestBundles;
    }

    public void setLatestBundles(List<JsBundle> latestBundles) {
        this.latestBundles = latestBundles;
    }

    public List<JsBundlePatch> getJsBundlePatches() {
        return jsBundlePatches;
    }

    public void setJsBundlePatches(List<JsBundlePatch> jsBundlePatches) {
        this.jsBundlePatches = jsBundlePatches;
    }

    public boolean isAllDevice() {
        return allDevice;
    }

    public void setAllDevice(boolean allDevice) {
        this.allDevice = allDevice;
    }

}
