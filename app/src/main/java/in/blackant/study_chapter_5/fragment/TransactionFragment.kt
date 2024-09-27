package `in`.blackant.study_chapter_5.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import `in`.blackant.study_chapter_5.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTransactionBinding.inflate(layoutInflater).root
    }
}