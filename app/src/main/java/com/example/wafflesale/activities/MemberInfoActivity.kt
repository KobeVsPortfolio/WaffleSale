package com.example.wafflesale.activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.wafflesale.R
import com.example.wafflesale.data.MyDBAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_member_info.*

class MemberInfoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var myDBAdapter: MyDBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_info)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        initializeDatabase()

        var id : Int = intent.getIntExtra("id", 0)
        var firstName = intent.getStringExtra("firstName")
        var lastName = intent.getStringExtra("lastName")
        var email = intent.getStringExtra("email")
        imageInfo.setImageResource(intent.getIntExtra("image", 0))
        fullNameInfo.text = "$firstName $lastName"

        deleteButton.setOnClickListener {
                val deleteAlert = AlertDialog.Builder(this)
                deleteAlert.setTitle("Delete ${firstName} ${lastName}")
                deleteAlert.setMessage(
                    "Are you sure you want to delete this member?"
                )

                deleteAlert.setPositiveButton("Delete") { dialogInterface: DialogInterface, i: Int ->
                    myDBAdapter?.deleteMemberById(id)
                    db.collection("members").document(email)
                        .delete()
                    val intent = Intent(this, ViewMembersActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "$firstName $lastName has been deleted.", Toast.LENGTH_LONG).show()
                }
                deleteAlert.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                }

                val alertDialog: AlertDialog = deleteAlert.create()
                alertDialog.show()
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorWarning))
                val textView = alertDialog.findViewById<TextView>(android.R.id.message)
                val typeface = Typeface.createFromAsset(assets, "baloo_regular.ttf")
                textView?.typeface = typeface
                textView?.setTextColor(getColor(R.color.colorPrimaryDark))
                alertDialog.window?.setBackgroundDrawableResource(R.color.colorPrimary)
        }
    }

    private fun initializeDatabase(){
        myDBAdapter = MyDBAdapter(this@MemberInfoActivity)
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.logout) {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    fun backButton(v: View) {
        val intent = Intent(this, ViewMembersActivity::class.java)
        startActivity(intent)
    }
}
