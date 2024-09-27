package `in`.blackant.study_chapter_5.model

class Creditors : ArrayList<Creditors.Creditor>() {
    companion object {
        private const val ENDPOINT = "/creditor"

        fun get(api: Api): Creditors? {
            return api.get(ENDPOINT, Creditors::class.java)
        }
    }

    data class Creditor(
        val id: Int,
        val name: String,
        val job: String,
        val phone: String,
        val address: String,
    ) {
        companion object {
            fun get(api: Api, id: Int): Creditor? {
                val params = mapOf(Pair("id", id.toString()))
                return api.get(ENDPOINT, Creditor::class.java, params)
            }

            fun save(api: Api, data: Creditor): Creditor? {
                return api.post(ENDPOINT, Creditor::class.java, data)
            }

            fun delete(api: Api, data: Creditor): Creditor? {
                return api.delete(
                    ENDPOINT,
                    Creditor::class.java,
                    mapOf(Pair("id", data.id.toString()))
                )
            }
        }

        fun save(api: Api): Creditor? {
            return save(api, this)
        }

        fun delete(api: Api): Creditor? {
            return delete(api, this)
        }
    }
}
