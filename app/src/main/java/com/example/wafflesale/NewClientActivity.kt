package com.example.wafflesale

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.wafflesale.data.MyDBAdapter
import kotlinx.android.synthetic.main.activity_new_client.*

class NewClientActivity : AppCompatActivity() {

    private var myDBAdapter: MyDBAdapter? = null

    var firstName = ""
    var lastName = ""
    var street = ""
    var city = ""
    var postCode = ""
    var number = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        initializeDatabase()
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

        if (!firstName.isBlank() && !lastName.isBlank() && !street.isBlank() && !city.isBlank() && !postCode.isBlank() && !number.isBlank()) {

            myDBAdapter?.addClient(firstName, lastName, street, number, city, postCode)

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
}
