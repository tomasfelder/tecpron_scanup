package com.tecpron.tecpronscanning.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import com.tecpron.tecpronscanning.general.GlobalVariables;

public class NetworkChecker {

    private Context context;

    public NetworkChecker(Context context) {
        this.context = context;
    }

    // Network Check
    public void registerNetworkCallback()
    {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                                                                   @Override
                                                                   public void onAvailable(Network network) {
                                                                       GlobalVariables.isNetworkConnected = true; // Global Static Variable
                                                                   }
                                                                   @Override
                                                                   public void onLost(Network network) {
                                                                       GlobalVariables.isNetworkConnected = false; // Global Static Variable
                                                                   }
                                                               }

            );
            GlobalVariables.isNetworkConnected = false;
        }catch (Exception e){
            GlobalVariables.isNetworkConnected = false;
        }
    }
}
