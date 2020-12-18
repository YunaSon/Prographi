package org.prography.lemorning.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import org.prography.lemorning.R
import org.prography.lemorning.config.Constants

object Converters {

    @JvmStatic
    fun backDrawableFromGender(context: Context, gender : String, idx: Int) : Drawable? =
        context.getDrawable(when(idx) {
            0 -> if (gender == Constants.MALE) R.drawable.background_round_4_left_yellow else R.drawable.background_round_4_left_border_efefef
            else -> if (gender == Constants.MALE) R.drawable.background_round_4_right_border_efefef else R.drawable.background_round_4_right_yellow
        })

    @JvmStatic
    @ColorInt fun txtColorFromGender(context: Context, gender : String, idx: Int) : Int =
        context.getColor(when(idx) {
            0 -> if (gender == Constants.MALE) R.color.white else R.color.colorPrimary
            else -> if (gender == Constants.MALE) R.color.colorPrimary else R.color.white
        })

    @JvmStatic
    @ColorInt fun txtColorFromPasswordValidation(context: Context, validation: Int) : Int =
        context.getColor(if (validation == Constants.PASSWORD_SAME) R.color.colorAccept else R.color.colorWarn)

    @JvmStatic
    fun txtFromPasswordValidation(validation: Int) : String =
        when (validation) {
            Constants.PASSWORD_SAME -> "Correct!"
            Constants.PASSWORD_DIFFERENT -> "Incorrect!"
            Constants.PASSWORD_MALFORMED -> "Should be 6 ~ 20 letters"
            else -> ""
        }

    @JvmStatic
    @ColorInt fun txtColorFromEmailValidation(context: Context, validation: Int) : Int =
        context.getColor(if (validation == Constants.EMAIL_VALID) R.color.colorAccept else R.color.colorWarn)

    @JvmStatic
    fun txtFromEmailValidation(validation: Int) : String =
        when (validation) {
            Constants.EMAIL_VALID -> "You can use it!"
            Constants.EMAIL_INVALID -> "Already Exist!"
            Constants.EMAIL_UNCHECKED -> ""
            else -> ""
        }


    @JvmStatic
    fun mediaPositionToRealTimeTxt(position: Int) : String {
        var seconds = position / 1000
        return String.format("%02d:%02d", seconds / 60, seconds % 60)
    }

}