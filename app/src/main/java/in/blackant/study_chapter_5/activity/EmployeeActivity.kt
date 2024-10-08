package `in`.blackant.study_chapter_5.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.adapter.EmployeeTableAdapter
import `in`.blackant.study_chapter_5.databinding.ActivityDataTableBinding
import `in`.blackant.study_chapter_5.dialog.ConfirmDeleteDialog
import `in`.blackant.study_chapter_5.dialog.EmployeeDialog
import `in`.blackant.study_chapter_5.extension.doAsync
import `in`.blackant.study_chapter_5.extension.hide
import `in`.blackant.study_chapter_5.extension.show
import `in`.blackant.study_chapter_5.extension.uiThread
import `in`.blackant.study_chapter_5.model.Employees
import `in`.blackant.study_chapter_5.model.Api
import `in`.blackant.study_chapter_5.model.table.ActionCell
import `in`.blackant.study_chapter_5.model.table.Cell
import `in`.blackant.study_chapter_5.model.table.ColumnHeader
import `in`.blackant.study_chapter_5.model.table.RowHeader
import kotlinx.coroutines.DelicateCoroutinesApi

class EmployeeActivity : AppCompatActivity(), EmployeeDialog.Listener {
    private lateinit var api: Api
    private lateinit var binding: ActivityDataTableBinding
    private lateinit var employeeDialog: EmployeeDialog
    private lateinit var confirmDeleteDialog: ConfirmDeleteDialog
    private lateinit var adapter: EmployeeTableAdapter
    private var employees = Employees()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        api = Api(this)
        binding = ActivityDataTableBinding.inflate(layoutInflater)
        employeeDialog = EmployeeDialog(this)
        confirmDeleteDialog = ConfirmDeleteDialog(this)
        adapter = EmployeeTableAdapter()

        binding.btnRefresh.setOnClickListener { loadData() }
        binding.btnAdd.setOnClickListener { employeeDialog.add() }
        binding.table.setAdapter(adapter)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadData()
    }

    private val columnHeader: List<ColumnHeader> by lazy {
        listOf(
            ColumnHeader(getString(R.string.code)),
            ColumnHeader(getString(R.string.name)),
            ColumnHeader(getString(R.string.role)),
            ColumnHeader(getString(R.string.action)),
        )
    }

    private fun updateTable() {
        val rowHeaderItems = employees.map { employee ->
            RowHeader(employees.indexOf(employee) + 1)
        }
        val cellItems = employees.map { employee ->
            listOf(
                Cell(employee.code),
                Cell(employee.name),
                Cell(employee.role),
                ActionCell(
                    ActionCell.Action { employeeDialog.edit(employee) },
                    ActionCell.Action {
                        confirmDeleteDialog.show(
                            getString(
                                R.string.confirm_delete,
                                employee.name
                            )
                        ) { onDelete(employee) }
                    }
                ),
            )
        }
        adapter.setAllItems(columnHeader, rowHeaderItems, cellItems)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadData() {
        binding.loading.root.show()
        doAsync {
            val response = Employees.get(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    employees = response
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onAdd(data: Employees.Employee) {
        binding.loading.root.show()
        doAsync {
            val response = data.save(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    employees.add(data)
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onEdit(oldData: Employees.Employee, data: Employees.Employee) {
        binding.loading.root.show()
        doAsync {
            val response = data.save(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    val index = employees.indexOf(oldData)
                    employees.removeAt(index)
                    employees.add(index, response)
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onDelete(data: Employees.Employee) {
        binding.loading.root.show()
        doAsync {
            val response = data.delete(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    employees.removeIf { item -> item.id == response.id }
                    updateTable()
                }
            }
        }
    }
}
