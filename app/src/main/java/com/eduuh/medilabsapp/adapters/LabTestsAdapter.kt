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
import com.eduuh.medilabsapp.models.Lab
import com.eduuh.medilabsapp.models.LabTests
import com.google.android.material.textview.MaterialTextView

class LabTestsAdapter(var context:Context):
    RecyclerView.Adapter<LabTestsAdapter.ViewHolder>() {

    //craete a list and connect it with our model
    var itemList : List<LabTests> = listOf() //Its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //create a class here, will hold our views in single_lab xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabTestsAdapter.ViewHolder {
        //access /inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_labtests, parent, false)
        return ViewHolder(view) //pass the single lab to viewHolder
    }

    override fun onBindViewHolder(holder: LabTestsAdapter.ViewHolder, position: Int) {
        //Find your 3 text
        val test_name = holder.itemView.findViewById<MaterialTextView>(R.id.test_name)
        val test_description = holder.itemView.findViewById<MaterialTextView>(R.id.test_description)
        val test_cost = holder.itemView.findViewById<MaterialTextView>(R.id.test_cost)
        //Assume one lab
        val item = itemList[position]
        test_name.text = item.test_name
        test_description.text = item.test_description
        test_cost.text = "KES " + item.test_cost
        holder.itemView.setOnClickListener{
            val i = Intent(context, SingleLabTest::class.java)
            i.putExtra("lab_id", item.lab_id)
            i.putExtra("test_id", item.test_id)
            i.putExtra("test_discount", item.test_discount)
            i.putExtra("test_cost", item.test_cost)
            i.putExtra("test_name", item.test_name)
            i.putExtra("test_description", item.test_description)
            i.putExtra("availability", item.availability)
            i.putExtra("more_info", item.more_info)
            i.putExtra("reg_date", item.reg_date)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
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