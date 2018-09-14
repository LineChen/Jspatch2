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
}
