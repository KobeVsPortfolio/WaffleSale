package com.example.wafflesale

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_member_info.*

class MemberInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_info)
        var firstName = intent.getStringExtra("firstName")
        var lastName = intent.getStringExtra("lastName")
        imageInfo.setImageResource(intent.getIntExtra("image", 0))
        fullNameInfo.text = "$firstName $lastName"
    }

    fun backButton(v: View) {
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }
}
