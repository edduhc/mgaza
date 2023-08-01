package com.eduuh.medilabsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eduuh.medilabsapp.adapters.LabAdapter
import com.eduuh.medilabsapp.constants.Constants
import com.eduuh.medilabsapp.helpers.ApiHelper
import com.eduuh.medilabsapp.helpers.PrefsHelper
import com.eduuh.medilabsapp.helpers.SQLLiteHelper
import com.eduuh.medilabsapp.models.Lab
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    //Global Declaration - they can be accessed all over this class
    lateinit var itemList: List<Lab>
    lateinit var labAdapter: LabAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var swiperefresh: SwipeRefreshLayout

    fun update() {
        val user = findViewById<MaterialTextView>(R.id.user)
        val signin = findViewById<MaterialButton>(R.id.signin)
        val signout = findViewById<MaterialButton>(R.id.signout)
        val profile = findViewById<MaterialButton>(R.id.profile)

        signin.visibility = View.GONE
        signout.visibility = View.GONE
        profile.visibility = View.GONE
        val token = PrefsHelper.getPrefs(applicationContext, "refresh_tokrn")
        if (token.isEmpty()) {//token  not available
            user.text = "Not Logged In"
            signin.visibility = View.VISIBLE
            signin.setOnClickListener {
                startActivity(Intent(applicationContext, SignInActivity::class.java))
        }
    }else{//token  available
            profile.visibility = View.VISIBLE
            profile.setOnClickListener {
                startActivity(Intent(applicationContext, MemberProfile::class.java))
            }
            val surname = PrefsHelper.getPrefs(applicationContext,"surname")
            user.text = "Welcome $surname"
            signout.visibility = View.VISIBLE
        }

    }//END

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        update()
        progress = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.recycler)
        labAdapter = LabAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        //call the function
        fetchData()

        swiperefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swiperefresh.setOnRefreshListener{
            fetchData()// fetch data again
        }//end refresh

        //Filter labs
        val etsearch = findViewById<EditText>(R.id.etsearch)
        etsearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(texttyped: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(texttyped.toString())
            }

            override fun onTextChanged(texttyped: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }//oncreate

    fun fetchData(){
        //paste here
        //Go to the Api get the data
        val api = Constants.BASE_URL+"/laboratories"
        val helper = ApiHelper(applicationContext)
        helper.get(api, object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                //Take above result to adapter
                swiperefresh.isRefreshing = false
                //Convert above result from Json array to LIST<Lab>


                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(),
                    Array<Lab>::class.java
                ).toList()
                //Finally our adapter the data
                labAdapter.setListItems(itemList)
                recyclerView.adapter = labAdapter
                progress.visibility = View.GONE
            }

            override fun onSuccess(result: JSONObject?) {

            }

            override fun onFailure(result: String?) {
                progress.visibility = View.GONE

            }

        })
    }//end fetch data

    //Filter function
    //justepaste.it/9j21s
    //Filter
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Lab> = ArrayList()
        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.lab_name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
        // if no item is added in filtered list we are
        // displaying a toast message as no data found.
        //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            labAdapter.filterList(filteredlist)
        } else {
          // at last we are passing that filtered
          // list to our adapter class.
            labAdapter.filterList(filteredlist)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val item: MenuItem = menu!!.findItem(R.id.mycart)
        item.setActionView(R.layout.design)
        val actionView: View? = item.actionView
        val number = actionView?.findViewById<TextView>(R.id.badge)
        val image = actionView?.findViewById<ImageView>(R.id.image)
        image?.setOnClickListener{
            startActivity(Intent(applicationContext, MyCart::class.java))
        }
        val helper = SQLLiteHelper(applicationContext)
        number?.text = ""+helper.getNumItems()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mycart){
            startActivity(Intent(applicationContext,MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)
    }//end

}