package com.example.appevents

import com.example.appevents.util.Util
import org.junit.Assert.*
import org.junit.Test

class UnitTest {
    @Test
    fun emailValidationTest() {
        val util = Util()
        val invalidEmail = "teste"
        val validEmail = "teste@test.com"

        assertEquals(util.isEmailValid(validEmail), true)
        assertEquals(util.isEmailValid(invalidEmail), false)
    }

    @Test
    fun nameValidationTest() {
        val util = Util()
        val invalidName = " "
        val validName = "Junior"

        assertEquals(util.isNameValid(validName), true)
        assertEquals(util.isNameValid(invalidName), false)
    }
}