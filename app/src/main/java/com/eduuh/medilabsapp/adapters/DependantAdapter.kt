package com.eduuh.medilabsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eduuh.medilabsapp.R
import com.eduuh.medilabsapp.models.Dependant
import com.google.android.material.textview.MaterialTextView

class DependantAdapter(var context: Context):
    RecyclerView.Adapter<DependantAdapter.ViewHolder>() {


    //Create a List and connect it with our model
    var itemList : List<Dependant> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DependantAdapter.ViewHolder {
        //access/inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_dependant,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: DependantAdapter.ViewHolder, position: Int) {
        //Find your 3 text views
        val dep_name = holder.itemView.findViewById<MaterialTextView>(R.id.dep_name)
        val dep_others = holder.itemView.findViewById<MaterialTextView>(R.id.dep_others)
        val dep_dob = holder.itemView.findViewById<MaterialTextView>(R.id.dep_dob)
        //Assume one Lab
        val item = itemList[position]
        dep_name.text = item.surname
        dep_others.text = item.others
        dep_dob.text = item.dob
//         holder.itemView.setOnClickListener {
//             val i = Intent(context, SingleLabTest::class.java)
//             i.putExtra("lab_id", item.lab_id)
//             i.putExtra("test_id", item.test_id)
//             i.putExtra("test_discount", item.test_discount)
//             i.putExtra("test_cost", item.test_cost)
//             i.putExtra("test_name", item.test_name)
//             i.putExtra("test_description", item.test_description)
//             i.putExtra("availability", item.availability)
//             i.putExtra("more_info", item.more_info)
//             i.putExtra("reg_date", item.reg_date)
//             i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//             context.startActivity(i)
//         }//end
        // Toast.makeText(context, "yyy"+item.test_cost, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return itemList.size  //Count how may Items in the List
    }

    //This is for filtering data
    fun filterList(filterList: List<Dependant>){
        itemList = filterList
        notifyDataSetChanged()
    }


    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<Dependant>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
        //Tell this adapter class that now itemList is loaded with data
    }
    //justpaste.it/cgaym
}