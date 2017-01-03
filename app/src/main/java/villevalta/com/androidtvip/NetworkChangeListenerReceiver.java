package villevalta.com.androidtvip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by villevalta on 13/12/16.
 */

public class NetworkChangeListenerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidTvIpApp.getInstance().updateRecommendation();
    }
}
