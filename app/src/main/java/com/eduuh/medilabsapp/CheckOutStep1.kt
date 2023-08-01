 package com.eduuh.medilabsapp

import android.animation.TimeAnimator.TimeListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
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
             applicationContext,
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
         val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
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

     }//End onCreate

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