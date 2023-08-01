package com.eduuh.medilabsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class screen1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen1)

        //Find Views by ID
        val skip1 = findViewById<MaterialTextView>(R.id.skip1)
        skip1.setOnClickListener {
            startActivity(Intent(applicationContext, MemberProfile::class.java))
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(applicationContext, screen2::class.java))
        }
    }
}