package com.eduuh.medilabsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.eduuh.medilabsapp.R
import android.content.Context
import android.content.Intent
import com.eduuh.medilabsapp.LabTestsActivity
import com.eduuh.medilabsapp.models.Lab
import com.google.android.material.textview.MaterialTextView

class LabAdapter(var context:Context):
        RecyclerView.Adapter<LabAdapter.ViewHolder>() {

    //craete a list and connect it with our model
    var itemList : List<Lab> = listOf() //Its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //create a class here, will hold our views in single_lab xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabAdapter.ViewHolder {
       //access /inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_lab, parent, false)
        return ViewHolder(view) //pass the single lab to viewHolder
    }

    override fun onBindViewHolder(holder: LabAdapter.ViewHolder, position: Int) {
        //Find your 3 text
        val lab_name = holder.itemView.findViewById<MaterialTextView>(R.id.lab_name)
        val permit_id = holder.itemView.findViewById<MaterialTextView>(R.id.permit_id)
        val email = holder.itemView.findViewById<MaterialTextView>(R.id.email)
        val lab = itemList[position]
        lab_name.text = lab.lab_name
        permit_id.text = lab.permit_id
        email.text = lab.email

        holder.itemView.setOnClickListener{
            //carry the lab id of what you clicked
            //carry it with bundles, peferencs
            val id = lab.lab_id
            val name = lab.lab_name
            //pass this id along with intent, i prefer shred preferences
            //save id to prefs
            val i = Intent(context, LabTestsActivity::class.java)
            i.putExtra("key1", id)
            i.putExtra("key2", name)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size //count how many items in the list
    }

    fun filterList(filterList: List<Lab>){
        itemList = filterList
        notifyDataSetChanged()
    }

    //Earlier we mentioned item list is empty!
    //we will get data from our Api, then bring it to below function
    fun setListItems(data:List<Lab>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged() //Tell this adapter class that now itemlist is loaded with data
    }//
}