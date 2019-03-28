package com.example.wafflesale

import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.wafflesale.data.MemberListAdapter
import com.example.wafflesale.domain.Address
import com.example.wafflesale.domain.Club
import com.example.wafflesale.domain.Member
import kotlinx.android.synthetic.main.activity_third.*


class ThirdActivity : AppCompatActivity() {

    var firstName = ""
    var lastName = ""
    var address: Address = Address("Bxl", "Straat", 1, "2000")
    var club: Club = Club(1, "Scouts", address)
    private var members: ArrayList<Member> = arrayListOf()
    private var adapter: MemberListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    var currentImage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        if (savedInstanceState != null && savedInstanceState.getSerializable("rView") != null) {
            members = savedInstanceState.getSerializable("rView") as ArrayList<Member>
        }
        members = ArrayList()
        adapter = MemberListAdapter(members, this)
        layoutManager = LinearLayoutManager(this)

        lv.layoutManager = layoutManager
        lv.adapter = adapter

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

    fun addMember(v: View) {

        firstName = inputFirstName.text.toString()
        lastName = inputLastName.text.toString()
        var member = Member(firstName, lastName, address)
        member.imageUrl = currentImage
        members.add(member)
        club.addMember(member)

        adapter!!.notifyDataSetChanged()

        inputFirstName.text = null
        inputLastName.text = null
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putSerializable("rView", members)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        members = savedInstanceState?.getSerializable("rView") as ArrayList<Member>
    }
}


