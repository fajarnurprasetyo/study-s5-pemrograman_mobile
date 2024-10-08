package `in`.blackant.study_chapter_5.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.adapter.CreditorTableAdapter
import `in`.blackant.study_chapter_5.databinding.ActivityDataTableBinding
import `in`.blackant.study_chapter_5.dialog.ConfirmDeleteDialog
import `in`.blackant.study_chapter_5.dialog.CreditorDialog
import `in`.blackant.study_chapter_5.extension.doAsync
import `in`.blackant.study_chapter_5.extension.hide
import `in`.blackant.study_chapter_5.extension.show
import `in`.blackant.study_chapter_5.extension.uiThread
import `in`.blackant.study_chapter_5.model.Api
import `in`.blackant.study_chapter_5.model.Creditors
import `in`.blackant.study_chapter_5.model.table.ActionCell
import `in`.blackant.study_chapter_5.model.table.Cell
import `in`.blackant.study_chapter_5.model.table.ColumnHeader
import `in`.blackant.study_chapter_5.model.table.RowHeader
import kotlinx.coroutines.DelicateCoroutinesApi

class CreditorActivity : AppCompatActivity(), CreditorDialog.Listener {
    private val api: Api by lazy { Api(this) }
    private val binding: ActivityDataTableBinding by lazy {
        ActivityDataTableBinding.inflate(
            layoutInflater
        )
    }
    private val motorcycleDialog: CreditorDialog by lazy { CreditorDialog(this) }
    private val confirmDeleteDialog: ConfirmDeleteDialog by lazy { ConfirmDeleteDialog(this) }
    private val adapter = CreditorTableAdapter()
    private var creditors = Creditors()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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

    private val columnHeader: List<ColumnHeader> by lazy {
        listOf(
            ColumnHeader(getString(R.string.name)),
            ColumnHeader(getString(R.string.job)),
            ColumnHeader(getString(R.string.phone)),
            ColumnHeader(getString(R.string.address)),
            ColumnHeader(getString(R.string.action)),
        )
    }

    private fun updateTable() {
        val rowHeaderItems = creditors.map { creditor ->
            RowHeader(creditors.indexOf(creditor) + 1)
        }
        val cellItems = creditors.map { creditor ->
            listOf(
                Cell(creditor.name),
                Cell(creditor.job),
                Cell(creditor.phone),
                Cell(creditor.address),
                ActionCell(
                    ActionCell.Action { motorcycleDialog.edit(creditor) },
                    ActionCell.Action {
                        confirmDeleteDialog.show(
                            getString(
                                R.string.confirm_delete,
                                creditor.name
                            )
                        ) { onDelete(creditor) }
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
            val response = Creditors.get(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    creditors = response
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onAdd(data: Creditors.Creditor) {
        binding.loading.root.show()
        doAsync {
            val response = data.save(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    creditors.add(data)
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onEdit(oldData: Creditors.Creditor, data: Creditors.Creditor) {
        binding.loading.root.show()
        doAsync {
            val response = data.save(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    val index = creditors.indexOf(oldData)
                    creditors.removeAt(index)
                    creditors.add(index, response)
                    updateTable()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onDelete(data: Creditors.Creditor) {
        binding.loading.root.show()
        doAsync {
            val response = data.delete(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    creditors.removeIf { item -> item.id == response.id }
                    updateTable()
                }
            }
        }
    }
}