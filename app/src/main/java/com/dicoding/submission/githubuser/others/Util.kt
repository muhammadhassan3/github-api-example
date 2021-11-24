package com.dicoding.submission.githubuser.others

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dicoding.submission.githubuser.R

class Util {
    companion object {
        fun getImageId(context: Context, imagePath: String): Int {
            var image = imagePath.replace("res/drawable/", "")
            image = image.replace(".png", "")
            return context.resources.getIdentifier(image, "drawable", context.packageName)
        }

        fun setupAlertDialog(context: Context, title: String, msg: String?) {
            val dialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setNeutralButton(
                    context.resources.getString(R.string.neutral_button_text)
                ) { p0, p1 -> p0.cancel() }
                .create()
            dialog.show()
        }
    }
}