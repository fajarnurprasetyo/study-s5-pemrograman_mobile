package `in`.blackant.study_chapter_5.adapter

class CreditorTableAdapter : TableAdapter() {
    override fun getCellItemViewType(position: Int): Int {
        return when (position) {
            4 -> ViewType.ACTION
            else -> super.getColumnHeaderItemViewType(position)
        }
    }
}