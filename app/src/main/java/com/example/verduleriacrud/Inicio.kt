package com.example.verduleriacrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_circulos: Button = findViewById(R.id.btn_circulos)
        btn_circulos.setOnClickListener{
            val intent: Intent = Intent(this, Login:: class.java)
            startActivity(intent)
        }
    }

}