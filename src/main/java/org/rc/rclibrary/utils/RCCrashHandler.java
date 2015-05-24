package org.rc.rclibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * User: WuRuiqiang(263454190@qq.com)
 * Date: 2015-03-18
 * Time: 16:18
 * Description：
 */
public class RCCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = RCCrashHandler.class.getSimpleName();

    private String LOG_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/crash/";

    private static RCCrashHandler INSTANCE;

    private boolean isRestart = false;
    //保存日志文件回调
    private SaveLogFileCallBack saveLogFileCallBack;

    private Context mContext;
    // 系统默认的 UncaughtException 处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINESE);

    private RCCrashHandler() {

    }

    public static RCCrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RCCrashHandler();
        }
        return INSTANCE;
    }


    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.init(context, false, null);
    }

    /**
     * 初始化
     *
     * @param context
     * @param isRestart 崩溃之后是否重启应用
     */
    public void init(Context context, boolean isRestart) {
        this.init(context, isRestart, null);
    }

    /**
     * 初始化
     *
     * @param context
     * @param isRestart 崩溃之后是否重启应用
     */
    public void init(Context context, boolean isRestart, SaveLogFileCallBack saveLogFileCallBack) {
        mContext = context;
        this.LOG_FILE_PATH = mContext.getApplicationContext().getFilesDir().getPath() + "/crash/";
        this.isRestart = isRestart;
        this.saveLogFileCallBack = saveLogFileCallBack;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }

            // 退出程序
            if (!isRestart) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            } else {
                // 重新启动程序
                Intent intent = mContext.getApplicationContext().getPackageManager()
                        .getLaunchIntentForPackage(mContext.getApplicationContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }


        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序刚刚在开小差，正在赶回岗位。", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件

        if (this.saveLogFileCallBack != null) {
            String name = saveCrashInfo2File(ex);
            if (name != null) {
                this.saveLogFileCallBack.saveSucceed(LOG_FILE_PATH, name);
            }
        }
//        saveCrashInfo2File(ex);
//        IPushService iPushService = new PushServiceImpl();
//        iPushService.uploadFile(mContext,"crash");
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    public String getLogFilePath() {
        return LOG_FILE_PATH;
    }

    public void setLogFilePath(String LOG_FILE_PATH) {
        this.LOG_FILE_PATH = LOG_FILE_PATH;
    }

    /**
     * 保存错误信息到文件中
     * *
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = LOG_FILE_PATH;
                File dir = new File(path);
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        Log.e(TAG, "an error to create file...");
                    }
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }

    public interface SaveLogFileCallBack {
        void saveSucceed(String path, String fileName);
    }
}
