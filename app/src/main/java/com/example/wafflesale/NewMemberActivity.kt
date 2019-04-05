package com.example.wafflesale

import android.content.Intent
import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.wafflesale.data.MyDBAdapter
import kotlinx.android.synthetic.main.activity_new_member.*


class NewMemberActivity : AppCompatActivity() {

    var firstName = ""
    var lastName = ""
    var currentImage: Int = 0
    private var myDBAdapter: MyDBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_member)

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

    private fun initializeDatabase(){
        myDBAdapter = MyDBAdapter(this@NewMemberActivity)
        myDBAdapter?.open()
    }

    fun addMember(v: View) {
            firstName = inputFirstName.text.toString()
            lastName = inputLastName.text.toString()

        if (!firstName.isBlank() && !lastName.isBlank()) {
            myDBAdapter?.addMember(firstName, lastName, currentImage)

            Toast.makeText(this, "$firstName $lastName was added successfully.", Toast.LENGTH_SHORT).show()

            inputFirstName.text = null
            inputLastName.text = null

        }else{
            Toast.makeText(this, "Please fill in a first name and last name.", Toast.LENGTH_SHORT).show()
        }
    }
    fun back(v: View) {
        val intent = Intent(this, ViewMembersActivity::class.java)
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


