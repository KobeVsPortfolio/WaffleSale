package com.example.wafflesale.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.wafflesale.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.displayName).title = auth.currentUser?.displayName.toString()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.home) {
            Toast.makeText(this, "You're already on the homepage you silly goose!", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.account) {
            Toast.makeText(this, "${auth.currentUser?.displayName.toString()}", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.logout) {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)

    }


    fun goToOrder(v : View){
        val intent = Intent(this, ViewOrdersActivity::class.java)
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
