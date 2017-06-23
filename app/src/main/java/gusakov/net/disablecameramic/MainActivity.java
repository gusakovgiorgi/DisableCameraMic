package gusakov.net.disablecameramic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import gusakov.net.disablecameramic.service.DeviceControlService;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate");
        Intent serviceIntent = new Intent(this, DeviceControlService.class);
        DeviceControlService.taskId = getTaskId();
        startService(serviceIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "task id=" + getTaskId());
    }
}
