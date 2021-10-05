package com.example.gestiondetelephone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: MutableList<Tel>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.nomTel.text = currentitem.nomTel
        holder.sysEx.text = currentitem.sysEx
        holder.cap.text = currentitem.cap
        holder.ram.text = currentitem.ram
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){


        val nomTel : TextView = itemView.findViewById(R.id.nomTel)
        val sysEx : TextView = itemView.findViewById(R.id.sysEx)
        val cap: TextView = itemView.findViewById(R.id.cap)
        val ram: TextView = itemView.findViewById(R.id.ram)


    }

}