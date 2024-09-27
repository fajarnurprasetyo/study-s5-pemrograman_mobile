package `in`.blackant.study_chapter_5.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.activity.CreditorsActivity
import `in`.blackant.study_chapter_5.activity.EmployeeActivity
import `in`.blackant.study_chapter_5.activity.MotorcycleActivity
import `in`.blackant.study_chapter_5.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.btnEmployee.setOnClickListener(this)
        binding.btnMotorcycle.setOnClickListener(this)
        binding.btnCreditors.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        val cls = when (v?.id) {
            R.id.btn_employee -> EmployeeActivity::class.java
            R.id.btn_motorcycle -> MotorcycleActivity::class.java
            R.id.btn_creditors -> CreditorsActivity::class.java
            else -> return
        }
        startActivity(Intent(context, cls))
    }
}