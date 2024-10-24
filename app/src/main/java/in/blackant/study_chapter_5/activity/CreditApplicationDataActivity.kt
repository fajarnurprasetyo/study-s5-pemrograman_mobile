package `in`.blackant.study_chapter_5.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.adapter.CreditApplicationTableAdapter
import `in`.blackant.study_chapter_5.databinding.ActivityDataTableBinding
import `in`.blackant.study_chapter_5.dialog.ConfirmDeleteDialog
import `in`.blackant.study_chapter_5.extension.doAsync
import `in`.blackant.study_chapter_5.extension.hide
import `in`.blackant.study_chapter_5.extension.show
import `in`.blackant.study_chapter_5.extension.uiThread
import `in`.blackant.study_chapter_5.model.Api
import `in`.blackant.study_chapter_5.model.CreditApplications
import `in`.blackant.study_chapter_5.model.table.ActionCell
import `in`.blackant.study_chapter_5.model.table.Cell
import `in`.blackant.study_chapter_5.model.table.ColumnHeader
import `in`.blackant.study_chapter_5.model.table.CurrencyCell
import `in`.blackant.study_chapter_5.model.table.DateCell
import `in`.blackant.study_chapter_5.model.table.RowHeader
import kotlinx.coroutines.DelicateCoroutinesApi

class CreditApplicationDataActivity : AppCompatActivity() {
    private val api: Api by lazy { Api(this) }
    private val binding: ActivityDataTableBinding by lazy {
        ActivityDataTableBinding.inflate(layoutInflater)
    }
    private val confirmDeleteDialog: ConfirmDeleteDialog by lazy {
        ConfirmDeleteDialog(this)
    }
    private val adapter = CreditApplicationTableAdapter()
    private var creditApplications = CreditApplications()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding.table.setAdapter(adapter)
        binding.btnRefresh.setOnClickListener { loadData() }
        binding.btnAdd.hide()

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
            ColumnHeader("ID"),
            ColumnHeader(getString(R.string.date)),
            ColumnHeader(getString(R.string.creditor)),
            ColumnHeader(getString(R.string.address)),
            ColumnHeader(getString(R.string.motorcycle_code)),
            ColumnHeader(getString(R.string.motorcycle_name)),
            ColumnHeader(getString(R.string.motorcycle_price)),
            ColumnHeader(getString(R.string.down_payment)),
            ColumnHeader(getString(R.string.credit_price)),
            ColumnHeader(getString(R.string.interest_per_year)),
            ColumnHeader(getString(R.string.installment_period)),
            ColumnHeader(getString(R.string.total_credit)),
            ColumnHeader(getString(R.string.monthly_installments)),
            ColumnHeader(getString(R.string.action)),
        )
    }

    private fun updateTable() {
        val rowHeaderItems = creditApplications.map { creditApplication ->
            RowHeader(creditApplications.indexOf(creditApplication) + 1)
        }
        val cellItems = creditApplications.map { creditApplication ->
            val creditor = creditApplication.creditor
            val motorcycle = creditApplication.motorcycle
            val creditPrice = motorcycle.price - creditApplication.downPayment
            val totalCredit =
                creditPrice + (creditPrice * (creditApplication.interestPerYear / 100) * 12)
            val monthlyInstallments = totalCredit / creditApplication.installmentPeriod
            listOf(
                Cell(creditApplication.id),
                DateCell(creditApplication.createdAt, "in"),
                Cell(creditor.name),
                Cell(creditor.address),
                Cell(motorcycle.code),
                Cell(motorcycle.name),
                CurrencyCell(motorcycle.price, "IDR"),
                CurrencyCell(creditApplication.downPayment, "IDR"),
                CurrencyCell(creditPrice, "IDR"),
                Cell(creditApplication.interestPerYear),
                Cell(creditApplication.installmentPeriod),
                CurrencyCell(totalCredit, "IDR"),
                CurrencyCell(monthlyInstallments, "IDR"),
                ActionCell(
                    ActionCell.Action(R.drawable.ic_pdf_24) { createPdf(creditApplication) },
                    ActionCell.Action {
                        confirmDeleteDialog.show(
                            getString(
                                R.string.confirm_delete,
                                creditApplication.id.toString()
                            )
                        ) { onDelete(creditApplication) }
                    },
                ),
            )
        }
        adapter.setAllItems(columnHeader, rowHeaderItems, cellItems)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadData() {
        binding.loading.root.show()
        doAsync {
            val response = CreditApplications.get(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    creditApplications = response
                    updateTable()
                }
            }
        }
    }

    private fun createPdf(creditApplication: CreditApplications.CreditApplication) {

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onDelete(data: CreditApplications.CreditApplication) {
        binding.loading.root.show()
        doAsync {
            val response = data.delete(api)
            uiThread {
                binding.loading.root.hide()
                if (response != null) {
                    creditApplications.removeIf { item -> item.id == response.id }
                    updateTable()
                }
            }
        }
    }
}