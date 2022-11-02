package org.idnp.idnp2022lab04;

import androidx.appcompat.app.AppCompatActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvBroadCast;
    private IntentFilter chargingIntentFilter;
    private ChargingBroadCastReceived chargingBroadCastReceived;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    private void init(){
        tvBroadCast = (TextView) findViewById(R.id.tvBroadCast);
        chargingIntentFilter = new IntentFilter();
        chargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        chargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        chargingBroadCastReceived = new ChargingBroadCastReceived();
    }
    private class ChargingBroadCastReceived extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            boolean isCharging = (action.equals(Intent.ACTION_POWER_CONNECTED));
            showCharging(isCharging);
        }
    }
    private void showCharging (boolean isCharging){
        if(isCharging){
            tvBroadCast.setText("Está conectado: Cargando");
            tvBroadCast.setTextColor(Color.parseColor("#0000FF"));
        }
        else{
            tvBroadCast.setText("Está desconectado: No está Cargando");
            tvBroadCast.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    protected void onResume(){
        super.onResume();
        registerReceiver(chargingBroadCastReceived, chargingIntentFilter);
    }

    protected void onPause(){
        super.onPause();
        unregisterReceiver(chargingBroadCastReceived);
    }
}