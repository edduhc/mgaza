package com.eduuh.medilabsapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.eduuh.medilabsapp.constants.Constants
import com.eduuh.medilabsapp.helpers.ApiHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var buttonDatePicker: Button
    private lateinit var editTextDate: EditText
    private lateinit var spinner: Spinner
    private lateinit var selectedItemText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val linktologin = findViewById<MaterialTextView>(R.id.linktologin)
        linktologin.setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
        }

        buttonDatePicker = findViewById(R.id.buttonDatePicker)
        editTextDate = findViewById(R.id.editTextDate)

        buttonDatePicker.setOnClickListener {
            showDatePickerDialog()
        }//end onclick
        //Spinner
        spinner = findViewById(R.id.spinner)
        selectedItemText = findViewById(R.id.selecteditemtext)
        // Sample data for the spinner
        val data = listOf(" 1", " 2", " 3", " 4", " 5")//pending
        // Create an ArrayAdapter using the sample data
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set the adapter to the spinner
        spinner.adapter = adapter


        //push/post data to Api
        val surname = findViewById<TextInputEditText>(R.id.surname)
        val others = findViewById<TextInputEditText>(R.id.others)
        val email = findViewById<TextInputEditText>(R.id.email)
        val phone = findViewById<TextInputEditText>(R.id.phone)
        val password = findViewById<TextInputEditText>(R.id.password)
        val confirm = findViewById<TextInputEditText>(R.id.confirm)
        val female = findViewById<RadioButton>(R.id.radioFemale)
        val male = findViewById<RadioButton>(R.id.radioMale)

        val create = findViewById<MaterialButton>(R.id.create)
        create.setOnClickListener {
        var gender = "N/A"
        if (female.isChecked){gender = "female"}
        if (male.isChecked){gender = "male"}
        if (password.text.toString() != confirm.text.toString()){
            Toast.makeText(applicationContext,"Password do not match",
            Toast.LENGTH_SHORT).show()
        }
        else {

                val api = Constants.BASE_URL+"/member_signup"
                val helper = ApiHelper(applicationContext)
                val body = JSONObject()
                body.put("surname", surname.text.toString())
                body.put("others", others.text.toString())
                body.put("email", email.text.toString())
                body.put("phone", phone.text.toString())
                body.put("password", password.text.toString())
                body.put("gender", gender)
                body.put("location_id", spinner.selectedItem.toString())
                body.put("dob", editTextDate.text.toString())
                helper.post(api,body, object : ApiHelper.CallBack {
                    override fun onSuccess(result: JSONArray?) {

                    }

                    override fun onSuccess(result: JSONObject?) {
                        Toast.makeText(applicationContext, result.toString() , Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(result: String?) {

                    }

                })
            }



        }
    }//end oncreate

    //other functions
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


}//end class



