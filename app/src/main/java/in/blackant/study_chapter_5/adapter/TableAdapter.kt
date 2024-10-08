package `in`.blackant.study_chapter_5.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import `in`.blackant.study_chapter_5.adapter.holder.ActionCellViewHolder
import `in`.blackant.study_chapter_5.adapter.holder.ColumnHeaderViewHolder
import `in`.blackant.study_chapter_5.adapter.holder.CurrencyCellViewHolder
import `in`.blackant.study_chapter_5.adapter.holder.DateCellViewHolder
import `in`.blackant.study_chapter_5.adapter.holder.RowHeaderViewHolder
import `in`.blackant.study_chapter_5.adapter.holder.StringCellViewHolder
import `in`.blackant.study_chapter_5.databinding.TableCornerBinding
import `in`.blackant.study_chapter_5.model.table.ActionCell
import `in`.blackant.study_chapter_5.model.table.Cell
import `in`.blackant.study_chapter_5.model.table.ColumnHeader
import `in`.blackant.study_chapter_5.model.table.CurrencyCell
import `in`.blackant.study_chapter_5.model.table.DateCell
import `in`.blackant.study_chapter_5.model.table.RowHeader

abstract class TableAdapter : AbstractTableAdapter<ColumnHeader, RowHeader, Cell>() {
    class ViewType {
        companion object {
            const val DEFAULT = 0
            const val DATE = 1
            const val CURRENCY = 2
            const val ACTION = 3
        }
    }

    override fun getColumnHeaderItemViewType(position: Int): Int {
        return ViewType.DEFAULT
    }

    override fun getCellItemViewType(position: Int): Int {
        return ViewType.DEFAULT
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        val binding = TableCornerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding.root
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return RowHeaderViewHolder(parent)
    }

    override fun onBindRowHeaderViewHolder(
        holder: AbstractViewHolder,
        rowHeaderItemModel: RowHeader?,
        rowPosition: Int,
    ) {
        (holder as RowHeaderViewHolder).bind(rowHeaderItemModel)
    }

    override fun onCreateColumnHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AbstractViewHolder {
        return ColumnHeaderViewHolder(parent)
    }

    override fun onBindColumnHeaderViewHolder(
        holder: AbstractViewHolder,
        columnHeaderItemModel: ColumnHeader?,
        columnPosition: Int,
    ) {
        (holder as ColumnHeaderViewHolder).bind(columnHeaderItemModel)
    }

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return when (viewType) {
            ViewType.DATE -> DateCellViewHolder(parent)
            ViewType.CURRENCY -> CurrencyCellViewHolder(parent)
            ViewType.ACTION -> ActionCellViewHolder(parent)
            else -> StringCellViewHolder(parent)
        }
    }

    override fun onBindCellViewHolder(
        holder: AbstractViewHolder,
        cellItemModel: Cell?,
        columnPosition: Int,
        rowPosition: Int,
    ) {
        when (getCellItemViewType(columnPosition)) {
            ViewType.DATE -> (holder as DateCellViewHolder).bind(cellItemModel as DateCell?)
            ViewType.CURRENCY -> (holder as CurrencyCellViewHolder).bind(cellItemModel as CurrencyCell?)
            ViewType.ACTION -> (holder as ActionCellViewHolder).bind(cellItemModel as ActionCell?)
            else -> (holder as StringCellViewHolder).bind((cellItemModel?.content ?: "").toString())
        }
    }
}
