package com.example.wafflesale.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.wafflesale.R
import com.example.wafflesale.data.MyDBAdapter
import com.example.wafflesale.data.OrderListAdapter
import com.example.wafflesale.domain.Member
import com.example.wafflesale.domain.Order
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_view_orders.*
import kotlinx.android.synthetic.main.orderlist_layout.*

class ViewOrdersActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var orders: ArrayList<Order?>? = arrayListOf()
    private var adapter: OrderListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var myDBAdapter: MyDBAdapter? = null
    var currentMember = Member()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_orders)
        auth = FirebaseAuth.getInstance()
        initializeDatabase()

        if(myDBAdapter?.findMemberByEmail(auth.currentUser?.email!!) != null) {
            currentMember = myDBAdapter?.findMemberByEmail(auth.currentUser?.email!!)!!
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Only members can view orders.", Toast.LENGTH_LONG).show()
        }
        if(currentMember.id != null) {
            orders = myDBAdapter?.findAllOrdersByMember(currentMember.email!!)!!
            orders?.forEach { o -> o!!.orderList = myDBAdapter?.findAllOrderLinesByOrder(o.id!!)!! }
        }

        adapter = OrderListAdapter(orders, this)
        layoutManager = LinearLayoutManager(this)

        rv_orders.layoutManager = layoutManager
        rv_orders.adapter = adapter
    }

    private fun initializeDatabase(){
        myDBAdapter = MyDBAdapter(this@ViewOrdersActivity)
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
        menuInflater.inflate(R.menu.menuplus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == R.id.plus) {
            val intent = Intent(this, NewOrderActivity::class.java)
            startActivity(intent)
        }
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
