package com.example.wafflesale.data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.wafflesale.MemberInfoActivity
import com.example.wafflesale.R
import com.example.wafflesale.domain.Member
import kotlinx.android.synthetic.main.card_layout.view.*

class MemberListAdapter(private val list:MutableList<Member>, private val context:Context)
    : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    inner class ViewHolder (itemView : View):RecyclerView.ViewHolder(itemView){
        fun bindItem(member : Member){
            var firstName:TextView = itemView.findViewById(R.id.tv_firstName) as TextView
            var lastName: TextView = itemView.findViewById(R.id.tv_lastName) as TextView
            var image : ImageView? = itemView.findViewById(R.id.cardImage) as ImageView
            firstName.text = member.firstName
            lastName.text = member.lastName
            image?.setImageResource(member.imageUrl)

            itemView.setOnClickListener{
                val intent = Intent(context, MemberInfoActivity::class.java)
                intent.putExtra("firstName", member.firstName)
                intent.putExtra("lastName", member.lastName)
                intent.putExtra("image", member.imageUrl)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(parent: MemberListAdapter.ViewHolder, position: Int) {
        parent.bindItem(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MemberListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

}