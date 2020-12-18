package org.prography.lemorning.utils.components

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bigkoo.pickerview.R
import com.bigkoo.pickerview.configure.PickerOptions
import com.bigkoo.pickerview.view.BasePickerView
import com.bigkoo.pickerview.view.WheelTime
import java.text.ParseException
import java.util.*

class TimePickerSlider(context: Context)
    : BasePickerView(context), View.OnClickListener {

    constructor(pickerOptions: PickerOptions) : this(pickerOptions.context) {
        mPickerOptions = pickerOptions
        initView(pickerOptions.context)
    }

    private var wheelTime: WheelTime? = null//自定义控件
    private val TAG_SUBMIT = "submit"
    private val TAG_CANCEL = "cancel"

    private fun initView(context: Context) {
        setDialogOutSideCancelable()
        initViews()
        initAnim()
        if (mPickerOptions.customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer)

            //顶部标题
            val tvTitle = findViewById(R.id.tvTitle) as TextView
            val rv_top_bar = findViewById(R.id.rv_topbar) as RelativeLayout

            //确定和取消按钮
            val btnSubmit = findViewById(R.id.btnSubmit) as Button
            val btnCancel = findViewById(R.id.btnCancel) as Button
            btnSubmit.apply {
                tag = TAG_SUBMIT
                typeface = ResourcesCompat.getFont(context, org.prography.lemorning.R.font.notosanscjkkr_medium)
                includeFontPadding = false
                letterSpacing = 0f
                text =
                    if (TextUtils.isEmpty(mPickerOptions.textContentConfirm)) context.resources.getString(R.string.pickerview_submit)
                    else mPickerOptions.textContentConfirm
                textSize = mPickerOptions.textSizeSubmitCancel.toFloat()
                setTextColor(mPickerOptions.textColorConfirm)
            }.setOnClickListener(this)
            btnCancel.apply {
                tag = TAG_CANCEL
                typeface = ResourcesCompat.getFont(context, org.prography.lemorning.R.font.notosanscjkkr_regular)
                includeFontPadding = false
                letterSpacing = 0f
                text =
                    if (TextUtils.isEmpty(mPickerOptions.textContentCancel)) context.resources.getString(R.string.pickerview_cancel)
                    else mPickerOptions.textContentCancel
                textSize = mPickerOptions.textSizeSubmitCancel.toFloat()
                setTextColor(mPickerOptions.textColorCancel)
            }.setOnClickListener(this)
            tvTitle.apply {
                typeface = ResourcesCompat.getFont(context, org.prography.lemorning.R.font.notosanscjkkr_regular)
                includeFontPadding = false
                letterSpacing = 0f
                text =
                    if (TextUtils.isEmpty(mPickerOptions.textContentTitle)) ""
                    else mPickerOptions.textContentTitle //默认为空
                textSize = mPickerOptions.textSizeTitle.toFloat()
                setTextColor(mPickerOptions.textColorTitle)
            }

            //设置color
            rv_top_bar.setBackgroundColor(mPickerOptions.bgColorTitle)

        } else {
            mPickerOptions.customListener.customLayout(
                LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer)
            )
        }
        // 时间转轮 自定义控件
        val timePickerView = findViewById(R.id.timepicker) as LinearLayout
        timePickerView.setBackgroundColor(mPickerOptions.bgColorWheel)
        initWheelTime(timePickerView)
    }

    private fun initWheelTime(timePickerView: LinearLayout) {
        wheelTime = WheelTime(
            timePickerView,
            mPickerOptions.type,
            mPickerOptions.textGravity,
            mPickerOptions.textSizeContent
        )
        mPickerOptions.timeSelectChangeListener?.let {
            wheelTime?.setSelectChangeCallback {
                try {
                    val date = WheelTime.dateFormat.parse(wheelTime!!.getTime())
                    it.onTimeSelectChanged(date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }
        wheelTime?.setLunarMode(mPickerOptions.isLunarCalendar)
        if (mPickerOptions.startYear != 0 && mPickerOptions.endYear != 0 && mPickerOptions.startYear <= mPickerOptions.endYear) {
            setRange()
        }

        //若手动设置了时间范围限制
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            require(mPickerOptions.startDate.timeInMillis <= mPickerOptions.endDate.timeInMillis) { "startDate can't be later than endDate" }
            setRangDate()
        } else if (mPickerOptions.startDate != null) {
            require(mPickerOptions.startDate[Calendar.YEAR] >= 1900) { "The startDate can not as early as 1900" }
            setRangDate()
        } else if (mPickerOptions.endDate != null) {
            require(mPickerOptions.endDate[Calendar.YEAR] <= 2100) { "The endDate should not be later than 2100" }
            setRangDate()
        } else { //没有设置时间范围限制，则会使用默认范围。
            setRangDate()
        }
        setTime()
        wheelTime?.apply {
            setLabels(
                mPickerOptions.label_year,
                mPickerOptions.label_month,
                mPickerOptions.label_day
                ,
                mPickerOptions.label_hours,
                mPickerOptions.label_minutes,
                mPickerOptions.label_seconds
            )
            setTextXOffset(
                mPickerOptions.x_offset_year,
                mPickerOptions.x_offset_month,
                mPickerOptions.x_offset_day,
                mPickerOptions.x_offset_hours,
                mPickerOptions.x_offset_minutes,
                mPickerOptions.x_offset_seconds
            )
            setItemsVisible(mPickerOptions.itemsVisibleCount)
            setAlphaGradient(mPickerOptions.isAlphaGradient)
            setOutSideCancelable(mPickerOptions.cancelable)
            setCyclic(mPickerOptions.cyclic)
            setDividerColor(mPickerOptions.dividerColor)
            setDividerType(mPickerOptions.dividerType)
            setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier)
            setTextColorOut(mPickerOptions.textColorOut)
            setTextColorCenter(mPickerOptions.textColorCenter)
            isCenterLabel(mPickerOptions.isCenterLabel)
        }

    }


    /**
     * 设置默认时间
     */
    fun setDate(date: Calendar?) {
        mPickerOptions.date = date
        setTime()
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private fun setRange() {
        wheelTime?.apply {
            startYear = mPickerOptions.startYear
            endYear = mPickerOptions.endYear
        }
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private fun setRangDate() {
        wheelTime?.setRangDate(mPickerOptions.startDate, mPickerOptions.endDate)
        initDefaultSelectedDate()
    }

    private fun initDefaultSelectedDate() {
        //如果手动设置了时间范围
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            //若默认时间未设置，或者设置的默认时间越界了，则设置默认选中时间为开始时间。
            if (mPickerOptions.date == null || mPickerOptions.date.timeInMillis < mPickerOptions.startDate.timeInMillis || mPickerOptions.date.timeInMillis > mPickerOptions.endDate.timeInMillis) {
                mPickerOptions.date = mPickerOptions.startDate
            }
        } else if (mPickerOptions.startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            mPickerOptions.date = mPickerOptions.startDate
        } else if (mPickerOptions.endDate != null) {
            mPickerOptions.date = mPickerOptions.endDate
        }
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private fun setTime() {
        val year: Int
        val month: Int
        val day: Int
        val hours: Int
        val minute: Int
        val seconds: Int
        val calendar = Calendar.getInstance()
        if (mPickerOptions.date == null) {
            calendar.timeInMillis = System.currentTimeMillis()
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            day = calendar[Calendar.DAY_OF_MONTH]
            hours = calendar[Calendar.HOUR_OF_DAY]
            minute = calendar[Calendar.MINUTE]
            seconds = calendar[Calendar.SECOND]
        } else {
            year = mPickerOptions.date[Calendar.YEAR]
            month = mPickerOptions.date[Calendar.MONTH]
            day = mPickerOptions.date[Calendar.DAY_OF_MONTH]
            hours = mPickerOptions.date[Calendar.HOUR_OF_DAY]
            minute = mPickerOptions.date[Calendar.MINUTE]
            seconds = mPickerOptions.date[Calendar.SECOND]
        }
        wheelTime?.setPicker(year, month, day, hours, minute, seconds)
    }


    override fun onClick(v: View) {
        when (v.tag as String) {
            TAG_SUBMIT -> returnData()
            TAG_CANCEL -> mPickerOptions.cancelListener?.onClick(v)
        }
        dismiss()
    }

    fun returnData() {
        mPickerOptions.timeSelectListener?.let {
            try {
                val date = WheelTime.dateFormat.parse(wheelTime!!.time)
                it.onTimeSelect(date, clickView)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 动态设置标题
     *
     * @param text 标题文本内容
     */
    fun setTitleText(text: String?) {
        (findViewById(R.id.tvTitle) as TextView?)?.text = text
    }

    /**
     * 目前暂时只支持设置1900 - 2100年
     *
     * @param lunar 农历的开关
     */
    fun setLunarCalendar(lunar: Boolean) {
        try {
            val calendar = Calendar.getInstance()
            calendar.time = WheelTime.dateFormat.parse(wheelTime!!.time)
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val hours = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            val seconds = calendar[Calendar.SECOND]
            wheelTime?.apply {
                isLunarMode = lunar
                setLabels(
                    mPickerOptions.label_year,
                    mPickerOptions.label_month,
                    mPickerOptions.label_day,
                    mPickerOptions.label_hours,
                    mPickerOptions.label_minutes,
                    mPickerOptions.label_seconds
                )
                setPicker(year, month, day, hours, minute, seconds)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun isLunarCalendar(): Boolean = wheelTime!!.isLunarMode

    override fun isDialog(): Boolean = mPickerOptions.isDialog
}
