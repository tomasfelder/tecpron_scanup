package com.tecpron.tecpronscanning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.*
import com.amplifyframework.core.Amplify


private val TAG = AuthenticationActivity::class.java.simpleName

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        val mobileClient = Amplify.Auth.getPlugin("awsCognitoAuthPlugin").escapeHatch as AWSMobileClient?

        mobileClient?.initialize(this, object: Callback<UserStateDetails> {

            override fun onResult(result: UserStateDetails) {
                Log.i(TAG, result.userState.toString())
                when (result.userState) {
                    UserState.SIGNED_IN -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                    UserState.SIGNED_OUT -> showSignIn()
                    else -> {
                        mobileClient.signOut()
                    }
                }
            }

            override fun onError(e: Exception?) {
                Log.e(TAG, e.toString())
            }
        })
    }

    private fun showSignIn() {
        try {
            val mobileClient = Amplify.Auth.getPlugin("awsCognitoAuthPlugin").escapeHatch as AWSMobileClient?
            mobileClient?.showSignIn(
                this,
                SignInUIOptions.builder()
                    .nextActivity(MainActivity::class.java).build()
            )
        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.toString())
        }
    }
}


