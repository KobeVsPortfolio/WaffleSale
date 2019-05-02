package com.example.wafflesale.activities

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.wafflesale.R
import com.example.wafflesale.data.MyDBAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_client.*

class NewClientActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var myDBAdapter: MyDBAdapter? = null

    var firstName = ""
    var lastName = ""
    var street = ""
    var city = ""
    var postCode = ""
    var number = ""
    var phoneNumber = ""

    val PICK = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        auth = FirebaseAuth.getInstance()
        initializeDatabase()

        pickContact.setOnClickListener { view ->
            var intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK && resultCode == Activity.RESULT_OK) {
            var contact: Uri = data!!.data
            var cursor: Cursor = contentResolver.query(contact, null, null, null)
            cursor.moveToFirst()
            var phone = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            var name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

            var phoneS = cursor.getString(phone)
            var nameS = cursor.getString(name)

            val split = nameS.split(" ", limit = 2)
            var first = split[0]
            var last = split[1]

            inputFirstName.setText(first)
            inputLastName.setText(last)
            inputPhoneNumber.setText(phoneS)
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
        myDBAdapter = MyDBAdapter(this@NewClientActivity)
        myDBAdapter?.open()
    }

    fun addClient(v : View){
        firstName = inputFirstName.text.toString()
        lastName = inputLastName.text.toString()
        street = inputStreet.text.toString()
        city = inputCity.text.toString()
        postCode = inputPostCode.text.toString()
        number = inputNumber.text.toString()
        phoneNumber = inputPhoneNumber.text.toString()

        if (!firstName.isBlank() && !lastName.isBlank() && !street.isBlank() && !city.isBlank() && !postCode.isBlank() && !number.isBlank() && !phoneNumber.isBlank()) {

            myDBAdapter?.addClient(firstName, lastName, street, number, city, postCode, phoneNumber)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "$firstName $lastName was added successfully.", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
        }
    }

    fun back(v : View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun getFromContacts(v : View){
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
        if (id == R.id.account) {
            Toast.makeText(this, "This hasn't been made yet.", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.logout) {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
