package `in`.blackant.study_chapter_5.adapter

class CreditApplicationTableAdapter : TableAdapter() {
    override fun getCellItemViewType(position: Int): Int {
        return when (position) {
            1 -> ViewType.DATE
            6, 7, 8, 11, 12 -> ViewType.CURRENCY
            13 -> ViewType.ACTION
            else -> super.getColumnHeaderItemViewType(position)
        }
    }
}