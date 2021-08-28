package com.tecpron.tecpronscanning.ui.settings

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.amplifyframework.datastore.generated.model.TecpronProject
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog
import com.tecpron.tecpronscanning.MainActivity
import com.tecpron.tecpronscanning.R
import com.tecpron.tecpronscanning.ui.Constants.RESULT_LOAD_IMAGE
import com.tecpron.tecpronscanning.ui.TecpronProjectSharedViewModel
import com.tecpron.tecpronscanning.ui.ViewModelProviderFactory
import com.tecpron.tecpronscanning.ui.base.ScopedFragment
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


class SettingsFragment : ScopedFragment(), PickiTCallbacks {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var tecpronProjectSharedViewModel: TecpronProjectSharedViewModel
    private var photoPath: String? = null
    private var pickiT: PickiT? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    private fun choosePhoto() {
        val properties = DialogProperties()
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = null
        properties.root = File("mnt/sdcard/Pictures")
        properties.error_dir = File("mnt/sdcard/Pictures")
        properties.offset = File("")
        properties.extensions = null;
        properties.show_hidden_files = false;
        val dialog = FilePickerDialog(this@SettingsFragment.context, properties)
        dialog.setTitle("Elegir un plano")
        dialog.show()
        dialog.setDialogSelectionListener {
            for (item in it){
                Log.e("TAG",item.toString())
                tecpronProjectSharedViewModel.tecpronMap.value = item
            }
        }
    }

    private fun bindUI() = launch(Dispatchers.Main) {

        button_add_map.setOnClickListener {
            choosePhoto()
        }

        val tecpronProjects = viewModel.tecpronProjects.await()
        tecpronProjects.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            val spinnerArrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                this@SettingsFragment.context!!,
                R.layout.custom_spinner_item, response
            )
            spinnerTecpronProjects.adapter = spinnerArrayAdapter
        })
        spinnerTecpronProjects.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tecpronProjectSharedViewModel.select(spinnerTecpronProjects.selectedItem as TecpronProject)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        (activity as MainActivity?)
            ?.setActionBarTitle("Configuracion")

        tecpronProjectSharedViewModel = activity?.run {
            ViewModelProviders.of(this)[TecpronProjectSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        pickiT = PickiT(context, this)

        bindUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            val selectedImage = data.data
            pickiT?.getPath(selectedImage, Build.VERSION.SDK_INT)
        }
    }

    override fun PickiTonProgressUpdate(progress: Int) {

    }

    override fun PickiTonStartListener() {

    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        Log.e("TAG",path!!)
    }

}
