package com.example.j15001.mylocationapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener{
    Button b1;

    private LocationManager locationManager;
    final double[] lat = {0.0};
    final double[] lng = {0.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                locationStart();
                AsyncHttp post = new AsyncHttp(lat[0],lng[0]);
                post.execute();
            }
        });

    }
    private void locationStart() {
        Log.d("debug", "locationStart()");
        //LocationManagerインスタンス生成
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            //GPS設定するように促す
            Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingIntent);
            Log.d("debug", "gpsEnable, startActivity");
        } else {
            Log.d("debug", "gpsEnabled");
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("debug", "checkSelfPermission false");

            return;

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, (LocationListener) this);
        ((TextView)findViewById(R.id.textView2)).setText(""+lat[0]);
        ((TextView)findViewById(R.id.textView3)).setText(""+lng[0]);
        Log.d("Debug", String.valueOf(lat[0]));
        Log.d("Debug", String.valueOf(lng[0]));
    }

    public void onLocationChanged(Location location) {
        //緯度の表示
        lat[0] = location.getLatitude();
        lng[0] = location.getLongitude();
        Log.d("Debug", String.valueOf(lat[0]));
        Log.d("Debug", String.valueOf(lng[0]));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}