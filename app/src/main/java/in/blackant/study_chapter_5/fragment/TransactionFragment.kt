package `in`.blackant.study_chapter_5.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.activity.CreditApplicationActivity
import `in`.blackant.study_chapter_5.activity.CreditApplicationDataActivity
import `in`.blackant.study_chapter_5.activity.InstallmentPaymentActivity
import `in`.blackant.study_chapter_5.activity.InstallmentPaymentDataActivity
import `in`.blackant.study_chapter_5.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTransactionBinding.inflate(layoutInflater)
        binding.btnCreditApplication.setOnClickListener(this)
        binding.btnInstallmentPayment.setOnClickListener(this)
        binding.btnCreditApplicationData.setOnClickListener(this)
        binding.btnInstallmentPaymentData.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        val cls = when (v?.id) {
            R.id.btn_credit_application -> CreditApplicationActivity::class.java
            R.id.btn_credit_application_data -> CreditApplicationDataActivity::class.java
            R.id.btn_installment_payment -> InstallmentPaymentActivity::class.java
            R.id.btn_installment_payment_data -> InstallmentPaymentDataActivity::class.java
            else -> return
        }
        startActivity(Intent(context, cls))
    }
}