package `in`.blackant.study_chapter_5.model.table

import java.util.Date

class DateCell(override val content: Date, val localeCode: String = "en") : Cell(content)
