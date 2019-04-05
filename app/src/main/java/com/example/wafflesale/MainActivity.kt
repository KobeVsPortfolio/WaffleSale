package com.example.wafflesale

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.home) {
            Toast.makeText(this, "You're already on the homepage you silly goose!", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.account) {
            Toast.makeText(this, "This hasn't been made yet.", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.logout) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)

    }


    fun goToOrder(v : View){
        val intent = Intent(this, NewOrderActivity::class.java)
        startActivity(intent)
    }

    fun goToMember(v : View){
        val intent = Intent(this, ViewMembersActivity::class.java)
        startActivity(intent)
    }

    fun newClient(v : View){
        val intent = Intent(this, NewClientActivity::class.java)
        startActivity(intent)
    }
}
