package com.example.celebapi_v2

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.celebapi_v2.databinding.ItemBinding
import java.util.*
import kotlin.collections.ArrayList


class RVAdapter(private val context: Context, private var list: ArrayList<Data>): RecyclerView.Adapter<RVAdapter.ViewHolder>(), Filterable{


    //private val mainList = list
    private val searchList = ArrayList<Data>(list)


    class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvTaboo1.text = item.taboo1
        holder.binding.tvTaboo2.text = item.taboo2
        holder.binding.tvTaboo3.text = item.taboo3
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun update(list: ArrayList<Data>){
        this.list = list
        notifyDataSetChanged()
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList = ArrayList<Data>()

                if (constraint.isBlank() or constraint.isEmpty()) {
                    filteredList.addAll(searchList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                    searchList.forEach {
                        if (it.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(it)
                        }
                    }
                }

                val result = FilterResults()
                result.values = filteredList

                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list.clear()
                list.addAll(results!!.values as List<Data>)
                notifyDataSetChanged()
            }
        }
    }

}