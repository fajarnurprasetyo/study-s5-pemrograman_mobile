package `in`.blackant.study_chapter_5.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import `in`.blackant.study_chapter_5.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(layoutInflater).root
    }
}