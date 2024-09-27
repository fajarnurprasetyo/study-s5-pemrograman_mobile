package `in`.blackant.study_chapter_5.adapter

import android.content.Context
import `in`.blackant.study_chapter_5.R
import `in`.blackant.study_chapter_5.dialog.EmployeeDialog
import `in`.blackant.study_chapter_5.model.Employees
import `in`.blackant.study_chapter_5.model.table.ActionCell
import `in`.blackant.study_chapter_5.model.table.Cell
import `in`.blackant.study_chapter_5.model.table.ColumnHeader
import `in`.blackant.study_chapter_5.model.table.RowHeader

class CreditorTableAdapter : TableAdapter() {
    override fun getCellItemViewType(position: Int): Int {
        return when (position) {
            4 -> ViewType.ACTION
            else -> super.getColumnHeaderItemViewType(position)
        }
    }
}