package com.eduuh.medilabsapp.helpers

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.eduuh.medilabsapp.LabTestsActivity
import com.eduuh.medilabsapp.MyCart
import com.eduuh.medilabsapp.models.LabTests

class SQLLiteHelper(context: Context):
    SQLiteOpenHelper(context,"cart1.db", null, 2){
    //
    val context = context

    //SQLite helps store data locally on your phone
    override fun onCreate(sql: SQLiteDatabase?) {
        sql?.execSQL("CREATE TABLE IF NOT EXISTS cart(test_id Integer primary key, test_name varchar, test_cost Integer, test_description text, lab_id Integer)")
    }

    override fun onUpgrade(sql: SQLiteDatabase?, p1: Int, p2: Int) {
       sql?.execSQL("DROP TABLE IF EXISTS cart")
    }

    //INSERT save to cart
    fun insert(test_id: String, test_name: String, test_cost: String, test_description: String, lab_id: String){
        val db = this.writableDatabase
        //Select before insert see if ID already exists
        val values = ContentValues()
        values.put("test_id", test_id)
        values.put("test_name", test_name)
        values.put("test_cost", test_cost)
        values.put("test_description", test_description)
        values.put("lab_id", lab_id)
        //save
        val result:Long = db.insert("cart", null, values)
        if (result <1){
            println("Failed to add")
            Toast.makeText(context,"Item alaready in cart",Toast.LENGTH_SHORT).show()
        }
        else{
            println("Item Added to cart")
            Toast.makeText(context,"Item Added to cart",Toast.LENGTH_SHORT).show()
        }
    }

    //TODO GetRecords, Delete all, delete one, Gte Totals
    //count how many items are there in the cart table

    fun getNumItems(): Int {
        val db = this.readableDatabase
        val result:Cursor = db.rawQuery("select * from cart ", null)
        //return result count
        return  result.count
    }//end

    //clear all records
    fun clearcart(){
        val db = this.writableDatabase
        db.delete("cart", null,null)
        println("cart Cleared")
        Toast.makeText(context,"cart Cleared",Toast.LENGTH_SHORT).show()
        val i = Intent(context, MyCart::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)

    }//end

    //remove one item

    fun clearCartById(test_id: String){
        val db = this.writableDatabase
        //provide the test_id when deleting
        db.delete("cart", "test_id=?", arrayOf(test_id))
        println("Item Id $test_id Removed")
        Toast.makeText(context,"Removed",Toast.LENGTH_SHORT).show()
        val i = Intent(context, MyCart::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)

    }//end

    fun totalCost(): Double{
        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select SUM(test_cost) from cart",
        null)
        var total: Double = 0.0
        while (result.moveToNext()){
            //the cursor result returns a list of test_cost
            //Below result.getDouble(0) to retrieve the value from the first column of the current row
            total += result.getDouble(0)
        }//end
        return  total
    }//end
    //https://github.com/modcomlearning/MediLabApp
    //get all items from the cart
    fun getAllItems(): ArrayList<LabTests>{
        val db = this.writableDatabase
        val items = ArrayList<LabTests>()
        val result:Cursor = db.rawQuery("select * from cart ", null)
        //Lets add all rows into the items arraylist
        while (result.moveToNext())
        {
            val model = LabTests()
            model.test_id = result.getString(0) //Assume one row, test_id
            model.test_name = result.getString(1) //Assume one row, test_name
            model.test_cost = result.getString(2) //Assume one row, test_cost
            model.lab_id = result.getString(3) //Assume one row, lab_id
            model.test_description = result.getString(4) //Assume one row, test_description
            items.add(model) //add model to array list
        }//end while
        return items
    }//End function

}

