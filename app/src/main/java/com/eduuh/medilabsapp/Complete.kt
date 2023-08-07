package com.eduuh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.widget.Toast
import com.eduuh.medilabsapp.helpers.ApiHelper
import com.eduuh.medilabsapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class Complete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        val member_id = PrefsHelper.getPrefs(this,"member_id")
        val date = PrefsHelper.getPrefs(this,"date")
        val time = PrefsHelper.getPrefs(this,"time")
        val booked_for = PrefsHelper.getPrefs(this,"booked_for")
        val where_taken = PrefsHelper.getPrefs(this,"where_taken")
        val latitude = PrefsHelper.getPrefs(this,"latitude")
        val longitude = PrefsHelper.getPrefs(this,"longitude")
        val dependant_id = 4
        val test_id = 1
        val lab_id = 4
        val invoice_no = "5454545"

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
                TODO("Not yet implemented")
            }

            override fun onSuccess(result: JSONArray?) {
                TODO("Not yet implemented")
            }
        })
    }
}