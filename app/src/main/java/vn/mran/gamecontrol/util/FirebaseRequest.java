package vn.mran.gamecontrol.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import vn.mran.gamecontrol.Constant;
import vn.mran.gamecontrol.controler.Preferences;

public class FirebaseRequest {

    public interface OnFirebaseRequestChanged {
        void onAllowRequestStringUpdated(String value);
    }

    private OnFirebaseRequestChanged onFirebaseRequestChanged;

    public void setOnFirebaseRequestChanged(OnFirebaseRequestChanged onFirebaseRequestChanged) {
        this.onFirebaseRequestChanged = onFirebaseRequestChanged;
        this.onFirebaseRequestChanged.onAllowRequestStringUpdated(getRemainRequest());
    }

    private Preferences preferences;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    public FirebaseRequest(Context context) {
        this.preferences = new Preferences(context);
    }

    public String getRemainRequest() {
        String savedDate = preferences.getStringValue(Constant.TIME);
        Date currentDate = new Date();
        if (savedDate.equals("") || !savedDate.equals(simpleDateFormat.format(currentDate))) {
            preferences.storeValue(Constant.TIME, simpleDateFormat.format(currentDate));
            preferences.storeValue(Constant.ALLOW_REQUEST, 100);
            return Constant.TOTAL_ALLOW_REQUEST + "/" + Constant.TOTAL_ALLOW_REQUEST;
        } else {
            return preferences.getIntValue(Constant.ALLOW_REQUEST, 100) + "/" + Constant.TOTAL_ALLOW_REQUEST;
        }
    }

    public void minuteAllowRequest() {
        int allowRequest = preferences.getIntValue(Constant.ALLOW_REQUEST, 100);
        allowRequest = allowRequest - 1;
        preferences.storeValue(Constant.ALLOW_REQUEST, allowRequest);
        if (onFirebaseRequestChanged != null) {
            onFirebaseRequestChanged.onAllowRequestStringUpdated(allowRequest + "/" + Constant.TOTAL_ALLOW_REQUEST);
        }
    }
}
