package `in`.blackant.study_chapter_5.dialog

import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.activity.EmployeeActivity
import `in`.blackant.study_chapter_5.databinding.DialogEmployeeBinding
import `in`.blackant.study_chapter_5.model.Employees

class EmployeeDialog(private val activity: EmployeeActivity) {
    interface Listener {
        fun onAdd(data: Employees.Employee)
        fun onEdit(oldData: Employees.Employee, data: Employees.Employee)
    }

    private val binding = DialogEmployeeBinding.inflate(activity.layoutInflater)
    private val dialog = MaterialAlertDialogBuilder(activity)
        .setView(binding.root)
        .setCancelable(false)
        .setNegativeButton(R.string.cancel, null)
        .setPositiveButton(R.string.save, null)
        .create()
    private var id = 0

    private val data
        get() = Employees.Employee(
            id,
            binding.code.text.toString().toInt(),
            binding.name.text.toString(),
            binding.role.text.toString(),
        )

    fun add() {
        id = 0
        binding.layoutCode.isEnabled = true
        binding.code.text = null
        binding.name.text = null
        binding.role.text = null

        dialog.setTitle(R.string.add_employee)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { _ ->
            dialog.dismiss()
            activity.onAdd(data)
        }
    }

    fun edit(employee: Employees.Employee) {
        id = employee.id
        binding.layoutCode.isEnabled = false
        binding.code.setText(employee.code.toString())
        binding.name.setText(employee.name)
        binding.role.setText(employee.role)

        dialog.setTitle(R.string.edit_employee)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { _ ->
            dialog.dismiss()
            activity.onEdit(employee, data)
        }
    }
}