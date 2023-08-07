package com.eduuh.medilabsapp

import android.animation.TimeAnimator.TimeListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.media.audiofx.BassBoost.Settings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.eduuh.medilabsapp.helpers.PrefsHelper
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class CheckoutStep1 : AppCompatActivity() {
    private lateinit var buttonDatePicker: Button
    private lateinit var editTextDate: EditText
    private lateinit var btnTimePicker: Button
    private lateinit var editTextTime: EditText

    fun showTimePicker(){
        val calender = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            timeSetListener,
            calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            false)
        timePickerDialog.show()
    }//end
    //https://justpaste.it/cmht0
    private val timeSetListener = TimePickerDialog.OnTimeSetListener{
            _, hourOfDay, minute ->

        val calendar = Calendar.getInstance()  //***********
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val sdf = SimpleDateFormat("hh:mm", Locale.getDefault())
        val selectedTime = sdf.format(calendar.time)
        editTextTime.setText(selectedTime)
    }//end


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_step1)

        btnTimePicker = findViewById(R.id.btnTimePicker)
        editTextTime = findViewById(R.id.editTextTime)
        btnTimePicker.setOnClickListener {
            showTimePicker()
        }//end

        //calender
        buttonDatePicker = findViewById(R.id.buttonDatePicker)
        editTextDate = findViewById(R.id.editTextDate)
        buttonDatePicker.setOnClickListener {
            showDatePickerDialog()
        }//end onclick

        //Find proceed button
        val proceed = findViewById<MaterialButton>(R.id.proceedstep2)
        proceed.setOnClickListener {
            val date = editTextDate.text.toString()
            val time = editTextTime.text.toString()
            //Radio Button Place
            val home = findViewById<RadioButton>(R.id.radioHome)
            val away = findViewById<RadioButton>(R.id.radioAway)
            var where_taken = ""
            if (home.isChecked){
                where_taken = "Home"
            }//end dif
            if (away.isChecked){
                where_taken = "Away"
            }//end if
            //Radio Button Self/Other
            val self = findViewById<RadioButton>(R.id.radioSelf)
            val other = findViewById<RadioButton>(R.id.radioOther)
            var booked_for = ""
            if (self.isChecked){
                booked_for = "Self"
            }//end
            if (other.isChecked){
                booked_for = "Other"
            }//end

            if (date.isEmpty() || time.isEmpty() || where_taken.isEmpty()
                || booked_for.isEmpty()){

                Toast.makeText(applicationContext, "Empty Fields",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                //Intent to GPS - TODO
                PrefsHelper.savePrefs(applicationContext, "date", date)
                PrefsHelper.savePrefs(applicationContext, "time", time)
                PrefsHelper.savePrefs(applicationContext, "where_taken", where_taken)
                PrefsHelper.savePrefs(applicationContext, "booked_for", booked_for)
                if (isLocationEnabled()) {
                    startActivity(Intent(applicationContext, CheckoutStep2GPS::class.java))
                }
                else {
                    Toast.makeText(applicationContext, "GPS Is OFF",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

            }//end else
        }//end listener
    }//End onCreate
    //justpaste.it/arfi6
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }//end




    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        // Create a date picker dialog and set the current date as the default selection
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = formatDate(year, month, day)
                editTextDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the date picker dialog
        datePickerDialog.show()
    }


    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    //Time Functions

}//end class