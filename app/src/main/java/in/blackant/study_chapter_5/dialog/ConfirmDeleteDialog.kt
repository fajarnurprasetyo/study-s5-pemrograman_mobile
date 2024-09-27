package `in`.blackant.study_chapter_5.dialog

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import `in`.blackant.study_chapter_5.R

class ConfirmDeleteDialog(context: Context) {
    private var callback: (() -> Unit)? = null
    private val dialog = MaterialAlertDialogBuilder(context)
        .setNegativeButton(R.string.cancel, null)
        .setPositiveButton(R.string.delete) { _, _ -> callback?.invoke() }
        .create()

    fun show(message: String, callback: () -> Unit) {
        this.callback = callback
        dialog.setMessage(message)
        dialog.show()
    }
}