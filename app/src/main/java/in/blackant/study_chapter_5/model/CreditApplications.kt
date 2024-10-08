package `in`.blackant.study_chapter_5.model

import java.util.Date

class CreditApplications : ArrayList<CreditApplications.CreditApplication>() {
    companion object {
        private const val ENDPOINT = "/creditApplication"

        fun get(api: Api): CreditApplications? {
            return api.get(ENDPOINT, CreditApplications::class.java)
        }
    }

    data class CreditApplication(
        val id: Int,
        val creditorId: Int,
        val motorcycleId: Int,
        val downPayment: Double,
        val interestPerYear: Double,
        val installmentPeriod: Int,
        val creditor: Creditors.Creditor = Creditors.Creditor.dummy,
        val motorcycle: Motorcycles.Motorcycle = Motorcycles.Motorcycle.dummy,
        val createdAt: Date = Date(),
    ) {
        companion object {
            fun get(api: Api, id: Int): CreditApplication? {
                val params = mapOf(Pair("id", id.toString()))
                return api.get(ENDPOINT, CreditApplication::class.java, params)
            }

            fun save(api: Api, data: CreditApplication): CreditApplication? {
                return api.post(ENDPOINT, CreditApplication::class.java, data)
            }

            fun delete(api: Api, data: CreditApplication): CreditApplication? {
                return api.delete(
                    ENDPOINT,
                    CreditApplication::class.java,
                    mapOf(Pair("id", data.id.toString()))
                )
            }
        }

        fun save(api: Api): CreditApplication? {
            return save(api, this)
        }

        fun delete(api: Api): CreditApplication? {
            return delete(api, this)
        }
    }
}
