package com.example.apurp_000.dementiaapp;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class SendToDataLayerThread extends Thread {

    String path;
    String message;

    private GoogleApiClient googleClient;

    SendToDataLayerThread(String p, String msg,
                          GoogleApiClient googleC){
        path = p;
        message = msg;
        googleClient = googleC;
    }

    public void run(){
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
        for(Node node : nodes.getNodes()){
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), path, message.getBytes()).await();
            if(result.getStatus().isSuccess()){
                Log.d("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
            }else{
                Log.d("myTag", "ERROR: Failed to send Message");
            }
        }
    }
}
