package `in`.blackant.study_chapter_5.model

class Employees : ArrayList<Employees.Employee>() {
    companion object {
        private const val ENDPOINT = "/employee"

        fun get(api: Api): Employees? {
            return api.get(ENDPOINT, Employees::class.java)
        }
    }

    data class Employee(
        val id: Int,
        val code: Int,
        val name: String,
        val role: String,
    ) {
        companion object {
            fun get(api: Api, id: Int): Employee? {
                val params = mapOf(Pair("id", id.toString()))
                return api.get(ENDPOINT, Employee::class.java, params)
            }

            fun save(api: Api, data: Employee): Employee? {
                return api.post(ENDPOINT, Employee::class.java, data)
            }

            fun delete(api: Api, data: Employee): Employee? {
                return api.delete(
                    ENDPOINT,
                    Employee::class.java,
                    mapOf(Pair("id", data.id.toString()))
                )
            }
        }

        fun save(api: Api): Employee? {
            return save(api, this)
        }

        fun delete(api: Api): Employee? {
            return delete(api, this)
        }
    }
}
