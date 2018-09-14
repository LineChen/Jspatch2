package com.line.jsbundlepatch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.line.jsbundlepatch.utils.FileUtils;
import com.line.jsbundlepatch.utils.Log;
import com.line.jsbundlepatch.utils.PatchUtils;

public class PatchCreator {
	
	private static final String TAG = "PatchCreator";
	
	public static void createPatch(String buildRootDirPath) {
		try{
			File buildRootDir = new File(buildRootDirPath);
			File[] moduleDirList = buildRootDir.listFiles();
			if(moduleDirList == null || moduleDirList.length == 0) return;
			for(File mDir : moduleDirList) {
				String moduleName = mDir.getName();
				File jsbundleDir = new File(mDir, JsPatchMain.JSBUNDLE_DIR);
				File[] appVersionDirList = jsbundleDir.listFiles();
				if(appVersionDirList == null || appVersionDirList.length == 0) return;
				List<String> bundlePathList = new ArrayList<>();
				for (File versionDir : appVersionDirList) {
					File[] bundleFileList = versionDir.listFiles();
					if(bundleFileList != null && bundleFileList.length > 0) {
						for(File bundle : bundleFileList) {
							bundlePathList.add(bundle.getAbsolutePath());
						}
					}
				}
				String patchDir = mDir.getAbsolutePath() + File.separator  + JsPatchMain.JSPATCH_DIR + File.separator;
				patch(moduleName, patchDir, bundlePathList);
			}
		}catch (Exception e){
			Log.log(TAG, e.getMessage());
		}
	}
	
	private static void patch(String moduleName, String patchDir, List<String> bundlePathList) {
		if(bundlePathList != null && bundlePathList.size() > 1) {
			Collections.sort(bundlePathList, new Comparator<String>() {
	            @Override
	            public int compare(String o1, String o2) {
	                return o1.compareTo(o2);
	            }
	        });
	        for(int j = 1, size = bundlePathList.size(); j < size; j++) {
	            File lastFile = new File(bundlePathList.get(j - 1));
	            File curFile = new File(bundlePathList.get(j));
	            String patchName = PatchUtils.getPatchName(moduleName, lastFile.getName(), curFile.getName());
	            String patchPath = patchDir + File.separator + patchName ;
	            if(!FileUtils.exists(patchPath))
	                PatchUtils.patch(lastFile.getAbsolutePath(), curFile.getAbsolutePath(), patchPath);
	        }
		}
	}
	
}
