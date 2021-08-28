package com.tecpron.tecpronscanning

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import com.tecpron.tecpronscanning.data.network.NetworkChecker
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.ui.Constants.CAMERA
import com.tecpron.tecpronscanning.ui.Constants.DOUBLESCAN
import com.tecpron.tecpronscanning.ui.Constants.LAST_UPDATED_TIME
import com.tecpron.tecpronscanning.ui.Constants.LOCATION
import com.tecpron.tecpronscanning.ui.Constants.MyPREFERENCES
import com.tecpron.tecpronscanning.ui.Constants.RESOLUTION
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class MainActivity : DaggerAppCompatActivity(), CoroutineScope {

    private lateinit var navigationController: NavController

    private lateinit var sharedpreferences: SharedPreferences

    @Inject
    lateinit var stationRepository: StationRepository

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        job = Job()

        navigationController = findNavController(R.id.navigationHostFragment)

        bottom_nav.setupWithNavController(navigationController)

        NavigationUI.setupActionBarWithNavController(this, navigationController)

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        val editor = sharedpreferences.edit()

        editor.putString(LOCATION, "Exterior")
        editor.putString(RESOLUTION, "Baja")
        editor.putBoolean(CAMERA, false)
        editor.putBoolean(DOUBLESCAN, false)
        //editor.putString(LAST_UPDATED_TIME, Date().time.toString())
        editor.apply()

        val network = NetworkChecker(applicationContext)
        network.registerNetworkCallback()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if(intent?.scheme != null && "myapp" == intent.scheme) {
            Amplify.Auth.handleWebUISignInResponse(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

}

