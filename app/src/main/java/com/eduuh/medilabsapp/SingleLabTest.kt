package com.eduuh.medilabsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eduuh.medilabsapp.helpers.SQLLiteHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class SingleLabTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_lab_test)
        //find the textView
        val tvtest_id = findViewById<MaterialTextView>(R.id.test_id)
        //Get test id value from Intent Extras
        val test_id = intent.extras?.getString("test_id")
        //put the test id value to tvtestid
        tvtest_id.text = test_id
        //===========
        val tvtest_name = findViewById<MaterialTextView>(R.id.test_name)
        val test_name = intent.extras?.getString("test_name")
        tvtest_name.text = test_name
        //===========

        val tvtest_cost = findViewById<MaterialTextView>(R.id.test_cost)
        val test_cost = intent.extras?.getString("test_cost")
        tvtest_cost.text = "KES $test_cost"
        //===========

        val tvtest_discount = findViewById<MaterialTextView>(R.id.test_discount)
        val test_discount = intent.extras?.getString("test_discount")
        tvtest_discount.text = "$test_discount% OFF"
        //===========

        val tvtest_description = findViewById<MaterialTextView>(R.id.test_description)
        val test_description = intent.extras?.getString("test_description")
        tvtest_description.text = test_description
        //===========

        val tvtest_availability = findViewById<MaterialTextView>(R.id.test_availability)
        val test_availability = intent.extras?.getString("test_availability")
        tvtest_availability.text = test_availability
        //===========

        val tvtest_info = findViewById<MaterialTextView>(R.id.more_info)
        val more_info = intent.extras?.getString("more_info")
        tvtest_info.text = more_info

        val addcart = findViewById<MaterialButton>(R.id.addcart)
        addcart.setOnClickListener{
            val helper = SQLLiteHelper(applicationContext)
            val lab_id = intent.extras?.getString("lab_id")

            try {
                helper.insert(test_id!!, test_name!!, test_cost!!, test_description!!, lab_id!!)
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, "An error occured", Toast.LENGTH_SHORT).show()
            }

        }//end onclick

    }//end oncreate

    override fun onBackPressed() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finishAffinity()

    }
}//end class
