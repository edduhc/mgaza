package com.eduuh.medilabsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eduuh.medilabsapp.adapters.LabTestsAdapter
import com.eduuh.medilabsapp.adapters.LabTestsCartAdapter
import com.eduuh.medilabsapp.helpers.PrefsHelper
import com.eduuh.medilabsapp.helpers.SQLLiteHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MyCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        //put total cost in a textview

        val helper = SQLLiteHelper(applicationContext)
        val checkout = findViewById<MaterialButton>(R.id.checkout)
        if (helper.totalCost() == 0.0){
            checkout.visibility = View.GONE
        }//end
        checkout.setOnClickListener{
            val token = PrefsHelper.getPrefs(applicationContext,"refresh_token")
            if (token.isEmpty()){
                Toast.makeText(applicationContext,"Not Logged In",
                Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, SignInActivity::class.java))
                finish()
            }
            else{
                //TODO
                startActivity(Intent(applicationContext, CheckoutStep1::class.java))
                Toast.makeText(applicationContext,"Logged In", Toast.LENGTH_SHORT).show()
            }
        }//End

        val total = findViewById<MaterialTextView>(R.id.total)
//        val helper = SQLLiteHelper(applicationContext)
        total.text = "Total: "+helper.totalCost()

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        recycler.setHasFixedSize(true)
        //Access adapter and provide it with from getAllItems
        if(helper.getNumItems()==0){
            Toast.makeText(applicationContext,"Your cart is empty",Toast.LENGTH_SHORT).show()
        }
        else{
            val adapter = LabTestsCartAdapter(applicationContext)
            adapter.setListItems(helper.getAllItems())//pass data
            recycler.adapter = adapter//link adapter to recycler
        }

    }

    //Activate options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearcart){
            val helper = SQLLiteHelper(applicationContext)
            helper.clearcart()
        }

        if (item.itemId == R.id .backtolabs){
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finishAffinity()
    }

}
