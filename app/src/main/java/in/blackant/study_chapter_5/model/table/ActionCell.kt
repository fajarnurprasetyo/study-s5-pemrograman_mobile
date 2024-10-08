package `in`.blackant.study_chapter_5.model.table

import android.view.View.OnClickListener

data class ActionCell(val primary: Action, val secondary: Action? = null) : Cell() {
    class Action {
        val icon: Int?
        val listener: OnClickListener

        constructor(listener: OnClickListener) : this(null, listener)
        constructor(icon: Int?, listener: OnClickListener) {
            this.icon = icon
            this.listener = listener
        }
    }
}