package `in`.blackant.study_chapter_5.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.adapter.MotorcycleTableAdapter
import `in`.blackant.study_chapter_5.databinding.ActivityDataTableBinding
import `in`.blackant.study_chapter_5.dialog.ConfirmDeleteDialog
import `in`.blackant.study_chapter_5.dialog.MotorcycleDialog
import `in`.blackant.study_chapter_5.extension.doAsync
import `in`.blackant.study_chapter_5.extension.hide
import `in`.blackant.study_chapter_5.extension.show
import `in`.blackant.study_chapter_5.extension.uiThread
import `in`.blackant.study_chapter_5.model.Api
import `in`.blackant.study_chapter_5.model.Motorcycles
import `in`.blackant.study_chapter_5.model.table.ActionCell
import `in`.blackant.study_chapter_5.model.table.Cell
import `in`.blackant.study_chapter_5.model.table.ColumnHeader
import `in`.blackant.study_chapter_5.model.table.CurrencyCell
import `in`.blackant.study_chapter_5.model.table.RowHeader
import kotlinx.coroutines.DelicateCoroutinesApi

class MotorcycleActivity : AppCompatActivity(), MotorcycleDialog.Listener {
    private lateinit var api: Api
    private lateinit var binding: ActivityDataTableBinding
    private lateinit var motorcycleDialog: MotorcycleDialog
    private lateinit var confirmDeleteDialog: ConfirmDeleteDialog
    private lateinit var adapter: MotorcycleTableAdapter
    private var motorcycles = Motorcycles()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        api = Api(this)
        binding = ActivityDataTableBinding.inflate(layoutInflater)
        motorcycleDialog = MotorcycleDialog(this)
        confirmDeleteDialog = ConfirmDeleteDialog(this)
        adapter = MotorcycleTableAdapter()

        binding.btnRefresh.setOnClickListener { loadData() }
        binding.btnAdd.setOnClickListener { motorcycleDialog.add() }
        binding.table.setAdapter(adapter)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadData()
    }

    private fun updateTable() {
        val columnHeader = listOf(
            ColumnHeader(getString(R.string.code)),
            ColumnHeader(getString(R.string.name)),
            ColumnHeader(getString(R.string.price)),
            ColumnHeader(getString(R.string.action)),
        )
        val rowHeaderItems = motorcycles.map { motorcycle ->
            RowHeader(motorcycles.indexOf(motorcycle) + 1)
        }
        val cellItems = motorcycles.map { motorcycle ->
            listOf(
                Cell(motorcycle.code),
                Cell(motorcycle.name),
                CurrencyCell(motorcycle.price, "IDR"),
                ActionCell(
                    ActionCell.Action { motorcycleDialog.edit(motorcycle) },
                    ActionCell.Action {
                        confirmDeleteDialog.show(
                            getString(
                                R.string.confirm_delete,
                                motorcycle.name
                            )
                        ) { onDelete(motorcycle) }
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
            val response = Motorcycles.get(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    motorcycles = response
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onAdd(data: Motorcycles.Motorcycle) {
        binding.loading.root.show()
        doAsync {
            val response = data.save(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    motorcycles.add(data)
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onEdit(oldData: Motorcycles.Motorcycle, data: Motorcycles.Motorcycle) {
        binding.loading.root.show()
        doAsync {
            val response = data.save(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    val index = motorcycles.indexOf(oldData)
                    motorcycles.removeAt(index)
                    motorcycles.add(index, response)
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onDelete(data: Motorcycles.Motorcycle) {
        binding.loading.root.show()
        doAsync {
            val response = data.delete(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    motorcycles.removeIf { item -> item.id == response.id }
                    updateTable()
                }
            }
        }
    }
}