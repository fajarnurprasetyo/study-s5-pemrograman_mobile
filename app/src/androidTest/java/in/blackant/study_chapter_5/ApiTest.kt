package `in`.blackant.study_chapter_5

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import `in`.blackant.study_chapter_5.model.Api
import `in`.blackant.study_chapter_5.model.Employees

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ApiTest {
    companion object {
        const val BASE_URL = "http://study.blackant.net/KreditMotor"
    }

    @Test
    fun test() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val api = Api(context)
        val response = api.get("$BASE_URL/employee.php", Employees::class.java)
        println(response)
    }
}