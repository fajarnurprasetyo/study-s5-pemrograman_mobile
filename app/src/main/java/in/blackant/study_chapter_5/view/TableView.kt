package `in`.blackant.study_chapter_5.view

import android.content.Context
import android.util.AttributeSet
//import android.view.View
import androidx.annotation.AttrRes
import com.evrencoskun.tableview.TableView as TV

class TableView : TV {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    )

//    init {
//        mRowHeaderRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
//        mColumnHeaderRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
//        mCellRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
//    }
}