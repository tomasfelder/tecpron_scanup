package com.tecpron.tecpronscanning.ui.leicaprojects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.LeicaScanningProject
import com.amplifyframework.datastore.generated.model.Scanner
import com.amplifyframework.datastore.generated.model.TecpronProject
import com.tecpron.tecpronscanning.MainActivity
import com.tecpron.tecpronscanning.R
import com.tecpron.tecpronscanning.ui.Constants.ACTION_DICTATION
import com.tecpron.tecpronscanning.ui.Constants.DICTATION_REQUEST_CODE
import com.tecpron.tecpronscanning.ui.ViewModelProviderFactory
import com.tecpron.tecpronscanning.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.new_leica_scanning_project_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class NewLeicaScanningProjectFragment : ScopedFragment() {

    private lateinit var viewModel: NewLeicaScanningProjectViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_leica_scanning_project_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewLeicaScanningProjectViewModel::class.java)

        (activity as MainActivity?)
            ?.setActionBarTitle("Agregar Nuevo Subproyecto")

        initializeUI()
    }

    private fun initializeUI() = launch(Dispatchers.Main) {
        val scanners = viewModel.scanners.await()
        scanners.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            val spinnerArrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                this@NewLeicaScanningProjectFragment.context!!,
                android.R.layout.simple_spinner_item, response
            )
            spinner_scanners.adapter = spinnerArrayAdapter
        })

        button_dictation.setOnClickListener {
            onLaunchDictation()
        }

        button_add_project.setOnClickListener {
            createProject()
            view?.findNavController()?.navigate(R.id.action_newLeicaScanningProjectFragment_to_leicaProjectsListFragment)
        }
    }

    private fun onLaunchDictation() {
        val intent = Intent(ACTION_DICTATION)
        startActivityForResult(intent, DICTATION_REQUEST_CODE)
    }

    private fun createProject() = launch(Dispatchers.Main) {
        val name = editText.text.toString()
        val scanner = spinner_scanners.selectedItem as Scanner
        val df: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        val nowAsISO: String = df.format(Date())
        val leicaScanningProject = LeicaScanningProject.builder()
            .name(name)
            .startDate(nowAsISO)
            .scanner(
               Scanner.builder().name(scanner.name).id(scanner.id).build()
            )
            .tecpronProject(
                TecpronProject.justId("117948d5-6ac9-4140-adec-018616dfbba8")
            )
            .build()
        viewModel.addLeicaScanningProject(leicaScanningProject)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == DICTATION_REQUEST_CODE) {
            var result: String? = "[Error]"
            if (data != null) {
                result = data.getStringExtra("result")
            }
            editText.setText(result)
        }
    }

}
