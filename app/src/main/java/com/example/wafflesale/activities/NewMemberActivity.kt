package com.example.wafflesale.activities

import android.content.Intent
import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.wafflesale.R
import com.example.wafflesale.data.MyDBAdapter
import com.example.wafflesale.domain.Member
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_member.*


class NewMemberActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var firstName = ""
    var lastName = ""
    var currentImage: Int = 0
    var email = ""
    private var myDBAdapter: MyDBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_member)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        initializeDatabase()

        var stringList: Array<String> = resources.getStringArray(R.array.avatars)
        var images: TypedArray = resources.obtainTypedArray(R.array.avatar_images)

        var spinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, stringList)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)

        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentImage = images.getResourceId(spinner.selectedItemPosition, -1)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeDatabase(){
        myDBAdapter = MyDBAdapter(this@NewMemberActivity)
        myDBAdapter?.open()
    }

    fun addMember(v: View) {
            firstName = inputFirstName.text.toString()
            lastName = inputLastName.text.toString()
            email = inputEmail.text.toString().toLowerCase()

        if (!firstName.isBlank() && !lastName.isBlank() && !email.isBlank()) {
            db.collection("members").document(email).get().addOnSuccessListener { document ->
                if (!document.exists()) {
                    myDBAdapter?.addMember(firstName, lastName, currentImage, email)

                    Toast.makeText(
                        this,
                        "$firstName $lastName with email: ${email} was added successfully.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val member = HashMap<String, Any>()
                    member["firstName"] = firstName
                    member["lastName"] = lastName
                    member["email"] = email
                    db.collection("members").document(email)
                        .set(member)
                        .addOnSuccessListener { documentReference ->
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "$firstName $lastName was not added to FireStore.", Toast.LENGTH_SHORT)
                                .show()
                        }

                    val intent = Intent(this, ViewMembersActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "A member with $email already exists.", Toast.LENGTH_SHORT).show()
                }
            }

        }else{
            Toast.makeText(this, "Please fill in your full name and email.", Toast.LENGTH_SHORT).show()
        }
    }
    fun back(v: View) {
        val intent = Intent(this, ViewMembersActivity::class.java)
        startActivity(intent)
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
}


