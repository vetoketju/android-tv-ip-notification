package villevalta.com.androidtvip;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.app.recommendation.ContentRecommendation;

/**
 * Created by villevalta on 12/12/16.
 */

public class RecommendationUpdaterService extends IntentService {

    private final static String SERVICENAME = "IP notification updater service";

    NotificationManager mgr;

    public RecommendationUpdaterService() {
        super(SERVICENAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        updateRecommendation();
    }

    private void updateRecommendation() {

        int cardWidth = getResources().getDimensionPixelSize(R.dimen.recommendation_card_width);
        int cardHeight = getResources().getDimensionPixelSize(R.dimen.recommendation_card_height);

        String ssid = AndroidTvIpApp.getInstance().getWifiSSID();
        String ip = AndroidTvIpApp.getInstance().getWifiIp();

        Bitmap image = createImage(cardWidth, cardHeight, new String[]{
                ssid,
                ip
        });

        ContentRecommendation.Builder builder = new ContentRecommendation.Builder();

        builder.setSourceName(ip);
        builder.setTitle(ip);
        builder.setText(ssid);
        builder.setBadgeIcon(android.R.drawable.ic_dialog_info);
        builder.setContentImage(image);

        builder.setContentIntentData(ContentRecommendation.INTENT_TYPE_ACTIVITY, new Intent(this, MainActivity.class), 0, null);

        Notification notification = builder.build().getNotificationObject(this);
        notification.priority = Notification.PRIORITY_MAX;


        mgr.notify(1, notification);
    }

    private Bitmap createImage(int w, int h, String[] texts){

        Paint p  = new Paint();
        p.setColor(Color.LTGRAY);
        p.setTextSize(38);

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect lineBounds = new Rect();

        canvas.drawColor(Color.BLACK);

        for (int i = 0; i < texts.length; i++) {
            p.getTextBounds(texts[i], 0, texts[i].length(), lineBounds);
            canvas.drawText(texts[i], (w / 2) - (lineBounds.centerX()), (((h/texts.length) * (i + 1)) - lineBounds.height()), p);
        }

        return bitmap;
    }
}
