package `in`.blackant.study_chapter_5.model.table

import android.view.View.OnClickListener

data class ActionCell(val primary: Action, val secondary: Action? = null) : Cell() {
    data class Action(val listener: OnClickListener)
}