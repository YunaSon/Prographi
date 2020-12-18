package org.prography.lemorning.utils

import org.prography.lemorning.config.Constants
import java.util.regex.Pattern

object Validators {

    @JvmStatic
    fun isValidEmail(src : String?) : Boolean =
        Pattern.matches("^[a-zA-Z0-9%+-._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$" ,src ?: "")

    @JvmStatic
    fun isValidPhone() {

    }

    @JvmStatic
    fun isValidPasswords(src1 : String, src2: String) : Int {
        return if (Pattern.matches("^[\\w]{6,20}$", src1) && Pattern.matches("^[\\w]{6,20}$", src2)) {
            if (src1 == src2) Constants.PASSWORD_SAME
            else Constants.PASSWORD_DIFFERENT
        } else {
            if (src1.isEmpty() || src2.isEmpty()) Constants.PASSWORD_AT_LEAST_ONE_EMPTY
            else Constants.PASSWORD_MALFORMED
        }
    }

    @JvmStatic
    fun isValidBirth(src: String) : Boolean =
        Pattern.matches("^[0-9]{4}-[0-9]{2}-[0-9]]{2}$", src)
}