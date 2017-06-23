package gusakov.net.disablecameramic.service;


import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gusakov.net.disablecameramic.MainActivity;

public class DeviceControlService extends Service {

    public static int taskId;
    public static final String TAG = DeviceControlService.class.getSimpleName();
    private List<String> cameraPackages = new ArrayList<>();
    private List<String> micPackages = new ArrayList<>();
    private ActivityManager manager;
    private Handler handler;
    private Intent mainIntent;
//    private List

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        Thread thread = new Thread(new Control());
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class Control implements Runnable {
        @Override
        public void run() {
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            final List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);

            Log.v(TAG, "size=" + pkgAppsList.size());

            for (ResolveInfo resolveInfo : pkgAppsList) {
                PackageInfo packageInfo = null;
                try {
                    packageInfo = getPackageManager().getPackageInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_PERMISSIONS);
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String[] requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions == null) {
                    continue;
                }

                for (int i = 0; i < requestedPermissions.length; i++) {
                    if (requestedPermissions[i].equals("android.permission.CAMERA")) {
                        cameraPackages.add(packageInfo.packageName);
                        Log.v(TAG, "added to camera packages " + packageInfo.packageName);
                    }
                    if (requestedPermissions[i].equals("android.permission.RECORD_AUDIO")) {
                        micPackages.add(packageInfo.packageName);
                        Log.v(TAG, "added to mic pachages " + packageInfo.packageName);
                    }
                }

//                Log.v(TAG, Arrays.toString(requestedPermissions));


            }
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getRunningApps();
            }

            stopSelf();
        }

        public void getRunningApps() {
            List<ActivityManager.RunningTaskInfo> taskInfo = manager.getRunningTasks(1);
            String runningPackageName = taskInfo.get(0).topActivity.getPackageName();
            Log.d(TAG, "CURRENT Activity ::" + runningPackageName);
            for (String disablePackage : cameraPackages) {
                if (runningPackageName.equals(disablePackage)) {
                    startActivity(mainIntent);
                    break;
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            DeviceControlService.this.manager.moveTaskToFront(DeviceControlService.this.taskId, 0);
//                        }
//                    });

                }
            }
//            ComponentName componentInfo = taskInfo.get(0).topActivity;


        }
    }
}
