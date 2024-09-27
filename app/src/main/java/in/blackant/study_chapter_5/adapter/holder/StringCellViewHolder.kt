package `in`.blackant.study_chapter_5.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import `in`.blackant.study_chapter_5.databinding.TableActionCellBinding
import `in`.blackant.study_chapter_5.databinding.TableStringCellBinding

open class StringCellViewHolder(
    parent: ViewGroup,
    private val binding: TableStringCellBinding = TableStringCellBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ),
) : AbstractViewHolder(binding.root) {
    open fun bind(text: String) {
        binding.text.text = text
        binding.root.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        binding.text.requestLayout()
    }
}
