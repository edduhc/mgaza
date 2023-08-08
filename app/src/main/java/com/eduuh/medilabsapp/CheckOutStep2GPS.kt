package com.eduuh.medilabsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.eduuh.medilabsapp.helpers.PrefsHelper
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class CheckOutStep2GPS : AppCompatActivity() {
    private  lateinit var editLatitude: TextInputEditText
    private lateinit var editLongitude: TextInputEditText
    private lateinit var getlocation: MaterialButton
    private lateinit var progress: ProgressBar
    private lateinit var skip: MaterialTextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var showMap : MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_step2_gps)
        editLatitude = findViewById(R.id.editLatitude)
        editLongitude = findViewById(R.id.editLongitude)
        progress = findViewById(R.id.progress)
        skip = findViewById(R.id.skip)
        getlocation = findViewById(R.id.getlocation)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        progress.visibility = View.GONE
        getlocation.setOnClickListener {
            //TODO
            progress.visibility = View.VISIBLE
            requestLocation()
        }//end

        showMap = findViewById(R.id.showMapButton)
        showMap.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:${editLatitude.text}, ${editLongitude.text}")))
        }


        val complete = findViewById<MaterialButton>(R.id.complete)
        complete.setOnClickListener {
            PrefsHelper.savePrefs(applicationContext, "latitude",
                editLatitude.text.toString())
            PrefsHelper.savePrefs(applicationContext, "longitude",
                editLongitude.text.toString())
            val intent = Intent(applicationContext, Complete::class.java)
            startActivity(intent)
        }

    }//end onCreate

    //Function to check if user accepted permission or not
    //If user has not accepted permissions, Give them dialog to decide
    fun requestLocation(){
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                123)
        }//end if
        else {
            getLocation() //get Lat and Lon
        }
    }//end function

    fun getAddress(latlng: LatLng) : String{
        val geoCoder = Geocoder(this)
        val list = geoCoder.getFromLocation(latlng.latitude, latlng.longitude,
            1)
        return if (list!!.isEmpty()){""}else {list!![0].getAddressLine(0)}
    }//end

    @SuppressLint("MissingPermission")
    fun getLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                    location ->
                location?.let {
                    editLatitude.setText(it.latitude.toString())
                    editLongitude.setText(it.longitude.toString())
                    progress.visibility = View.GONE

                    val place = getAddress(LatLng(it.latitude, it.longitude));
                    Toast.makeText(applicationContext,"here $place",
                        Toast.LENGTH_SHORT).show()
                    //put the place ia TextView
                    val skip = findViewById<MaterialTextView>(R.id.skip)
                    skip.text = "Current Location \n $place"
                    requestNewLocation()

                } ?: run {
                    Toast.makeText(applicationContext, "Searching Location",
                        Toast.LENGTH_SHORT).show()
                    progress.visibility = View.GONE
                    requestNewLocation()
                } //end run
            }//end success
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, "Error $e", Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE


            }//end Failure
    }//end function

    lateinit var mLocationCallback: LocationCallback
    @SuppressLint("MissingPermission")
    fun requestNewLocation(){
        progress.visibility = View.VISIBLE
        Log.d("hhhhhh", "Requesting New Location")
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 10000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                for(location in result.locations){
                    if (location!=null){
                        editLatitude.setText(location.latitude.toString())
                        editLongitude.setText(location.longitude.toString())
                        progress.visibility = View.GONE

                        PrefsHelper.savePrefs(applicationContext, "latitude", editLatitude.text.toString())
                        PrefsHelper.savePrefs(applicationContext, "longitude", editLongitude.text.toString())
                    }//end if
                    else {
                        Toast.makeText(applicationContext, "Check GPS",
                            Toast.LENGTH_SHORT).show()
                    }//end else
                }//end for
            }//end result
        }//end call back

        fusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback, Looper.getMainLooper())

    }//end function

}//end class