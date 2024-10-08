package `in`.blackant.study_chapter_5.model.table

class CurrencyCell(override val content: Number, val localeCode: String = "USD") : Cell(content)
