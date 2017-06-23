package gusakov.net.disablecameramic.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {

    public static final String TAG=MyAccessibilityService.class.getSimpleName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.v(TAG,"get ecent from "+event.getPackageName());
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        Log.v(TAG,"service connected");

//        AccessibilityServiceInfo info= new AccessibilityServiceInfo();
//        // Set the type of events that this service wants to listen to.  Others
//        // won't be passed to this service.
//        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_CLICKED|AccessibilityEvent.TYPE_VIEW_FOCUSED|AccessibilityEvent.TYPE_VIEW_SCROLLED|AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED|AccessibilityEvent.TYPE_WINDOWS_CHANGED;
//
//
////        // Set the type of feedback your service will provide.
////        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
//
//        // Default services are invoked only if no package-specific ones are present
//        // for the type of AccessibilityEvent generated.  This service *is*
//        // application-specific, so the flag isn't necessary.  If this was a
//        // general-purpose service, it would be worth considering setting the
//        // DEFAULT flag.
//
//         info.flags = AccessibilityServiceInfo.DEFAULT;
//
//        info.notificationTimeout = 100;
//
//        this.setServiceInfo(info);
    }
}
