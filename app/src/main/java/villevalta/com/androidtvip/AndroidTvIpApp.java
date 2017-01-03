package villevalta.com.androidtvip;

import android.app.Application;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * Created by villevalta on 13/12/16.
 */

public class AndroidTvIpApp extends Application {

    private static AndroidTvIpApp singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        updateRecommendation();
    }

    public static AndroidTvIpApp getInstance(){
        return singleton;
    }

    public void updateRecommendation(){
        startService(new Intent(this, RecommendationUpdaterService.class));
    }

    @NonNull
    public String getWifiIp(){

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString = null;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
        }

        return ipAddressString != null ? ipAddressString : "N/A";
    }

    @NonNull
    public String getWifiSSID(){
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        String ret = wifiManager.getConnectionInfo().getSSID();
        return ret != null ? ret : "N/A";
    }
}
