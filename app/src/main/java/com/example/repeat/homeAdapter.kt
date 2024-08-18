package com.example.repeat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class homeAdapter(var context: Context, var data: MutableList<homeModel>):BaseAdapter(), Filterable {

    var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var filterData: MutableList<homeModel> = data

    override fun getCount(): Int {
        return filterData.size
    }

    override fun getItem(position: Int): Any {
        return filterData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var item = convertView ?: inflater.inflate(R.layout.homeitem, null, false)

        var id = item.findViewById<TextView>(R.id.id_home)
        var nama = item.findViewById<TextView>(R.id.nama_home)
        var desk = item.findViewById<TextView>(R.id.desk_home)

        var data = getItem(position) as homeModel

        id.text = data.id
        nama.text = data.nama
        desk.text = data.desk

        return item;

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filtering = mutableListOf<homeModel>()

                if(constraint.isNullOrBlank())
                {
                    filtering.addAll(data)
                }else{

                    var filterPattern = constraint.toString().lowercase().trim()

                    for (item in data)
                    {
                        if (item.id.toString().lowercase().trim().equals(filterPattern)){
                            filtering.add(homeModel(item.id, item.nama, item.desk))
                        }
                    }

                }

                var result = FilterResults()
                result.values = filtering
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterData = results?.values as ArrayList<homeModel>
                notifyDataSetChanged()
            }

        }
    }


}