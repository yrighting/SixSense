package com.sixsense.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.sixsense.app.R

class MapSelectActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_select)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val swu = LatLng(37.6294, 127.0906)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(swu, 15f))

        mMap.setOnMapClickListener { latLng ->
            val address = "위도: ${latLng.latitude}, 경도: ${latLng.longitude}"
            val resultIntent = Intent()
            resultIntent.putExtra("location", address)
            resultIntent.putExtra("latitude", latLng.latitude)
            resultIntent.putExtra("longitude", latLng.longitude)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}