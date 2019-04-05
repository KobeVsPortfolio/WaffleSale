package com.example.wafflesale

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.wafflesale.data.MemberListAdapter
import com.example.wafflesale.data.MyDBAdapter
import com.example.wafflesale.domain.Member
import kotlinx.android.synthetic.main.activity_view_members.*

class ViewMembersActivity : AppCompatActivity() {

    private var members: ArrayList<Member>? = ArrayList()
    private var adapter: MemberListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var myDBAdapter: MyDBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_members)

        initializeDatabase()
        members = myDBAdapter?.findAllMembers()

        adapter = MemberListAdapter(members, this)
        layoutManager = LinearLayoutManager(this)

        rv.layoutManager = layoutManager
        rv.adapter = adapter
    }

    private fun initializeDatabase(){
        myDBAdapter = MyDBAdapter(this@ViewMembersActivity)
        myDBAdapter?.open()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menuplus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == R.id.plus) {
            val intent = Intent(this, NewMemberActivity::class.java)
            startActivity(intent)
        }
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