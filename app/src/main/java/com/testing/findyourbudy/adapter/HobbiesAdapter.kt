package com.testing.findyourbudy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testing.findyourbudy.R

class HobbiesAdapter(var context: Context,var hobyList:ArrayList<String>): RecyclerView.Adapter<HobbiesAdapter.HobyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobyViewHolder {
       var view = LayoutInflater.from(context).inflate(R.layout.hoby_item,parent,false)
        return HobyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HobyViewHolder, position: Int) {

        holder.item.text = hobyList.get(position)
    }

    override fun getItemCount(): Int {
       return hobyList.size
    }

    inner class HobyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var item:TextView = itemView.findViewById(R.id.itemText);

    }
}