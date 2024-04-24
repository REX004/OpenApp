package com.example.a01_wskpolice.View.recyclerview.session1

import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_wskpolice.R
import java.io.IOException

class ImageAdapter(private val context: Context, private val folderName: String) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val assetManager: AssetManager = context.assets
    private val images: Array<String> = try {
        assetManager.list(folderName) ?: arrayOf()
    } catch (e: IOException) {
        arrayOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = images[position]
        try {
            val inputStream = assetManager.open("$folderName/$imagePath")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            holder.imageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.photorobotIMG)
    }
}
