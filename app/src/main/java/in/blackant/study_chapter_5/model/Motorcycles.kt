package `in`.blackant.study_chapter_5.model

class Motorcycles : ArrayList<Motorcycles.Motorcycle>() {
    companion object {
        private const val ENDPOINT = "/motorcycle"

        fun get(api: Api): Motorcycles? {
            return api.get(ENDPOINT, Motorcycles::class.java)
        }
    }

    data class Motorcycle(
        val id: Int,
        val code: Int,
        val name: String,
        val price: Double,
    ) {
        companion object {
            val dummy = Motorcycle(0, 0, "", 0.0)

            fun get(api: Api, id: Int): Motorcycle? {
                val params = mapOf(Pair("id", id.toString()))
                return api.get(ENDPOINT, Motorcycle::class.java, params)
            }

            fun save(api: Api, data: Motorcycle): Motorcycle? {
                return api.post(ENDPOINT, Motorcycle::class.java, data)
            }

            fun delete(api: Api, data: Motorcycle): Motorcycle? {
                return api.delete(
                    ENDPOINT,
                    Motorcycle::class.java,
                    mapOf(Pair("id", data.id.toString()))
                )
            }
        }

        fun save(api: Api): Motorcycle? {
            return save(api, this)
        }

        fun delete(api: Api): Motorcycle? {
            return delete(api, this)
        }
    }
}
