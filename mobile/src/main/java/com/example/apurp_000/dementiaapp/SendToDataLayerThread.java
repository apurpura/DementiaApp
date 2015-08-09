package com.example.apurp_000.dementiaapp;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by apurpura on 6/19/2015.
 */
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
        if(nodes.getNodes().size() > 0) {
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.d("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
                } else {
                    Log.d("myTag", "ERROR: Failed to send Message");
                }
            }
        }
        else
           Notification.sendNotification(Credentials.signonActivity, "Wearable Disconnected", "Failed action on Wearable");
    }
}
