package com.mobileglobe.android.danewwifisetup;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * array name of wifi
     * you need to enter your own wifi name
     */
    private String[] wifiNames = new String[]{"SSID_1", "SSID_2", "SSID_3"};
    /**
     * array name of passwords
     * you need to enter your own wifi password
     */
    private String[] wifiPasswords = new String[]{"pass_SSID1", "pass_SSID2", "pass_SSID3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpWifiConfig();
    }


    void setUpWifiConfig() {
        String message = "";
        for (int i = 0; i < wifiNames.length && i < wifiPasswords.length; i++) {
            String wifiName = wifiNames[i];
            String wifiPass = wifiPasswords[i];
            message += "wifiName " + wifiName + " wifipass " + wifiPass + " is added succesfully.\n\n\n";
            try {
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiConfiguration wifiConfig = new WifiConfiguration();
                wifiConfig.SSID = "\"" + wifiName + "\""; //IMP! This should be in Quotes!!
                wifiConfig.preSharedKey = "\"" + wifiPass + "\"";
                wifiConfig.hiddenSSID = true;
                wifiConfig.status = WifiConfiguration.Status.ENABLED;
                wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                int res = wifi.addNetwork(wifiConfig);
                Log.d("WifiPreference", "add Network returned " + res);
                boolean b = wifi.enableNetwork(res, true);
                Log.d("WifiPreference", "enableNetwork returned " + b);


            } catch (Exception e) {
                if (e.getMessage() != null) {
                    message += " probleme : " + e.getMessage() + "\n\n";
                }
            }
        }
        TextView msgTextView = (TextView) findViewById(R.id.messageTextView);
        msgTextView.setText(message);
    }
}
