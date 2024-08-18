package com.example.repeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager


class repeat : Fragment() {
    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repeat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var popup = view.findViewById<ImageView>(R.id.menu_option)
        var data = mutableListOf<pagerModel>().apply {

            add(pagerModel("gojo1"))
            add(pagerModel("gojo2"))
        }
        var pager = view.findViewById<ViewPager>(R.id.viewpager)
        pagerAdapter = pagerAdapter(requireContext(), data)
        pager.adapter = pagerAdapter

        popup.setOnClickListener {
            optionMenu(popup)
        }
    }

    fun optionMenu(view: View) {
        var popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            item ->
            when(item.itemId){
                R.id.option1 -> {
                    Toast.makeText(requireContext(), "option1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.option2 -> {
                    Toast.makeText(requireContext(), "option2", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.option3 -> {
                    Toast.makeText(requireContext(), "option3", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false

            }
        }

        popupMenu.setOnDismissListener {
            popupMenu.dismiss()
        }

        popupMenu.show()
    }
}