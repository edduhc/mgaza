package com.eduuh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eduuh.medilabsapp.adapters.LabTestsAdapter
import com.eduuh.medilabsapp.constants.Constants
import com.eduuh.medilabsapp.helpers.ApiHelper
import com.eduuh.medilabsapp.models.LabTests
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class LabTestsActivity : AppCompatActivity() {
    lateinit var itemList: List<LabTests>
    lateinit var labTestAdapter: LabTestsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var swiperefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_tests)

        progress = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.recycler)
        labTestAdapter = LabTestsAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        post_fetch()

        swiperefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swiperefresh.setOnRefreshListener{
            post_fetch()// fetch data again
        }//end refresh

    }//end oncreate


    fun post_fetch(){
        val api = Constants.BASE_URL+"/lab_tests"
        //Above Api needs a body, so we have to build it
        val body = JSONObject()
        //Retrieve the id from Intent Extras
        val id = intent.extras?.getString("key1")
        Toast.makeText(applicationContext, "ID $id", Toast.LENGTH_SHORT).show()
        //provide the ID to the Api
        body.put("lab_id", id)//NB: 4 is static
        val helper = ApiHelper(applicationContext)
        helper.post(api, body, object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(),
                    Array<LabTests>::class.java).toList()
                //Finally our adapter the data
                labTestAdapter.setListItems(itemList)
                recyclerView.adapter = labTestAdapter
                swiperefresh.isRefreshing = false
                progress.visibility = View.GONE
            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(),Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(result: String?) {
                progress.visibility = View.GONE

            }

        })
    }//End fetch





}




