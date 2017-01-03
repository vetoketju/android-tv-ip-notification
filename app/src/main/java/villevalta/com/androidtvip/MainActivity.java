package villevalta.com.androidtvip;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by villevalta on 12/12/16.
 */

public class MainActivity extends Activity {

    TextView ssid;
    TextView ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ssid = (TextView) findViewById(R.id.label_ssid);
        ip = (TextView) findViewById(R.id.label_ip);

        ssid.setText("SSID: " + AndroidTvIpApp.getInstance().getWifiSSID());
        ip.setText("IP: " + AndroidTvIpApp.getInstance().getWifiIp());
    }
}
