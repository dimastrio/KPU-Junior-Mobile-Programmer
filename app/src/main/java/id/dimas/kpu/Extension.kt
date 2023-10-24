package id.dimas.kpu

import android.graphics.Bitmap
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

object Extension {

    fun ShapeableImageView.loadImage(uri: Uri) {
        Glide.with(this.context)
            .load(uri)
            .into(this)
    }

    fun ShapeableImageView.loadImage(bitmap: Bitmap) {
        Glide.with(this.context)
            .load(bitmap)
            .into(this)
    }
}