package com.eduuh.medilabsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.eduuh.medilabsapp.R
import android.content.Context
import android.content.Intent
import com.eduuh.medilabsapp.LabTestsActivity
import com.eduuh.medilabsapp.SingleLabTest
import com.eduuh.medilabsapp.helpers.SQLLiteHelper
import com.eduuh.medilabsapp.models.Lab
import com.eduuh.medilabsapp.models.LabTests
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class LabTestsCartAdapter(var context:Context):
    RecyclerView.Adapter<LabTestsCartAdapter.ViewHolder>() {

    //craete a list and connect it with our model
    var itemList : List<LabTests> = listOf() //Its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //create a class here, will hold our views in single_lab xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabTestsCartAdapter.ViewHolder {
        //access /inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_labtests_cart, parent, false)
        return ViewHolder(view) //pass the single lab to viewHolder
    }

    override fun onBindViewHolder(holder: LabTestsCartAdapter.ViewHolder, position: Int) {
        //Find your 3 text
        val test_name = holder.itemView.findViewById<MaterialTextView>(R.id.test_name)
        val test_description = holder.itemView.findViewById<MaterialTextView>(R.id.test_description)
        val test_cost = holder.itemView.findViewById<MaterialTextView>(R.id.test_cost)
        //Assume one lab
        val item = itemList[position]
        test_name.text = item.test_name
        test_description.text = item.test_description
        test_cost.text = "KES " + item.test_cost
        //Find remove button and set listener
        val remove = holder.itemView.findViewById<MaterialButton>(R.id.remove)
        remove.setOnClickListener{
            val test_id = item.test_id
            val helper = SQLLiteHelper(context)
            helper.clearCartById(test_id)
            //The
        }


    }

    override fun getItemCount(): Int {
        return itemList.size //count how many items in the list
    }

    fun filterList(filterList: List<LabTests>){
        itemList = filterList
        notifyDataSetChanged()
    }

    //Earlier we mentioned item list is empty!
    //we will get data from our Api, then bring it to below function
    fun setListItems(data:List<LabTests>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged() //Tell this adapter class that now itemlist is loaded with data
    }//
}