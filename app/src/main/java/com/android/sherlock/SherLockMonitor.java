package com.android.sherlock;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.net.NetworkInterface;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 示例：
 * <pre>
 *     XposedHelpers.findAndHookMethod(
 *     "需要hook的方法所在类的完整类名",
 *     lpparam.classLoader,      // 类加载器
 *     "需要hook的方法名",
 *     Class<?>,  // 参数类型
 *     new XC_MethodHook() {
 *
 *         @Override
 *         protected void beforeHookedMethod(MethodHookParam param) {
 *             XposedBridge.log("调用XXX()获取了XXX");
 *         }
 *
 *         @Override
 *         protected void afterHookedMethod(MethodHookParam param) throws Throwable {
 *             XposedBridge.log(getMethodStack());
 *             super.afterHookedMethod(param);
 *         }
 *     }
 * );
 * </pre>
 */
public class SherLockMonitor implements IXposedHookLoadPackage {

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {

        if (lpparam == null) {
            return;
        }

        final ApplicationInfo appInfo = lpparam.appInfo;

        // hook获取设备信息方法
        XposedHelpers.findAndHookMethod(
                android.telephony.TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getDeviceId",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用getDeviceId(int)获取了imei");
                        showToast(appInfo, "调用getDeviceId(int)获取了imei");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );

        // hook imsi获取方法
        XposedHelpers.findAndHookMethod(
                android.telephony.TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getSubscriberId",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用getSubscriberId获取了imsi");
                        showToast(appInfo, "调用getSubscriberId获取了imsi");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );
        // hook低版本系统获取mac地方方法
        XposedHelpers.findAndHookMethod(
                android.net.wifi.WifiInfo.class.getName(),
                lpparam.classLoader,
                "getMacAddress",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用getMacAddress()获取了mac地址");
                        showToast(appInfo, "调用getMacAddress()获取了mac地址");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );
        // hook获取mac地址方法
        XposedHelpers.findAndHookMethod(
                java.net.NetworkInterface.class.getName(),
                lpparam.classLoader,
                "getHardwareAddress",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用getHardwareAddress()获取了mac地址");
                        showToast(appInfo, "调用getNetworkInterfaces获取了网络信息");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );

        // hook定位方法
        XposedHelpers.findAndHookMethod(
                LocationManager.class.getName(),
                lpparam.classLoader,
                "getLastKnownLocation",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用getLastKnownLocation获取了GPS地址");
                        showToast(appInfo, "调用getLastKnownLocation获取了GPS地址");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );

        // Hook 获取网络接入标识|IP地址等信息
        XposedHelpers.findAndHookMethod(
                NetworkInterface.class.getName(),
                lpparam.classLoader,
                "getNetworkInterfaces",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("调用getNetworkInterfaces获取了网络信息");
                        showToast(appInfo, "调用getNetworkInterfaces获取了网络信息");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(
                ConnectivityManager.class.getName(),
                lpparam.classLoader,
                "getActiveNetworkInfo",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("调用getActiveNetworkInfo获取了网络信息");
                        showToast(appInfo, "调用getActiveNetworkInfo获取了网络信息");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(getMethodStack());
                        super.afterHookedMethod(param);
                    }
                }
        );
    }

    private void showToast(ApplicationInfo appInfo, String msg) {
        try {
            Context context = AndroidAppHelper.currentApplication().getApplicationContext();
            String label = appInfo.loadLabel(context.getPackageManager()).toString();
            Toast.makeText(context, "[" + label + "]" + msg, Toast.LENGTH_SHORT).show();
        } catch (Throwable ignore) {

        }
    }

    private String getMethodStack() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        StringBuilder stringBuilder = new StringBuilder();

        for (StackTraceElement temp : stackTraceElements) {
            stringBuilder.append(temp.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

}
