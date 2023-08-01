package com.eduuh.medilabsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class screen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen2)

        val skip2 = findViewById<MaterialTextView>(R.id.skip2)
        skip2.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }//end

        val fab2 = findViewById<FloatingActionButton>(R.id.fab2)
        fab2.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }//end

        //Android OptionsMenu


    }
}