package com.eduuh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.widget.Toast
import com.eduuh.medilabsapp.helpers.ApiHelper
import com.eduuh.medilabsapp.helpers.PrefsHelper
import com.eduuh.medilabsapp.helpers.SQLLiteHelper
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

class Complete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        //Lets get to SQLIte, fetch the Tests picked by user
        val sqlLitehelper = SQLLiteHelper(applicationContext)
        val items = sqlLitehelper.getAllItems()
        items.forEach(){
            //Capture test_id/lab_id at a given loop+++++++
            val test_id = it.test_id
            val lab_id = it.lab_id

            val member_id = PrefsHelper.getPrefs(this,"member_id")
            val date = PrefsHelper.getPrefs(this,"date")
            val time = PrefsHelper.getPrefs(this,"time")
            val booked_for = PrefsHelper.getPrefs(this,"booked_for")
            val where_taken = PrefsHelper.getPrefs(this,"where_taken")
            val latitude = PrefsHelper.getPrefs(this,"latitude")
            val longitude = PrefsHelper.getPrefs(this,"longitude")
            val dependant_id = PrefsHelper.getPrefs(this,"dependant_id")
            val invoice_no = generateInvoiceNumber() //Autogenerate

            val helper = ApiHelper(this)
            val api = com.eduuh.medilabsapp.constants.Constants.BASE_URL + "/make_booking"
            val body = JSONObject()
            body.put("member_id", member_id)
            body.put("appointment_date", date)
            body.put("appointment_time", time)
            body.put("booked_for", booked_for)
            body.put("where_taken", where_taken)
            body.put("latitude", latitude)
            body.put("longitude", longitude)
            body.put("dependant_id", dependant_id)
            body.put("test_id", test_id)
            body.put("lab_id", lab_id)
            body.put("invoice_no", invoice_no)

            helper.post(api, body, object : ApiHelper.CallBack{
                override fun onSuccess(result: JSONObject?) {
                    Toast.makeText(applicationContext, result.toString(),
                        Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(applicationContext, result.toString(),
                        Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(result: JSONArray?) {
                    TODO("Not yet implemented")
                }
            })//end post
        }//End foreach

    }

    fun generateInvoiceNumber(): String{
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val curreTime = Date()
        val timestamp = dateFormat.format(curreTime)

        //You can use a random number or a sequential number to add uniqueniness
        //For example, using a random number
        val random = Random()
        val randomNumber = random.nextInt(1000) // Change the upper bound

        //Combine the timestamp and random number to create the invoice number
        return "INV-$timestamp-$randomNumber"
    }
}//end class