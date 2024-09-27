package `in`.blackant.study_chapter_5.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import `in`.blackant.study_chapter_5.R

import `in`.blackant.study_chapter_5.databinding.ActivityMainBinding
import `in`.blackant.study_chapter_5.fragment.AboutFragment
import `in`.blackant.study_chapter_5.fragment.HomeFragment
import `in`.blackant.study_chapter_5.fragment.TransactionFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment()
                    1 -> TransactionFragment()
                    else -> AboutFragment()
                }
            }

            fun getFragmentIcon(position: Int): Drawable? {
                return when (position) {
                    0 -> AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_tab_home)
                    1 -> AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_tab_transaction)
                    else -> AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_tab_about)
                }
            }

            fun getFragmentTitle(position: Int): String {
                return when (position) {
                    0 -> getString(R.string.home)
                    1 -> getString(R.string.transaction)
                    else -> getString(R.string.about)
                }
            }
        }

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.icon = adapter.getFragmentIcon(position)
            tab.text = adapter.getFragmentTitle(position)
        }.attach()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}