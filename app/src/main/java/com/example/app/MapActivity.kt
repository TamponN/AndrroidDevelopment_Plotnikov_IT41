package com.example.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapActivity : AppCompatActivity() {
    private lateinit var _mapView: MapView
    private lateinit var _fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
            showUserLocation()
        } else {
            Toast.makeText(this, "Разрешение на геолокацию не предоставлено", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)

        _mapView = findViewById(R.id.map_view)
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Начальная настройка карты
        _mapView.setTileSource(TileSourceFactory.MAPNIK) // стиль
        _mapView.setMultiTouchControls(true) // зум жестами
        _mapView.controller.setZoom(15.0) // Начальный зум

        checkLocationPermissionAndSetupMap()
    }

    private fun checkLocationPermissionAndSetupMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            showUserLocation()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    private fun showUserLocation() {
        // проверка на разрешение доступа к геолокации
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        // есть разрешение, но не получилось найти
        _fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val userGeoPoint = GeoPoint(location.latitude, location.longitude)

                // Центрируем карту на пользователе
                _mapView.controller.setCenter(userGeoPoint)

                // Создаем маркер
                val userMarker = Marker(_mapView)
                userMarker.position = userGeoPoint
                userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                userMarker.title = "Вы здесь"

                // Очищаем старые маркеры и добавляем новый
                _mapView.overlays.clear()
                _mapView.overlays.add(userMarker)
                _mapView.invalidate() // Перерисовываем карту

            } else {
                Toast.makeText(this, "Не удалось определить местоположение. Включите GPS.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // из документации OSMDroid
    override fun onResume() {
        super.onResume()
        _mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        _mapView.onPause()
    }
}
