package com.example.wafflesale.activities

import android.content.Intent
import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.res.ResourcesCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.wafflesale.R
import com.example.wafflesale.data.MyDBAdapter
import com.example.wafflesale.domain.Member
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var myDBAdapter: MyDBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        initializeDatabase()

        if(myDBAdapter?.findMemberByEmail(auth.currentUser?.email!!)?.id == null) {
            db.collection("members").document(auth.currentUser?.email!!).get().addOnSuccessListener { document ->
                var newMember = document.toObject(Member::class.java)
                    myDBAdapter?.addMember(
                        newMember?.firstName,
                        newMember?.lastName,
                        R.drawable.admin,
                        auth.currentUser?.email!!
                    )
            }
        }
        //If you want to delete certain things in the database
        /*
        myDBAdapter?.deleteClients()
        myDBAdapter?.deleteOrders()
        myDBAdapter?.deleteOrderLines()*/
    }

    private fun initializeDatabase(){
        myDBAdapter = MyDBAdapter(this@MainActivity)
        myDBAdapter?.open()
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
