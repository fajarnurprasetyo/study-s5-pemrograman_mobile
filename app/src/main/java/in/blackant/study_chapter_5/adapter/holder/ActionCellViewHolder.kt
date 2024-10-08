package `in`.blackant.study_chapter_5.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.google.android.material.button.MaterialButton
import `in`.blackant.study_chapter_5.databinding.TableActionCellBinding
import `in`.blackant.study_chapter_5.model.table.ActionCell

class ActionCellViewHolder(
    parent: ViewGroup,
    private val binding: TableActionCellBinding = TableActionCellBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ),
) : AbstractViewHolder(binding.root) {
    fun bind(action: ActionCell?) {
        if (action == null) {
            binding.primary.visibility = View.GONE
            binding.secondary.visibility = View.GONE
            return
        }

        binding.primary.visibility = View.VISIBLE
        binding.primary.setOnClickListener(action.primary.listener)
        if (action.primary.icon != null) {
            (binding.primary as MaterialButton).setIconResource(action.primary.icon)
        }

        if (action.secondary == null) {
            binding.secondary.visibility = View.GONE
            return
        }

        binding.secondary.visibility = View.VISIBLE
        binding.secondary.setOnClickListener(action.secondary.listener)
        if (action.secondary.icon != null) {
            (binding.secondary as MaterialButton).setIconResource(action.secondary.icon)
        }
    }
}
