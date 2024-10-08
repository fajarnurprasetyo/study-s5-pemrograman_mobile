package `in`.blackant.study_chapter_5.view.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import `in`.blackant.study_chapter_5.databinding.TableColumnHeaderBinding
import `in`.blackant.study_chapter_5.model.table.ColumnHeader

class ColumnHeaderViewHolder(
    parent: ViewGroup,
    private val binding: TableColumnHeaderBinding = TableColumnHeaderBinding.inflate(
        LayoutInflater.from(parent.context),parent,false
    ),
) : AbstractViewHolder(binding.root) {
    fun bind(column: ColumnHeader?) {
        binding.text.text = column?.content
        binding.root.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        binding.text.requestLayout()
    }
}