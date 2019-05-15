package com.example.wafflesale.data

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.res.ResourcesCompat.getColor
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wafflesale.R
import com.example.wafflesale.activities.ViewOrdersActivity
import com.example.wafflesale.domain.Client
import com.example.wafflesale.domain.Member
import com.example.wafflesale.domain.Order
import com.example.wafflesale.domain.OrderLine
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderListAdapter(private val list:ArrayList<Order?>?, private val context:Context)
    : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    inner class ViewHolder (itemView : View):RecyclerView.ViewHolder(itemView){

        private var myDBAdapter: MyDBAdapter? = null
        private lateinit var auth: FirebaseAuth
        private lateinit var db: FirebaseFirestore

        fun bindItem(order : Order) {
            auth = FirebaseAuth.getInstance()
            db = FirebaseFirestore.getInstance()
            initializeDatabase()
            var clientName: TextView = itemView.findViewById(R.id.tv_clientName) as TextView
            var totalPrice: TextView = itemView.findViewById(R.id.tv_totalPrice) as TextView
            var deleteButton : FloatingActionButton = itemView.findViewById(R.id.deleteOrder) as FloatingActionButton
            var client = myDBAdapter?.findClientByPhone(order.clientPhone!!)!!
            clientName.text = "${client.firstName} ${client.lastName}"
            totalPrice.text = "€" + order.getTotalOrderPrice().toString()

            deleteButton.setOnClickListener {
                val deleteAlert = AlertDialog.Builder(context)
                deleteAlert.setTitle("Delete order for ${client.firstName} ${client.lastName}")
                deleteAlert.setMessage(
                    "Are you sure you want to delete this order?"
                )

                deleteAlert.setPositiveButton("Delete") { dialogInterface: DialogInterface, i: Int ->
                    var currentMember : Member = myDBAdapter?.findMemberByEmail(auth.currentUser?.email!!)!!
                    db.collection("members/${currentMember.email}/orders").document(order.id.toString())
                        .delete()
                    db.collection("clients/${client.phoneNumber}/orders").document(order.id.toString())
                        .delete()
                    myDBAdapter?.deleteOrderById(order.id!!)
                    val intent = Intent(context, ViewOrdersActivity::class.java)
                    context.startActivity(intent)
                }
                deleteAlert.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                }

                val alertDialog: AlertDialog = deleteAlert.create()
                alertDialog.show()
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.colorWarning))
                val textView = alertDialog.findViewById<TextView>(android.R.id.message)
                val typeface = Typeface.createFromAsset(context.assets, "baloo_regular.ttf")
                textView?.typeface = typeface
                textView?.setTextColor(context.getColor(R.color.colorPrimaryDark))
                alertDialog.window?.setBackgroundDrawableResource(R.color.colorPrimary)
            }

            itemView.setOnClickListener {
                val orderAlert = AlertDialog.Builder(context)
                var total: Int = 0
                var orderString = ""
                var orderList : ArrayList<OrderLine> = myDBAdapter?.findAllOrderLinesByOrder(order.id!!)!!
                orderList.forEach { o ->
                    if (o.amount > 0) {
                        orderString += "\n"
                        orderString += o.amount.toString()
                        orderString += " "
                        orderString += o.product?.name.toString()
                        orderString += "'s for €"
                        orderString += (o.product?.price!! * o.amount).toString()
                    }
                }
                order.orderList.forEach { o -> total += (o.product?.price!! * o.amount).toInt() }
                orderAlert.setTitle("Order for ${client.firstName} ${client.lastName}: ")
                orderAlert.setMessage(
                    orderString + "\nTotal: €${total}")
                orderAlert.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                }

                val alertDialog: AlertDialog = orderAlert.create()
                alertDialog.show()

                val textView = alertDialog.findViewById<TextView>(android.R.id.message)
                val typeface = Typeface.createFromAsset(context.assets, "baloo_regular.ttf")
                textView?.typeface = typeface
                textView?.setTextColor(context.getColor(R.color.colorPrimaryDark))
                alertDialog.window?.setBackgroundDrawableResource(R.color.colorPrimary)
            }
        }


        private fun initializeDatabase(){
            myDBAdapter = MyDBAdapter(context)
            myDBAdapter?.open()
        }
    }

    override fun getItemCount(): Int {
        if (list != null) {
            return list.size
        }
        return 0
    }

    override fun onBindViewHolder(parent: OrderListAdapter.ViewHolder, position: Int) {
        if (list != null) {
            parent.bindItem(list.get(position)!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): OrderListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.orderlist_layout, parent, false)
        return ViewHolder(view)
    }

}