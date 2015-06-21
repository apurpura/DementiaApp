package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity{

    private TextView mTextView;
    private GoogleApiClient mApiClient;
    private static  String WEAR_MESSAGE_PATH = "//message";
    private SimpleCursorAdapter mAdapter;;
    private JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String text = "";
        if(intent != null) {
            String m = intent.getStringExtra("text");
            if(m != null) {
                try {
                    json = new JSONObject(m);
                    text = text + "Id: " + json.get("Id").toString() + "\n";
                    text = text + "Action: " + json.get("Action").toString() + "\n";
                    text = text + "Summary: " + json.get("Summary").toString() + "\n";
                    text = text + "Description: " + json.get("Description").toString() + "\n";
                    text = text + "EndTime: " + json.get("EndTime").toString() + "\n";
                    text = text + "Location: " + json.get("Location").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        final String finalText = text;
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                if(finalText != ""){
                    mTextView.setText(finalText);
                }else{
                    mTextView.setText("tester123");
                }
            }
        });

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();


    }

    public class MessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){

            Log.d("onRecieve", "made it");
            String message = intent.getStringExtra("message");
            Intent messageIntent = new Intent(context, MainActivity.class);
            messageIntent.putExtra("name", message);
            context.startActivity(intent);
        }

    }

}
