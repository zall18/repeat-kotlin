package com.example.repeat

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.Page
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream


class home : Fragment() {

    lateinit var homeAdapter: homeAdapter
    lateinit var file: File
    lateinit var f: File
    lateinit var tipe: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = mutableListOf<homeModel>()
        var listview = view.findViewById<ListView>(R.id.listview)
        var search = view.findViewById<SearchView>(R.id.search)
        tipe = "pdf"


        for (i in 1 until 6){
            var data1 = homeModel(i.toString(), "astronomi", "luar angkasa")

            data.add(data1)
        }

        homeAdapter = homeAdapter(requireContext(), data)
        listview.adapter = homeAdapter

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeAdapter.filter.filter(newText)
                return true
            }

        })

        var save = view.findViewById<AppCompatButton>(R.id.save_button)
        save.setOnClickListener {

            var dialog = BottomSheetDialog(requireContext())
            var view2 = layoutInflater.inflate(R.layout.optionsave, null)

            var content = view.findViewById<LinearLayout>(R.id.content)
            var image = view2.findViewById<AppCompatButton>(R.id.image_button)
            var pdf = view2.findViewById<AppCompatButton>(R.id.pdf_button)

            var bitmap = Bitmap.createBitmap(content.width, content.height, Bitmap.Config.ARGB_8888)
            var canvas = Canvas(bitmap)
            content.draw(canvas)

            image.setOnClickListener {
                if(android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    file = File(android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "repeat")
                    if(!file.exists())
                    {
                        file.mkdir()
                    }

                    f = File(file.absoluteFile.toString() + "/repeat2.png")
                    var fileOutputStream = FileOutputStream(f)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, fileOutputStream)
                    tipe = "png"
                    Toast.makeText(requireContext(), "image berhasil disimpan", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }

            pdf.setOnClickListener {
                var pdfDocument = PdfDocument()
                var pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
                var page = pdfDocument.startPage(pageInfo)

                var canvas2 = page.canvas
                canvas2.drawBitmap(bitmap, 0f, 0f, null)
                pdfDocument.finishPage(page)
                if(android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    file = File(android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "repeat")
                    if(!file.exists())
                    {
                        file.mkdir()
                    }

                    f = File(file.absoluteFile.toString() + "/repeat2.pdf")
                    var fileOutputStream = FileOutputStream(f)
                    pdfDocument.writeTo(fileOutputStream)
                    tipe = "pdf"
                    Toast.makeText(requireContext(), "pdf berhasil disimpan", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }

            dialog.setContentView(view2)
            dialog.show()

        }

        var share = view.findViewById<AppCompatButton>(R.id.share_button)
        share.setOnClickListener {
            if(tipe == "png")
            {
                var intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(f.toString()))
                intent.setType("image/png")
                startActivity(Intent.createChooser(intent, "Share image via.."))
            }else{
                var uri = FileProvider.getUriForFile(requireContext(), "repeat", f.absoluteFile)
                var intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri.toString()))
                intent.setType("application/pdf")
                startActivity(Intent.createChooser(intent, "Share file via.."))
            }
        }

    }
}