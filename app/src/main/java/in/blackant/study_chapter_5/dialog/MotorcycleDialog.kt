package `in`.blackant.study_chapter_5.dialog

import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.activity.MotorcycleActivity
import `in`.blackant.study_chapter_5.databinding.DialogMotorcycleBinding
import `in`.blackant.study_chapter_5.model.Motorcycles

class MotorcycleDialog(private val activity: MotorcycleActivity) {
    interface Listener {
        fun onAdd(data: Motorcycles.Motorcycle)
        fun onEdit(oldData: Motorcycles.Motorcycle, data: Motorcycles.Motorcycle)
    }

    private val binding = DialogMotorcycleBinding.inflate(activity.layoutInflater)
    private val dialog = MaterialAlertDialogBuilder(activity)
        .setView(binding.root)
        .setCancelable(false)
        .setNegativeButton(R.string.cancel, null)
        .setPositiveButton(R.string.save, null)
        .create()
    private var id = 0

    private val data
        get() = Motorcycles.Motorcycle(
            id,
            binding.code.text.toString().toInt(),
            binding.name.text.toString(),
            binding.price.text.toString().toDouble(),
        )

    fun add() {
        id = 0
        binding.layoutCode.isEnabled = true
        binding.code.text = null
        binding.name.text = null
        binding.price.text = null

        dialog.setTitle(R.string.add_motorcycle)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { _ ->
            dialog.dismiss()
            activity.onAdd(data)
        }
    }

    fun edit(motorcycle: Motorcycles.Motorcycle) {
        id = motorcycle.id
        binding.layoutCode.isEnabled = false
        binding.code.setText(motorcycle.code.toString())
        binding.name.setText(motorcycle.name)
        binding.price.setText(motorcycle.price.toString())

        dialog.setTitle(R.string.edit_motorcycle)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { _ ->
            dialog.dismiss()
            activity.onEdit(motorcycle, data)
        }
    }
}