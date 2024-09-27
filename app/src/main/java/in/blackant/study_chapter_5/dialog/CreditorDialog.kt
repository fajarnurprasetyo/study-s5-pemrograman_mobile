package `in`.blackant.study_chapter_5.dialog

import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.activity.CreditorActivity
import `in`.blackant.study_chapter_5.databinding.DialogCreditorBinding
import `in`.blackant.study_chapter_5.databinding.DialogMotorcycleBinding
import `in`.blackant.study_chapter_5.model.Creditors
import `in`.blackant.study_chapter_5.model.Motorcycles

class CreditorDialog(private val activity: CreditorActivity) {
    interface Listener {
        fun onAdd(data: Creditors.Creditor)
        fun onEdit(oldData: Creditors.Creditor, data: Creditors.Creditor)
    }

    private val binding = DialogCreditorBinding.inflate(activity.layoutInflater)
    private val dialog = MaterialAlertDialogBuilder(activity)
        .setView(binding.root)
        .setCancelable(false)
        .setNegativeButton(R.string.cancel, null)
        .setPositiveButton(R.string.save, null)
        .create()
    private var id = 0

    private val data
        get() = Creditors.Creditor(
            id,
            binding.name.text.toString(),
            binding.job.text.toString(),
            binding.phone.text.toString(),
            binding.address.text.toString(),
        )

    fun add() {
        id = 0
        binding.name.text = null
        binding.job.text = null
        binding.phone.text = null
        binding.address.text = null

        dialog.setTitle(R.string.add_creditor)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { _ ->
            dialog.dismiss()
            activity.onAdd(data)
        }
    }

    fun edit(creditor: Creditors.Creditor) {
        id = creditor.id
        binding.name.setText(creditor.name)
        binding.job.setText(creditor.job)
        binding.phone.setText(creditor.phone)
        binding.address.setText(creditor.address)

        dialog.setTitle(R.string.edit_creditor)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { _ ->
            dialog.dismiss()
            activity.onEdit(creditor, data)
        }
    }
}