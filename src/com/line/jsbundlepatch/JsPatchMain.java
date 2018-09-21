package com.line.jsbundlepatch;

import com.alibaba.fastjson.JSON;
import com.line.jsbundlepatch.model.JsBundle;
import com.line.jsbundlepatch.model.JsBundleHotFix;
import com.line.jsbundlepatch.model.JsBundlePatch;
import com.line.jsbundlepatch.model.Module;
import com.line.jsbundlepatch.utils.FileUtils;
import com.line.jsbundlepatch.utils.Log;
import com.line.jsbundlepatch.utils.PatchUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class JsPatchMain {

    private static final String TAG = "[[JsPatchMain]]";

    private interface Function {
        String CREATE_PATCH = "-p";
        String CREATE_JSON = "-j";
    }

    public interface ENV {
        String DEBUG = "-debug";
        String PRE = "-pre";
        String RELEASE = "-release";
    }

    public interface DEVICETYPE {
        String ALLDEVICE = "-allD";
        String DEVDEVICE = "-devD";
    }

//	static HashMap<String, String> envMap;
//	static {
//		envMap = new HashMap<>();
//		envMap.put("-debug", ENV.DEBUG);
//		envMap.put("-pre", ENV.PRE);
//		envMap.put("-release", ENV.RELEASE);
//	}

    public static final String JSBUNDLE_DIR = "jsbundle";
    public static final String JSPATCH_DIR = "jspatch";

    public static void main(String[] args) {
        String function = null;
        String buildRootDir = null;
        String env = null;
        String deviceType = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (ENV.DEBUG.equals(arg) || ENV.PRE.equals(arg) || ENV.RELEASE.equals(arg)) {
                env = arg;
            } else if (Function.CREATE_JSON.equals(arg) || Function.CREATE_PATCH.equals(arg)) {
                function = arg;
            } else if (DEVICETYPE.ALLDEVICE.equals(arg) || DEVICETYPE.DEVDEVICE.equals(arg)) {
                deviceType = arg;
            } else {
                buildRootDir = arg;
            }
        }

        //test
//		String function = "-p";
////		String buildRootDir = "/Users/chenliu/workApp/RnAppTest/RnModule/build/";
//		String buildRootDir = "/Users/chenliu/workApp/android_v3/jsbundle-update/android/build/";
//		String env = ENV.PRE;

        if (Function.CREATE_PATCH.equals(function)) {
            PatchCreator.createPatch(buildRootDir);
        } else if (Function.CREATE_JSON.equals(function)) {
            JsonCreator.createBuildJson(deviceType, env, buildRootDir);
        }

    }

}
