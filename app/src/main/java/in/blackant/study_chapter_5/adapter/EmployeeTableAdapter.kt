package `in`.blackant.study_chapter_5.adapter

class EmployeeTableAdapter : TableAdapter() {
    override fun getCellItemViewType(position: Int): Int {
        return when (position) {
            3 -> ViewType.ACTION
            else -> super.getColumnHeaderItemViewType(position)
        }
    }
}