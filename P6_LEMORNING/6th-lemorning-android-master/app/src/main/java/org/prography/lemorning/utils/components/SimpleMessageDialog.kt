package org.prography.lemorning.utils.components

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.dialog_simple_message.*
import org.prography.lemorning.R

class SimpleMessageDialog(
    context : Context,
    val onClickListener: OnClickListener? = null,
    val message: String? = null,
    val btnText: String = context.getString(R.string.confirm),
    var cancel : Boolean = true)
    : Dialog(context) {

    interface OnClickListener {
        fun onClick(dialog: Dialog?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_simple_message)

        /* Set View */
        tv_desc_dialog_simple_message.text = message
        tv_positive_dialog_simple_message.text = btnText

        /* Set On Click Listener */
        tv_positive_dialog_simple_message.setOnClickListener { onClickListener?.onClick(this) ?: dismiss() }

        /* Set Window */
        window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setGravity(Gravity.CENTER)
            it.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        }
        setCancelable(cancel)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val dialogBounds = Rect()
        findViewById<View>(R.id.cl_dialog_simple_message).getHitRect(dialogBounds)
        if (!dialogBounds.contains(ev.x.toInt(), ev.y.toInt())) {
            dismiss()
        }
        return super.dispatchTouchEvent(ev)
    }
}