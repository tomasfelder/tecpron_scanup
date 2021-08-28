package com.tecpron.tecpronscanning.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.graphics.Paint.Align
import android.graphics.Path.FillType
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.amplifyframework.datastore.generated.model.Station
import com.amplifyframework.datastore.generated.model.TecpronProject
import com.tecpron.tecpronscanning.MainActivity
import com.tecpron.tecpronscanning.R
import com.tecpron.tecpronscanning.ui.Constants
import com.tecpron.tecpronscanning.ui.Constants.SCROLL_POSITION_X
import com.tecpron.tecpronscanning.ui.Constants.SCROLL_POSITION_Y
import com.tecpron.tecpronscanning.ui.Constants.ZOOM
import com.tecpron.tecpronscanning.ui.SharedViewModel
import com.tecpron.tecpronscanning.ui.TecpronProjectSharedViewModel
import com.tecpron.tecpronscanning.ui.ViewModelProviderFactory
import com.tecpron.tecpronscanning.ui.base.ScopedFragment
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.map_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MapFragment : ScopedFragment() {

    private lateinit var viewModel: MapViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var tecpronProjectSharedViewModel: TecpronProjectSharedViewModel
    private lateinit var tecpronProject: TecpronProject
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    val args: MapFragmentArgs by navArgs()

    private var imageView: TouchImageView? = null
    private var xCoordinate: Int = 0
    private var yCoordinate: Int = 0
    private lateinit var workingBitmap: Bitmap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
        sharedpreferences = requireActivity().getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);

        tecpronProjectSharedViewModel = activity?.run {
            ViewModelProviders.of(this)[TecpronProjectSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        tecpronProjectSharedViewModel.selected.observe(viewLifecycleOwner, androidx.lifecycle.Observer  { item ->
            tecpronProject = item
            viewModel.setTecpronProjectId(tecpronProject.id)
        })

        imageView = img as TouchImageView
        if(tecpronProjectSharedViewModel.tecpronMap.value == null)
            imageView?.setImageURI(Uri.parse(""))
        else
            imageView?.setImageURI(Uri.parse(tecpronProjectSharedViewModel.tecpronMap.value))


        zoomIn.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            val scale = 25.0F
            imageView?.setZoom(scale)
            editor.putFloat(ZOOM,imageView?.currentZoom!!)
            editor.apply()
        }
        zoomOut.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            imageView?.resetZoom()
            editor.putFloat(ZOOM,imageView?.currentZoom!!)
            editor.apply()
        }
        up.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            imageView?.setScrollPosition(imageView?.scrollPosition?.x!!,imageView?.scrollPosition?.y!! - 0.1F)
            editor.putFloat(SCROLL_POSITION_X,imageView?.scrollPosition?.x!!)
            editor.putFloat(SCROLL_POSITION_Y,imageView?.scrollPosition?.y!!)
            editor.apply()
        }
        down.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            imageView?.setScrollPosition(imageView?.scrollPosition?.x!!,imageView?.scrollPosition?.y!! + 0.1F)
            editor.putFloat(SCROLL_POSITION_X,imageView?.scrollPosition?.x!!)
            editor.putFloat(SCROLL_POSITION_Y,imageView?.scrollPosition?.y!!)
            editor.apply()
        }
        right.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            imageView?.setScrollPosition(imageView?.scrollPosition?.x!! + 0.1F,imageView?.scrollPosition?.y!!)
            editor.putFloat(SCROLL_POSITION_X,imageView?.scrollPosition?.x!!)
            editor.putFloat(SCROLL_POSITION_Y,imageView?.scrollPosition?.y!!)
            editor.apply()
        }
        left.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            imageView?.setScrollPosition(imageView?.scrollPosition?.x!! - 0.1F,imageView?.scrollPosition?.y!!)
            editor.putFloat(SCROLL_POSITION_X,imageView?.scrollPosition?.x!!)
            editor.putFloat(SCROLL_POSITION_Y,imageView?.scrollPosition?.y!!)
            editor.apply()
        }

        imageView?.setOnTouchListener(handleTouch)

        sharedViewModel = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        if(args.stationNumber == -1){
            (activity as MainActivity?)
                ?.setActionBarTitle("Plano de Trabajo")
        }
        else{
            (activity as MainActivity?)
                ?.setActionBarTitle("Marcar Estacion ${args.stationNumber}")
            val intent = Intent("com.realwear.wearhf.intent.action.MOUSE_COMMANDS")
            intent.putExtra("com.realwear.wearhf.intent.extra.MOUSE_ENABLED", true)
            requireActivity().sendBroadcast(intent)
        }
        if(tecpronProjectSharedViewModel.tecpronMap.value != null)
            bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val stations = viewModel.stations.await()
        var station: Station? = null

        stations.observe(viewLifecycleOwner, Observer { stationResponse ->
            if (stationResponse == null) return@Observer
            stationResponse.forEach {
                if(it.mapPoint.isNotEmpty()){
                    if(args.stationNumber == it.stationNumber)
                        station = it
                    else
                        drawPermanentStation(it.mapPoint[0].toFloat(),it.mapPoint[1].toFloat(),it)
                }
                val bitmap = (imageView?.drawable as BitmapDrawable).bitmap
                workingBitmap = Bitmap.createBitmap(bitmap)
            }
            val savedZoom = sharedpreferences.getFloat(ZOOM,-100f)
            val savedX = sharedpreferences.getFloat(SCROLL_POSITION_X,-100f)
            val savedY = sharedpreferences.getFloat(SCROLL_POSITION_Y,-100f)
            if(savedZoom != -100f){
                imageView?.setZoom(savedZoom)
            }
            if(savedX != -100f && savedY != -100f){
                imageView?.setScrollPosition(savedX,savedY)
            }

        })
        if(!::workingBitmap.isInitialized){
            val bitmap = (imageView?.drawable as BitmapDrawable).bitmap
            workingBitmap = Bitmap.createBitmap(bitmap)
        }
        /*
        if(station != null){
            drawNewStation(station!!.mapPoint[0].toFloat(),station!!.mapPoint[1].toFloat(),station!!.stationNumber)
        }

         */
    }

    private fun drawPermanentStation(x: Float, y: Float, station: Station) {
        val bitmap = (imageView?.drawable as BitmapDrawable).bitmap
        val workingBitmap: Bitmap = Bitmap.createBitmap(bitmap)
        val mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true)

        val paint2 = Paint()

        paint2.color = Color.BLACK
        paint2.textSize = 12f
        paint2.isAntiAlias = true
        paint2.textAlign = Align.CENTER
        val text = station.stationNumber.toString()
        val bounds = Rect()
        paint2.getTextBounds(text, 0, text.length, bounds)
        var circleColor = 0
        when (station.scannerConfiguration.location){
            "Exterior" -> circleColor = Color.parseColor("#75FE2C")
            "Interior" -> circleColor = Color.parseColor("#34FFFF")
            "Compleja" -> circleColor = Color.parseColor("#B555FF")
        }

        val tempCanvas = Canvas(mutableBitmap)
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = circleColor
        paint.isAntiAlias = true
        drawTriangle(
            x.toInt(),
            y.toInt(),
            16,
            16,
            false,
            paint,
            tempCanvas
        )
//        tempCanvas.drawCircle(
//            x, // cx
//            y, // cy
//            8F, // Radius
//            paint // Paint
//        )

        tempCanvas.drawText(text,
            x+8, // cx
            y,
            paint2)

        imageView?.setImageBitmap(mutableBitmap)

    }

    private fun drawTriangle(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        inverted: Boolean,
        paint: Paint,
        canvas: Canvas
    ) {
        val p1 = Point(x, y)
        val pointX = x + width / 2
        val pointY = if (inverted) y + height else y - height
        val p2 = Point(pointX, pointY)
        val p3 = Point(x + width, y)
        val path = Path()
        path.fillType = FillType.EVEN_ODD
        path.moveTo(p1.x.toFloat(), p1.y.toFloat())
        path.lineTo(p2.x.toFloat(), p2.y.toFloat())
        path.lineTo(p3.x.toFloat(), p3.y.toFloat())
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawNewStation(xCord: Float, yCord: Float, stationNumber: Int) {
        imageView?.setImageBitmap(workingBitmap)
        val mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true)

        val eventXY = floatArrayOf(xCord, yCord)

        val invertMatrix = Matrix()
        imageView?.imageMatrix?.invert(invertMatrix)

        invertMatrix.mapPoints(eventXY)
        xCoordinate = Integer.valueOf(eventXY[0].toInt())
        yCoordinate = Integer.valueOf(eventXY[1].toInt())

        //Limit x, y range within bitmap
        //Limit x, y range within bitmap
        if (xCoordinate < 0) {
            xCoordinate = 0
        } else if (xCoordinate > workingBitmap.width - 1) {
            xCoordinate = workingBitmap.width - 1
        }

        if (yCoordinate < 0) {
            yCoordinate = 0
        } else if (yCoordinate > workingBitmap.height - 1) {
            yCoordinate = workingBitmap.height - 1
        }

        val paint2 = Paint()
        val circlePaint = Paint()

        paint2.color = Color.BLACK
        paint2.textSize = 9f
        paint2.isAntiAlias = true
        paint2.textAlign = Align.CENTER
        val text = stationNumber.toString()
        val bounds = Rect()
        paint2.getTextBounds(text, 0, text.length, bounds)

        circlePaint.color = Color.RED
        circlePaint.isAntiAlias = true

//        canvas.drawCircle(-3, 15 - bounds.height() / 2, bounds.width() + 5, circlePaint)
//
//        canvas.drawText(text, -3, 15, paint)


        //Create a new image bitmap and attach a brand new canvas to it
        //Create a new image bitmap and attach a brand new canvas to it
        val tempCanvas = Canvas(mutableBitmap)
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
        paint.isAntiAlias = true
        tempCanvas.drawCircle(
            xCoordinate.toFloat(), // cx
            yCoordinate.toFloat(), // cy
            8F, // Radius
            paint // Paint
        )

        tempCanvas.drawText(text,
            xCoordinate.toFloat(), // cx
            yCoordinate.toFloat(),
            paint2)

        imageView?.setImageBitmap(mutableBitmap)
        sharedViewModel.mapPoint.postValue(mutableListOf(xCoordinate,yCoordinate))

    }

    @SuppressLint("ClickableViewAccessibility")
    private val handleTouch: View.OnTouchListener = View.OnTouchListener { _, event ->
        if(args.stationNumber != -1){
            Log.e("PRUEBA", "${event.x} ${event.y}")
            drawNewStation(event.x,event.y,args.stationNumber)
        }
        true
    }

}
