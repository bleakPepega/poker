package com.bihnerdranch.example.poker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlin.system.*

class MainActivity : AppCompatActivity() {
    lateinit var quantityText: EditText
    lateinit var startButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quantityText = findViewById(R.id.countPlayers)
        startButton = findViewById(R.id.start)
        startButton.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            intent.putExtra("key", quantityText.text.toString())
            startActivity(intent)
        }
    }
    


}