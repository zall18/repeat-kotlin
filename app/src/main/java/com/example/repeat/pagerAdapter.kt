package com.example.repeat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.imageview.ShapeableImageView

class pagerAdapter(var context: Context, var data: MutableList<pagerModel>): PagerAdapter() {
    var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view =inflater.inflate(R.layout.pageritem, null)

        var image = view.findViewById<ShapeableImageView>(R.id.image_pager)
        image.setImageResource(R.drawable.gojo1)

        container.addView(view)
        return view;
    }
}