package `in`.blackant.study_chapter_5.adapter

class MotorcycleTableAdapter : TableAdapter() {
    override fun getCellItemViewType(position: Int): Int {
        return when (position) {
            2 -> ViewType.CURRENCY
            3 -> ViewType.ACTION
            else -> super.getColumnHeaderItemViewType(position)
        }
    }
}