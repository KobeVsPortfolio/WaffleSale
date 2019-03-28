package com.example.wafflesale

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToOrder(v : View){
        val intent = Intent(this, SecondaryActivity::class.java)
        startActivity(intent)
    }

    fun goToMember(v : View){
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }

    fun login(v : View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
