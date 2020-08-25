package ir.omidtaheri.mainpage

import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.RuleChain

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RuleChain

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}