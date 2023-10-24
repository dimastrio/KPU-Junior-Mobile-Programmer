package id.dimas.kpu

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import id.dimas.kpu.databinding.ActivityMapsBinding
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var binding: ActivityMapsBinding

    private lateinit var gMaps: GoogleMap

    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var from = ""
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//        if (permissionState != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
//        }

        binding.btnPosition.setOnClickListener {
            getLocation()
        }

        setMapOnFragment()

    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 8f, this)
    }

    override fun onLocationChanged(location: Location) {
//        Toast.makeText(this, "${location.latitude} , ${location.longitude}", Toast.LENGTH_SHORT).show()
        onClickPosition(location.latitude, location.longitude)
    }


    private fun setMapOnFragment() {
//        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        gMaps = googleMap

        if (checkLocationPermission()) {
            gMaps.isMyLocationEnabled = true
        }
//        if (latitude != 0.0 && longitude != 0.0) {
//            Toast.makeText(this, "Latitude : $latitude, Longitude: $longitude", Toast.LENGTH_SHORT)
//                .show()
//        }
    }

    private fun getLocationAddress(address: String) {
        val intent = Intent()
        intent.putExtra("address", address)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onClickPosition(latitude: Double, longitude: Double) {
        if (latitude != 0.0 && longitude != 0.0) {
            val geocoder = Geocoder(this@MapsActivity)
            try {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
//                Toast.makeText(this, addresses!![0].getAddressLine(0), Toast.LENGTH_SHORT).show()
                getLocationAddress((addresses!![0].getAddressLine(0).toString()))

//            Toast.makeText(this, "${"Latitude: " + latitude + " , Longitude: " + longitude}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }
//
//    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
//    private fun requestLocationPermission() {
//        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
//        if (EasyPermissions.hasPermissions(this, *perms)) {
//            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
//        } else {
//            EasyPermissions.requestPermissions(
//                this,
//                "Please grant the location permission",
//                REQUEST_LOCATION_PERMISSION,
//                *perms
//            )
//        }
//    }

    private fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Perizinan lokasi")
                    .setPositiveButton(
                        "Ya"
                    ) { dialogInterface, i -> //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this@MapsActivity,
                            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            false
        } else {
            true
        }
    }


    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 1
        const val REQUEST_LOCATION_PERMISSION = 1
    }


}