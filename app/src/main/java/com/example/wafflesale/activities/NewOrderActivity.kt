package com.example.wafflesale.activities

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.graphics.Typeface
import android.net.Uri
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.wafflesale.R
import com.example.wafflesale.data.MyDBAdapter
import com.example.wafflesale.domain.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_order.*


class NewOrderActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    var totalChocoPrice: Float? = 0f
    var totalVanillePrice: Float? = 0f
    var totalCarrePrice: Float? = 0f
    var totalMixPrice: Float? = 0f
    var totalPrice: Float? = 0f

    var currentClient : Client? = null
    var currentOrder : Order? = null

    private var myDBAdapter: MyDBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order)
        auth = FirebaseAuth.getInstance()
        initializeDatabase()

        var clientList: ArrayList<Client> = myDBAdapter?.findAllClients()!!
        var stringList: ArrayList<String> = arrayListOf("Please choose a client")
            clientList.forEach { c -> stringList.add("${c.firstName} ${c.lastName}")}

        var spinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, stringList)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)

        searchableSpinner.adapter = spinnerAdapter
        searchableSpinner.setTitle("Clients")
        searchableSpinner.setPositiveButton("Back")
        searchableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(searchableSpinner.selectedItemPosition != 0) {
                    currentClient = clientList.get(searchableSpinner.selectedItemPosition - 1)
                }else{
                    currentClient = null
                    Toast.makeText(this@NewOrderActivity, "Please choose a client.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        cb_choco.setOnClickListener {
            if(cb_choco.isChecked){
                numberChoco.maxValue = 10
            }else{
                numberChoco.maxValue = 0
                totalPrice = totalPrice?.minus(totalChocoPrice!!)
                total.text = totalPrice.toString()
                totalChocoPrice = 0f
                totalChoco.text = null
            }
        }

        cb_vanille.setOnClickListener {
            if(cb_vanille.isChecked){
                numberVanille.maxValue = 10
            }else{
                numberVanille.maxValue = 0
                totalPrice = totalPrice?.minus(totalVanillePrice!!)
                total.text = totalPrice.toString()
                totalVanillePrice = 0f
                totalVanille.text = null
            }
        }
        cb_carre.setOnClickListener {
            if(cb_carre.isChecked){
                numberCarre.maxValue = 10
            }else{
                numberCarre.maxValue = 0
                totalPrice = totalPrice?.minus(totalCarrePrice!!)
                total.text = totalPrice.toString()
                totalCarrePrice = 0f
                totalCarre.text = null
            }
        }

        cb_mix.setOnClickListener {
            if(cb_mix.isChecked){
                numberMix.maxValue = 10
            }else{
                numberMix.maxValue = 0
                totalPrice = totalPrice?.minus(totalMixPrice!!)
                total.text = totalPrice.toString()
                totalMixPrice = 0f
                totalMix.text = null
            }
        }

        numberChoco.minValue = 0
        numberChoco.wrapSelectorWheel = true
        numberChoco.setOnValueChangedListener { _, oldVal, newVal ->
            totalChocoPrice = newVal * 6.00f
            totalChoco.text = totalChocoPrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 6.00f)
            totalPrice = totalPrice?.plus(totalChocoPrice!!)
            total.text = totalPrice.toString()
        }

        numberVanille.minValue = 0
        numberVanille.wrapSelectorWheel = true
        numberVanille.setOnValueChangedListener { _, oldVal, newVal ->
            totalVanillePrice = newVal * 6.00f
            totalVanille.text = totalVanillePrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 6.00f)
            totalPrice = totalPrice?.plus(totalVanillePrice!!)
            total.text = totalPrice.toString()
        }

        numberCarre.minValue = 0
        numberCarre.wrapSelectorWheel = true
        numberCarre.setOnValueChangedListener { _, oldVal, newVal ->
            totalCarrePrice = newVal * 6.00f
            totalCarre.text = totalCarrePrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 6.00f)
            totalPrice = totalPrice?.plus(totalCarrePrice!!)
            total.text = totalPrice.toString()
        }

        numberMix.minValue = 0
        numberMix.wrapSelectorWheel = true
        numberMix.setOnValueChangedListener { _, oldVal, newVal ->
            totalMixPrice = newVal * 7.00f
            totalMix.text = totalMixPrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 7.00f)
            totalPrice = totalPrice?.plus(totalMixPrice!!)
            total.text = totalPrice.toString()
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
        myDBAdapter = MyDBAdapter(this@NewOrderActivity)
        myDBAdapter?.open()
    }

    fun order(v: View){
        var currentMember : Member = myDBAdapter?.findMemberByEmail(auth.currentUser?.email!!)!!
        if(currentClient != null) {
            val orderAlert = AlertDialog.Builder(this)
            var chocoAmount :Int? = (totalChocoPrice?.div(6))?.toInt()
            var vanilleAmount: Int? = (totalVanillePrice?.div(6))?.toInt()
            var carreAmount : Int? = (totalCarrePrice?.div(6))?.toInt()
            var mixAmount : Int? = (totalMixPrice?.div(7))?.toInt()
            orderAlert.setTitle("Order for ${currentClient!!.firstName}: ")
            orderAlert.setMessage(
                        "\n${chocoAmount.toString()} Choco Waffles for €${totalChocoPrice?.toInt().toString()}" +
                        "\n${vanilleAmount.toString()} Vanille Waffles for €${totalVanillePrice?.toInt().toString()}" +
                        "\n${carreAmount.toString()} Carré Confitures for €${totalCarrePrice?.toInt().toString()}" +
                        "\n${mixAmount.toString()} Mix's for €${totalMixPrice?.toInt().toString()}" +
                        "\nTotal: €${totalPrice?.toInt().toString()}"
            )

            orderAlert.setPositiveButton("Confirm Order") { dialogInterface: DialogInterface, i: Int ->
                myDBAdapter?.addOrder(currentMember.id!!, currentClient!!.id!!)
                currentOrder = myDBAdapter?.findLastOrder()
                if(currentOrder?.memberId == currentMember.id && currentOrder?.clientId == currentClient!!.id) {
                    if (chocoAmount != null) {
                        myDBAdapter?.addOrderLine(currentOrder?.id!!, Product.CHOCO_WAFFLE, chocoAmount)
                    }
                    if (vanilleAmount != null) {
                        myDBAdapter?.addOrderLine(currentOrder?.id!!, Product.VANILLE_WAFFLE, vanilleAmount)
                    }
                    if (carreAmount != null) {
                        myDBAdapter?.addOrderLine(currentOrder?.id!!, Product.SQUAREJAM, carreAmount)
                    }
                    if (mixAmount != null) {
                        myDBAdapter?.addOrderLine(currentOrder?.id!!, Product.MIX, mixAmount)
                    }
                    Toast.makeText(applicationContext, "Thank you for ordering!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext, "Something went wrong while ordering.", Toast.LENGTH_LONG).show()
                }
            }
            orderAlert.setNegativeButton("Cancel Order") { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(applicationContext, "Order cancelled.", Toast.LENGTH_LONG).show()
            }

            val alertDialog: AlertDialog = orderAlert.create()
            alertDialog.show()

            val textView = alertDialog.findViewById<TextView>(android.R.id.message)
            val typeface = Typeface.createFromAsset(assets, "baloo_regular.ttf")
            textView?.typeface = typeface
            textView?.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            alertDialog.window?.setBackgroundDrawableResource(R.color.colorPrimary)
        }else{
            Toast.makeText(this@NewOrderActivity, "No client has been selected.", Toast.LENGTH_SHORT).show()
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
