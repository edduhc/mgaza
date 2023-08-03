package com.eduuh.medilabsapp

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class CheckOutStep2GPS : AppCompatActivity() {
    private lateinit var editLatitude: TextInputEditText
    private lateinit var editLongitude: TextInputEditText
    private lateinit var getlocation: MaterialButton
    private lateinit var progress: ProgressBar
    private lateinit var skip: MaterialTextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_step2_gps)
        editLatitude = findViewById(R.id.editLatitude)
        editLongitude = findViewById(R.id.editLongitude)
        progress = findViewById(R.id.progress)
        getlocation = findViewById(R.id.getlocation)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        progress.visibility = View.GONE
        getlocation.setOnClickListener {
            //TODO
            progress.visibility = View.VISIBLE
            requestLocation()
        }//END
    }//end oncreate

    //Function to check if user accepted permission or not
    //If user has not accepted permissions, give them dialog to decide
    fun requestLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
                 ActivityCompat.requestPermissions(
                     this,
                 arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                     android.Manifest.permission.ACCESS_COARSE_LOCATION),
                     123)
        }//END IF
        else{
           getlocation() //get lat and lon
        }
    }//end function
    @SuppressLint("MissingPermission")
    fun getlocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                location ->
                location?.let{
                    editLatitude.setText(it.latitude.toString())
                    editLongitude.setText(it.longitude.toString())
                    progress.visibility = View.GONE
                } ?: run {
                    Toast.makeText(applicationContext,"Location not found",
                    Toast.LENGTH_SHORT).show()
                    progress.visibility = View.GONE
                }//end run
            }//end success
            .addOnFailureListener{ e ->
                Toast.makeText(applicationContext,"Error $e", Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
            }//end failure
    }//end function

    lateinit var mLocationCallback: LocationCallback
    fun requestNewLoction(){
        progress
        Log.d("hhhhhh","Requesting New Location")
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 10000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationCallback = object  : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                for(location in result.locations){
                    if (location!=null){
                        editLatitude.setText(location.latitude.toString())
                        editLongitude.setText(location.longitude.toString())
                        progress.visibility = View.GONE
                    }//end if
                    else{
                        Toast.makeText(applicationContext,"Check GPS Settings",
                        Toast.LENGTH_SHORT).show()
                    }//end else
                }//end for
            }//end result
        }//end callback

    }//end function
}//end class
