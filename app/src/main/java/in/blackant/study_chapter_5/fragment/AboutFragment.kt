package `in`.blackant.study_chapter_5.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.BuildConfig
import `in`.blackant.study_chapter_5.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAboutBinding.inflate(layoutInflater)
        binding.version.text = getString(R.string.version, BuildConfig.VERSION_NAME)
        return binding.root
    }
}