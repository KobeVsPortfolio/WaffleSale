package com.example.wafflesale

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.home) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.account) {
            Toast.makeText(this, "This hasn't been made yet.", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.logout) {
            Toast.makeText(this, "This hasn't been made yet.", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun backButton(v: View) {
        val intent = Intent(this, ViewMembersActivity::class.java)
        startActivity(intent)
    }
}
