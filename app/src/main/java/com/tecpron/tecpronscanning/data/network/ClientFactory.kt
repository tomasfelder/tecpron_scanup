package com.tecpron.tecpronscanning.data.network

import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
//import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
//import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool

object ClientFactory {
//    @Volatile
//    private var client: AWSAppSyncClient? = null
//
//    @Synchronized
//    fun getInstance(context: Context?): AWSAppSyncClient? {
//        if (client == null) {
//            val awsConfig = AWSConfiguration(context)
//
//            val cognitoUserPool = CognitoUserPool(context, awsConfig)
//            val basicCognitoUserPoolsAuthProvider =
//                BasicCognitoUserPoolsAuthProvider(cognitoUserPool)
//
//            client = AWSAppSyncClient.builder()
//                .context(context)
//                .awsConfiguration(awsConfig)
//                .cognitoUserPoolsAuthProvider(basicCognitoUserPoolsAuthProvider)
//                .build()
//        }
//        return client
//    }
}