package com.line.jsbundlepatch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.line.jsbundlepatch.JsPatchMain.ENV;
import com.line.jsbundlepatch.model.JsBundle;
import com.line.jsbundlepatch.model.JsBundlePatch;
import com.line.jsbundlepatch.model.Module;
import com.line.jsbundlepatch.utils.FileUtils;
import com.line.jsbundlepatch.utils.Log;
import com.line.jsbundlepatch.utils.PatchUtils;

public class JsonCreator {
	
	private static final String TAG = "JsonCreator";
	
	private static String BUNDLE_PATCH_BUILD_FILE_NAME = "update.json";
	
	private static final String DOWNLOAD_BASE_URL_PRE = "http://pre.xiaoqishen.cn/static/jsbundle/android/";
	
	private static final String DOWNLOAD_BASE_URL_RELEASE = "http://cdn.xiaoqishen.cn/static/jsbundle/android/";
	
	private static String DOWNLOAD_BASE_URL;
	
	public static void createBuildJson(String env, String buildRootDirPath) {
		if(ENV.PRE.equals(env)) {
			DOWNLOAD_BASE_URL = DOWNLOAD_BASE_URL_PRE;
		} else if(ENV.RELEASE.equals(env)) {
			DOWNLOAD_BASE_URL = DOWNLOAD_BASE_URL_RELEASE;
		}
		try {
			List<Module> moduleList = new ArrayList<>();
			File buildRootDir = new File(buildRootDirPath);//module list
			File[] moduleDirList = buildRootDir.listFiles();
			if(moduleDirList != null && moduleDirList.length > 0) {
				Log.log(TAG,  "module count：" + moduleDirList.length);
				for(File moduleDir : moduleDirList) {
					Module module = new Module();
					module.setModuleName(moduleDir.getName());
					List<JsBundle> latestBundles = getModuleLatestBundles(moduleDir);
					module.setLatestBundles(latestBundles);
					
					List<JsBundlePatch> bundlePatchs = getModuleJsPatches(moduleDir);
					module.setJsBundlePatches(bundlePatchs);
					moduleList.add(module);
				}
			}
			String bundle_patch_json = JSON.toJSONString(moduleList);
			Log.log(TAG, "andriod_bundle_patch_json ok");
			FileUtils.createFile(buildRootDir.getParentFile().getAbsolutePath() + File.separator + BUNDLE_PATCH_BUILD_FILE_NAME, bundle_patch_json);
		} catch(Exception e) {
			e.printStackTrace();
			Log.log(TAG, e.getMessage());
		}
	}

	/**
	 * 获取某个模块下的所有patch文件
	 * @param moduleDir
	 * @return
	 */
	private static List<JsBundlePatch> getModuleJsPatches(File moduleDir) {
		List<JsBundlePatch> bundlePatchs = new ArrayList<>();
		File patchDir = new File(moduleDir.getAbsolutePath(), JsPatchMain.JSPATCH_DIR);
		File[] patchFiles = patchDir.listFiles();
		if(patchFiles != null && patchFiles.length > 0) {
			for (File patch : patchFiles) {
				JsBundlePatch jsBundlePatch = new JsBundlePatch();
				jsBundlePatch.setName(patch.getName());
				jsBundlePatch.setVersion(PatchUtils.getPatchVersion(patch.getName()));
				jsBundlePatch.setDownloadUrl(DOWNLOAD_BASE_URL + getFileShortPath(patch.getAbsolutePath()));
				bundlePatchs.add(jsBundlePatch);
			}
		}
		return bundlePatchs;
	}

	/**
	 * 获取该模块的每个版本最新的jsbundle
	 * @param moduleDir
	 * @return
	 */
	private static List<JsBundle> getModuleLatestBundles(File moduleDir) {
		File jsbundleDir = new File(moduleDir, JsPatchMain.JSBUNDLE_DIR);
		File[] appVersionDirList = jsbundleDir.listFiles();
		List<JsBundle> latestBundles = new ArrayList<>();
		if(appVersionDirList != null && appVersionDirList.length > 0) {
			for (File versionDir : appVersionDirList) {
				JsBundle jsbundle = new JsBundle();
				File[] bundleFileList = versionDir.listFiles();
				if(bundleFileList != null && bundleFileList.length > 0) {
					Arrays.sort(bundleFileList, new Comparator<File>() {
						@Override
						public int compare(File o1, File o2) {
							return o1.getName().compareTo(o2.getName());
						}
					});
					File latestJsbundle = bundleFileList[bundleFileList.length - 1];
					jsbundle.setMinAppVersion(versionDir.getName());
					jsbundle.setBundleName(latestJsbundle.getName());
					jsbundle.setBundleVersion(PatchUtils.getJsBundleVersion(latestJsbundle.getName()));
					jsbundle.setDownloadUrl(DOWNLOAD_BASE_URL + getFileShortPath(latestJsbundle.getAbsolutePath()));
				}
				latestBundles.add(jsbundle);
			}
		}
		return latestBundles;
	}
	
	private static String getFileShortPath(String absolutePath) {
		int beginIndex = absolutePath.indexOf("jsbundle-update/android");
		return absolutePath.substring(beginIndex + "jsbundle-update/android".length() + 1);
	}

}
