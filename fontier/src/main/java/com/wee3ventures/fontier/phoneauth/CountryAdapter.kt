package com.wee3ventures.fontier.phoneauth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.wee3ventures.fontier.FontView
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.utils.Utility

class CountryAdapter : BaseAdapter() {
    private var countryData : MutableList<Registration.CountryCode> = mutableListOf()
    fun addData(list : List<Registration.CountryCode>){
        this.countryData = list as MutableList<Registration.CountryCode>
        this.notifyDataSetChanged()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.layout_country_spinner_item,parent,false)
        view.findViewById<FontView>(R.id.countrylabel).text = Utility.htmlValue(0,0)
        return view
    }

    override fun getItem(position: Int): Any {
        return countryData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return countryData.size
    }
}