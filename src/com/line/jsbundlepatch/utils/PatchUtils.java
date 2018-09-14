package com.line.jsbundlepatch.utils;

import java.util.LinkedList;

/**
 * 补丁生成
 * @author chenliu
 *
 */
public class PatchUtils {
	private static final String TAG = "PatchUtils";
	
	public static void patch(String oldPath, String newPath, String patchPath) {
		String oldContent = PatchUtils.getFileContent(oldPath);
		String newContent = PatchUtils.getFileContent(newPath);
		String patchStr = PatchUtils.getPatchStr(oldContent, newContent);
		Log.log(TAG, "patchStr: " + patchStr.length());
		Log.log(TAG, "patchPath:" + patchPath);
		createPatchFile(patchPath, patchStr);
	}

	/**
	 * 获取文件差异
	 * @param oldSrc
	 * @param newSrc
	 * @return
	 */
	public static String getPatchStr(String oldSrc, String newSrc) {
		// 1.对比
		diff_match_patch dmp = new diff_match_patch();
		LinkedList<diff_match_patch.Diff> diffs = dmp.diff_main(oldSrc, newSrc);
		// 2.生成差异补丁包
		LinkedList<diff_match_patch.Patch> patches = dmp.patch_make(diffs);
		// 3.解析补丁包
		return dmp.patch_toText(patches);
	}
	
	/**
	 * 获取文件内容
	 * @param path
	 * @return
	 */
	public static String getFileContent(String path) {
		return FileUtils.getFileContent(path);
	}
	
	/**
	 * 生成patch文件
	 * @param path
	 * @param patchStr
	 */
	public static void createPatchFile(String path, String patchStr) {
		if(patchStr != null && patchStr.length() > 0)
			FileUtils.createFile(path, patchStr);
	}
	
	/**
	 * 获取jsbundle version
	 * @param fileName eg:m1.android.jsbundle
	 * @return
	 */
	public static String getJsBundleVersion(String fileName) {
		int index = fileName.indexOf("_");
		return fileName.substring(index + 1, fileName.length());
	}

	/**
	 * 获取patch version
	 * @param fileName eg:m1.patch_01_02
	 * @return
	 */
	public static String getPatchVersion(String fileName){
		int index = fileName.indexOf("_");
		return fileName.substring(index + 1, fileName.length());
	}
	
	public static String getPatchName(String moduleName, String lBundleName, String cBundleName) {
		String lversion = getJsBundleVersion(lBundleName);
		String cversion = getJsBundleVersion(cBundleName);
		return moduleName + ".patch_" + lversion + "_" + cversion;
	}

}
