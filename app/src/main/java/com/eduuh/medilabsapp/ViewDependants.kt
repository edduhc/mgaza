package com.eduuh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eduuh.medilabsapp.adapters.DependantAdapter
import com.eduuh.medilabsapp.constants.Constants
import com.eduuh.medilabsapp.helpers.ApiHelper
import com.eduuh.medilabsapp.helpers.PrefsHelper
import com.eduuh.medilabsapp.models.Dependant
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class ViewDependants : AppCompatActivity() {
    lateinit var itemList: List<Dependant>
    lateinit var depAdapter: DependantAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var swiperefresh: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_dependants)

        progress = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.recycler)
        depAdapter = DependantAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        post_fetch()

        swiperefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swiperefresh.setOnRefreshListener {
            post_fetch()// fetch data again
        }//end refresh

        //Filter labs
        val etsearch = findViewById<EditText>(R.id.etsearch)
        etsearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(texttyped: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(texttyped.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }//end oncreate

    fun post_fetch(){
        val api = Constants.BASE_URL+"/view_dependants"
        //Above APi needs a Body, So we have to build it
        val body = JSONObject()
        //Retrieve the id from Prefs
        val member_id = PrefsHelper.getPrefs(applicationContext,"member_id")
        //provide the ID to the API
        body.put("member_id", member_id) //NB: 4 is static
        val helper = ApiHelper(applicationContext)
        helper.post(api, body, object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(),
                    Array<Dependant>::class.java).toList()
                //Finally, our adapter has the data
                depAdapter.setListItems(itemList)
                //For the sake of recycling/Looping items, add the adapter to recycler
                recyclerView.adapter = depAdapter
                progress.visibility = View.GONE
                swiperefresh.isRefreshing = false
            }
            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(),
                    Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
            }

            override fun onFailure(result: String?) {

            }
        })
    }//end fetch


    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Dependant> = ArrayList()
        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.surname.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            depAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            depAdapter.filterList(filteredlist)
        }
    }


    }
