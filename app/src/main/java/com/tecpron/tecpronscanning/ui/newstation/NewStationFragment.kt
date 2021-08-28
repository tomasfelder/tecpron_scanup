package com.tecpron.tecpronscanning.ui.newstation

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.contains
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.*
import com.amplifyframework.datastore.generated.model.Scanner
import com.tecpron.tecpronscanning.MainActivity
import com.tecpron.tecpronscanning.R
import com.tecpron.tecpronscanning.data.model.InternalScanningProject
import com.tecpron.tecpronscanning.ui.Constants.ACTION_MIC_RELEASED
import com.tecpron.tecpronscanning.ui.Constants.ACTION_RELEASE_MIC
import com.tecpron.tecpronscanning.ui.Constants.BASIC_CAMERA_REQUEST_CODE
import com.tecpron.tecpronscanning.ui.Constants.CAMERA
import com.tecpron.tecpronscanning.ui.Constants.DOUBLESCAN
import com.tecpron.tecpronscanning.ui.Constants.EXTRA_SOURCE_PACKAGE
import com.tecpron.tecpronscanning.ui.Constants.LAST_UPDATED_TIME
import com.tecpron.tecpronscanning.ui.Constants.LOCATION
import com.tecpron.tecpronscanning.ui.Constants.MyPREFERENCES
import com.tecpron.tecpronscanning.ui.Constants.RESOLUTION
import com.tecpron.tecpronscanning.ui.SharedViewModel
import com.tecpron.tecpronscanning.ui.TecpronProjectSharedViewModel
import com.tecpron.tecpronscanning.ui.ViewModelProviderFactory
import com.tecpron.tecpronscanning.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_station_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
private const val THIRTY_MINUTES : Long = 1800000

class NewStationFragment : ScopedFragment() {

    private lateinit var viewModel: NewStationViewModel
    private lateinit var tecpronProjectSharedViewModel: TecpronProjectSharedViewModel
    private lateinit var tecpronProject: TecpronProject
    private var selectedLeicaScanningProject: InternalScanningProject? = null
    private var recorder: MediaRecorder? = null
    private var record: Boolean = false

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    val args: NewStationFragmentArgs by navArgs()
    private var station: Station? = null
    private lateinit var sharedViewModel: SharedViewModel
    private var mapPoint: List<Int>? = null
    private lateinit var sharedpreferences:SharedPreferences
    private var takenPictures: Int = 0
    private var voiceRecordings: Int = 0

    private var mAudioRecorder: AudioRecord? = null
    private val notesCheckBoxes: MutableList<CheckBox> = mutableListOf()

    private var contentUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_station_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity?)
            ?.setActionBarTitle("Nro. Estacion ${args.stationNumber}")

        val intent = Intent("com.realwear.wearhf.intent.action.MOUSE_COMMANDS")
        intent.putExtra("com.realwear.wearhf.intent.extra.MOUSE_ENABLED", false)
        requireActivity().sendBroadcast(intent)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewStationViewModel::class.java)
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        tecpronProjectSharedViewModel = activity?.run {
            ViewModelProviders.of(this)[TecpronProjectSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        tecpronProjectSharedViewModel.selected.observe(viewLifecycleOwner, androidx.lifecycle.Observer  { item ->
            tecpronProject = item
        })

        sharedViewModel.mapPoint.observe(viewLifecycleOwner, androidx.lifecycle.Observer  { item ->
            mapPoint = item
        })

        sharedpreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)

        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        initializeUI()
    }

    private fun initializeUI() {

        station = args.station
        activity?.bottom_nav?.visibility = View.GONE

        button_add_station.setOnClickListener {
            if(station == null)
                crateStation()
            else
                updateStation()
            view?.findNavController()?.navigate(R.id.action_newStation_to_leicaProjectsListFragment)
        }

        button_record.setOnClickListener {
            onStartRecord(it)
        }

        button_add_map_point.setOnClickListener {
            val action = NewStationFragmentDirections.actionNewStationToMapFragment(args.stationNumber)
            activity?.bottom_nav?.visibility = View.GONE
            view?.findNavController()?.navigate(action)
        }

        button_add_photo.setOnClickListener {
            onLaunchCameraBasic()
        }

        if(station != null){
            cancelled_switch.isChecked = station!!.cancelled
            cancelled_switch.isClickable = false
            takenPictures = station!!.takenPictures
            voiceRecordings = station!!.voiceRecordings
        }

        createNotesCheckBoxes()

        initializeProjectsSpinner()

        initializeComplexities()

    }

    private fun initializeComplexities() {
        if(station == null){
            val radioButtonLocation =  view?.findViewWithTag<View>(sharedpreferences.getString(LOCATION,null)) as RadioButton
            val radioButtonResolution = view?.findViewWithTag<View>(sharedpreferences.getString(RESOLUTION,null)) as RadioButton

            radioGroupLocation.check(radioButtonLocation.id)
            radioGroupResolution.check(radioButtonResolution.id)

            switchCamera.isChecked = sharedpreferences.getBoolean(CAMERA,false)
            switchDoubleScan.isChecked = sharedpreferences.getBoolean(DOUBLESCAN,false)
        }
        else {
            val radioButtonLocation =  view?.findViewWithTag<View>(station?.scannerConfiguration?.location) as RadioButton
            val radioButtonResolution = view?.findViewWithTag<View>(station?.scannerConfiguration?.resolution) as RadioButton

            radioGroupLocation.check(radioButtonLocation.id)
            radioGroupResolution.check(radioButtonResolution.id)

            switchCamera.isChecked = station?.scannerConfiguration?.camera!!
            switchDoubleScan.isChecked = station?.scannerConfiguration?.doubleScan!!
        }
    }

    private fun initializeProjectsSpinner()  = launch(Dispatchers.Main) {
        val projects = viewModel.projects.await()
        projects.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            val spinnerArrayAdapter: ArrayAdapter<InternalScanningProject> = ArrayAdapter(
                this@NewStationFragment.context!!,
                android.R.layout.simple_spinner_item, response
            )
            spinner_projects.adapter = spinnerArrayAdapter
            var i = 0
            if(station != null && selectedLeicaScanningProject == null){
                var stop = false
                while(!stop){
                    val item = spinnerArrayAdapter.getItem(i)
                    if(item?.id == station?.leicaScanningProject?.id){
                        stop = true
                        selectedLeicaScanningProject = item!!
                    }
                    else{
                        i++
                    }
                }
            }
            if(selectedLeicaScanningProject == null){
                selectedLeicaScanningProject =
                    spinner_projects.selectedItem as InternalScanningProject
            }
            spinner_projects.setSelection(spinnerArrayAdapter.getPosition(selectedLeicaScanningProject))
        })
        spinner_projects.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedLeicaScanningProject =
                    spinner_projects.selectedItem as InternalScanningProject
            }
        }
    }

    private fun crateStation() = launch(Dispatchers.Main){
        val stationNumber = args.stationNumber
        val cancelled = cancelled_switch.isChecked
        val selectedNotes = getSelectedNotes()
        val selectedProject = spinner_projects.selectedItem as InternalScanningProject
        val radioButtonResolution =  view?.findViewById<View>(radioGroupResolution.checkedRadioButtonId) as RadioButton
        val radioButtonLocation =  view?.findViewById<View>(radioGroupLocation.checkedRadioButtonId) as RadioButton

        val lastUpdatedTime = sharedpreferences.getString(LAST_UPDATED_TIME,Date().time.toString())!!.toLong()
        val date = Date()

        var passedThirtyMinutes = false
        if(lastUpdatedTime < (date.time - THIRTY_MINUTES))
            passedThirtyMinutes = true

        val df: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        val nowAsISO: String = df.format(date)

        //val leicaDate = df.format(selectedProject.startDate)

        val station = Station.builder()
            .stationNumber(stationNumber)
            .date(nowAsISO)
            .cancelled(cancelled)
            .endOfRoutine(passedThirtyMinutes)
            .notes(selectedNotes)
            .state(listOf("Prueba"))
            .battery(1)
            .scannerConfiguration(
                ScannerConfiguration.builder()
                    .location(radioButtonLocation.text.toString())
                    .resolution(radioButtonResolution.text.toString())
                    .camera(switchCamera.isChecked)
                    .doubleScan(switchDoubleScan.isChecked)
                    .build()
            )
            .mapPoint(mapPoint)
            .takenPictures(takenPictures)
            .voiceRecordings(voiceRecordings)
            .leicaScanningProject(LeicaScanningProject.builder()
                .name(selectedProject.name)
                .startDate(selectedProject.startDate)
                .scanner(Scanner.justId(selectedProject.scanner.id))
                .tecpronProject(TecpronProject.builder().name("TEC2020").startDate("2020-06-19T10:58:19.822Z").id("eff05468-301d-4670-ae58-0a2158f343a5").build())
                .id(selectedProject.id)
                .build()
            )
            .build()

        val editor: SharedPreferences.Editor = sharedpreferences.edit()

        editor.putString(LOCATION, radioButtonLocation.text.toString())
        editor.putString(RESOLUTION, radioButtonResolution.text.toString())
        editor.putBoolean(CAMERA, switchCamera.isChecked)
        editor.putBoolean(DOUBLESCAN, switchDoubleScan.isChecked)
        editor.putString(LAST_UPDATED_TIME,date.time.toString())
        editor.apply()

        viewModel.addStation(station)

        sharedViewModel.mapPoint.postValue(mutableListOf())
    }

    private fun updateStation() = launch(Dispatchers.Main){
        val selectedNotes = getSelectedNotes()
        val selectedProject = spinner_projects.selectedItem as InternalScanningProject
        val radioButtonResolution =  view?.findViewById<View>(radioGroupResolution.checkedRadioButtonId) as RadioButton
        val radioButtonLocation =  view?.findViewById<View>(radioGroupLocation.checkedRadioButtonId) as RadioButton

        val df: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Quoted "Z" to indicate UTC, no timezone offset

        //val leicaDate = df.format(selectedProject.startDate)
        Log.e("TAG",mapPoint.toString())
        if(mapPoint!!.isNotEmpty()){
            val station = station!!.copyOfBuilder()
                .cancelled(cancelled_switch.isChecked)
                .notes(selectedNotes)
                .mapPoint(mapPoint)
                .scannerConfiguration(
                    station!!.scannerConfiguration.copyOfBuilder()
                        .location(radioButtonLocation.text.toString())
                        .resolution(radioButtonResolution.text.toString())
                        .camera(switchCamera.isChecked)
                        .doubleScan(switchDoubleScan.isChecked)
                        .build()
                )
                .leicaScanningProject(LeicaScanningProject.builder()
                    .name(selectedProject.name)
                    .startDate(selectedProject.startDate)
                    .scanner(Scanner.justId(selectedProject.scanner.id))
                    .id(selectedProject.id)
                    .build()
                )
                .build()

            viewModel.updateStation(station)
        }
        else{
            val station = station!!.copyOfBuilder()
                .cancelled(cancelled_switch.isChecked)
                .notes(selectedNotes)
                .scannerConfiguration(
                    station!!.scannerConfiguration.copyOfBuilder()
                        .location(radioButtonLocation.text.toString())
                        .resolution(radioButtonResolution.text.toString())
                        .camera(switchCamera.isChecked)
                        .doubleScan(switchDoubleScan.isChecked)
                        .build()
                )
                .leicaScanningProject(LeicaScanningProject.builder()
                    .name(selectedProject.name)
                    .startDate(selectedProject.startDate)
                    .scanner(Scanner.justId(selectedProject.scanner.id))
                    .id(selectedProject.id)
                    .build()
                )
                .build()

            viewModel.updateStation(station)
        }
        sharedViewModel.mapPoint.postValue(mutableListOf())
    }

    private fun getSelectedNotes(): MutableList<String> {
        val notesList: MutableList<String> = mutableListOf()
        notesCheckBoxes.forEach {
            if(it.isChecked)
                notesList.add(it.tag as String)
        }
        return notesList
    }

    private fun createNotesCheckBoxes() = launch(Dispatchers.Main) {
        val notes = viewModel.notes.await()
        notes.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            var i = 1
            if(notesCheckBoxes.size == 0){
                for(note in response){
                    val checkBox = CheckBox(this@NewStationFragment.context)
                    checkBox.text = note.name
                    checkBox.tag = note.id
                    checkBox.id = i
                    i++
                    linearLayout.addView(checkBox)
                    notesCheckBoxes.add(checkBox)
                }
            }
            else{
                for(checkBox in notesCheckBoxes){
                    if(!linearLayout.contains(checkBox)){
                        (checkBox.parent as ViewGroup).removeView(checkBox);
                        linearLayout.addView(checkBox)
                    }

                }
            }
            if(station != null){
                station!!.notes.forEach {
                    note ->
                    notesCheckBoxes.find{
                        it.tag as String == note
                    }?.isChecked = true
                }
            }
        })
    }

    private fun onRecord(){
        if (record) {
            startRecording()
        } else {
            stopRecording()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) requireActivity().finish()
    }

    private fun onStartRecord(view: View?) {
        record = !record
        onRecord()
    }

     private fun takeMicrophone() {
         val intent = Intent(ACTION_RELEASE_MIC)
         intent.putExtra(EXTRA_SOURCE_PACKAGE, activity?.packageName)
         activity?.sendBroadcast(intent)
     }

    private fun releaseMicrophone() {
        val intent = Intent(ACTION_MIC_RELEASED)
        intent.putExtra(EXTRA_SOURCE_PACKAGE, activity?.packageName)
        activity?.sendBroadcast(intent)
 }

    private fun startRecording() {
        takeMicrophone()
        val mediaStorageDir =  "/sdcard/Documents"
        val file = File(mediaStorageDir, "${tecpronProject.name}-Station-${args.stationNumber}-A${voiceRecordings+1}.3gp")
        recorder = MediaRecorder()
        recorder?.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        recorder?.setOutputFile(file)
        recorder?.prepare()
        recorder?.start()
        showAlertDialogRecordStarted()
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        releaseMicrophone()
        recorder = null
    }

    private fun showAlertDialogRecordStarted() {
        val builder = AlertDialog.Builder(context);
        builder.setTitle("Grabando Audio");
        builder.setPositiveButton("Terminar"
        ) { _, _ ->
            stopRecording()
        };
        val dialog = builder.create();
        dialog.show();
    }

    private fun onLaunchCameraBasic() {
        if (ContextCompat.checkSelfPermission(requireActivity(),
                WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    WRITE_EXTERNAL_STORAGE
                )) {
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(WRITE_EXTERNAL_STORAGE),
                    50)
            }
        } else {
            val mediaStorageDir =  "/sdcard/Pictures"
            val file = File(mediaStorageDir, "${tecpronProject.name}-Station-${args.stationNumber}-P${takenPictures+1}.jpg")
            contentUri = FileProvider.getUriForFile(
                context!!.applicationContext,
                context!!.applicationContext.packageName + ".provider",
                file);
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
            startActivityForResult(intent, BASIC_CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null)
        {
            when (requestCode) {
                BASIC_CAMERA_REQUEST_CODE // Display Bitmap received from Camera
                -> {
                        takenPictures++
                }
            }
        }
    }

}
