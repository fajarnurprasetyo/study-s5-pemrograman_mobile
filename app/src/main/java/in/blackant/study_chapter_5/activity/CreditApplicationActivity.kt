package `in`.blackant.study_chapter_5.activity

import android.content.Context
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import `in`.blackant.study_chapter_5.databinding.ActivityCreditApplicationBinding
import `in`.blackant.study_chapter_5.extension.doAsync
import `in`.blackant.study_chapter_5.extension.hide
import `in`.blackant.study_chapter_5.extension.show
import `in`.blackant.study_chapter_5.extension.uiThread
import `in`.blackant.study_chapter_5.model.Api
import `in`.blackant.study_chapter_5.model.CreditApplications
import `in`.blackant.study_chapter_5.model.Creditors
import `in`.blackant.study_chapter_5.model.Motorcycles
import kotlinx.coroutines.DelicateCoroutinesApi
import java.text.NumberFormat
import java.util.Currency

class CreditApplicationActivity : AppCompatActivity() {
    private val api: Api by lazy { Api(this) }
    private val binding: ActivityCreditApplicationBinding by lazy {
        ActivityCreditApplicationBinding.inflate(layoutInflater)
    }
    private var creditors = Creditors()
    private var motorcycles = Motorcycles()

    private var creditor: Creditors.Creditor? = null
    private var motorcycle: Motorcycles.Motorcycle? = null
    private var downPayment = 0.0
    private var interestPerYear = 0.0
    private var installmentPeriod = 0
    private var creditPrice = 0.0
    private var totalCredit = 0.0
    private var monthlyInstallments = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding.creditor.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, id ->
            creditor = creditors.firstOrNull { data -> data.id.toLong() == id }
            updateData()
        }

        binding.motorcycle.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, id ->
            motorcycle = motorcycles.firstOrNull { data -> data.id.toLong() == id }
            updateData()
        }

        binding.downPayment.doAfterTextChanged { text ->
            downPayment = text.toString().toDoubleOrNull() ?: 0.0
            updateData()
        }

        binding.interestPerYear.doAfterTextChanged { text ->
            interestPerYear = text.toString().toDoubleOrNull() ?: 0.0
            updateData()
        }

        binding.installmentPeriod.doAfterTextChanged { text ->
            installmentPeriod = text.toString().toIntOrNull() ?: 0
            updateData()
        }

        binding.btnReset.setOnClickListener { resetData() }

        binding.btnSave.setOnClickListener { saveData() }

        updateData()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                binding.root.paddingLeft,
                systemBars.top,
                binding.root.paddingRight,
                systemBars.bottom
            )
            insets
        }

        loadData()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadData() {
        doAsync {
            binding.loading.root.show()
            creditors = Creditors.get(api) ?: Creditors()
            motorcycles = Motorcycles.get(api) ?: Motorcycles()
            uiThread {
                binding.loading.root.hide()
                val activity = this@CreditApplicationActivity
                binding.creditor.setAdapter(CreditorsAdapter(activity, creditors))
                binding.motorcycle.setAdapter(MotorcyclesAdapter(activity, motorcycles))
            }
        }
    }

    private fun resetData() {
        creditor = null
        motorcycle = null
        binding.creditor.setText(null, false)
        binding.motorcycle.setText(null, false)
        binding.downPayment.text = null
        binding.interestPerYear.text = null
        binding.installmentPeriod.text = null
    }

    private val currencyFormatter = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("IDR")
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    private fun updateData() {
        if (motorcycle == null) {
            downPayment = 0.0
            interestPerYear = 0.0
            installmentPeriod = 0
            creditPrice = 0.0
            totalCredit = 0.0
            monthlyInstallments = 0.0
        } else {
            creditPrice = motorcycle!!.price - downPayment
            totalCredit = creditPrice + (creditPrice * (interestPerYear / 100) * 12)
            monthlyInstallments = totalCredit / installmentPeriod
        }

        binding.motorcycleName.text = motorcycle?.name ?: "-"
        binding.motorcyclePrice.text = currencyFormatter.format(motorcycle?.price ?: 0)
        binding.creditPrice.text = currencyFormatter.format(creditPrice)
        binding.totalCredit.text = currencyFormatter.format(totalCredit)
        binding.monthlyInstallments.text = currencyFormatter.format(monthlyInstallments)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveData() {
        if (creditor == null || motorcycle == null) return
        binding.loading.root.show()
        doAsync {
            val creditApplication = CreditApplications.CreditApplication(
                0,
                creditor!!.id,
                motorcycle!!.id,
                downPayment,
                interestPerYear,
                installmentPeriod
            ).save(api)
            uiThread {
                binding.loading.root.hide()
                if (creditApplication != null) {
                    Toast.makeText(
                        this@CreditApplicationActivity,
                        "",
                        Toast.LENGTH_LONG,
                    ).show()
                    resetData()
                }
            }
        }
    }

    class CreditorsAdapter(context: Context, private val creditors: Creditors) :
        ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            creditors.map { creditor -> creditor.name }
        ) {
        override fun getItemId(position: Int): Long {
            return creditors[position].id.toLong()
        }
    }

    class MotorcyclesAdapter(context: Context, private val motorcycles: Motorcycles) :
        ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            motorcycles.map { creditor -> creditor.code.toString() }
        ) {
        override fun getItemId(position: Int): Long {
            return motorcycles[position].id.toLong()
        }
    }
}