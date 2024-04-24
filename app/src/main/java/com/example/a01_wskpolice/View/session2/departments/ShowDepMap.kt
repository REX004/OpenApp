package com.example.a01_wskpolice.View.session2.departments
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.R
import com.example.a01_wskpolice.databinding.IconForegroundBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider


class ShowDepMap : AppCompatActivity() {
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey("secret")
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        setContentView(R.layout.show_dep_map_activity)
        mapView = findViewById(R.id.mapView);
        var text = assets.open("customizations.json").bufferedReader().readText()
        println(text);
        mapView.map.setMapStyle(text);

        val latitude = intent.getDoubleExtra("departmentLatitude", 0.0)
        Log.e("ShowMapActivity", latitude.toString())
        val longitude = intent.getDoubleExtra("departmentLongitude", 0.0)
        Log.e("ShowMapActivity", longitude.toString())

        val placemark: PlacemarkMapObject = mapView.map.mapObjects.addPlacemark()
        placemark.geometry = Point(56.4343434, 65.4344234)
        val view = IconForegroundBinding.inflate(layoutInflater);
        placemark.setView(ViewProvider(view.root), IconStyle().apply { scale = 0.2f })

        setMapStyle()
    }
    fun setMapStyle(){
        val style = "InputStream"
        mapView.map.setMapStyle(style)
    }
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }
    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
