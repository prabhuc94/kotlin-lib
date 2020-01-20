package com.wee3ventures.attendancecalendar

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.wee3ventures.fontier.FontView
import java.text.DateFormatSymbols
import java.util.*

class CalendarView : LinearLayout {
    companion object {
        private const val DAY_OF_THE_WEEK_TEXT = "dayOfTheWeekText"
        private const val DAY_OF_THE_WEEK_LAYOUT = "dayOfTheWeekLayout"
        private const val DAY_OF_THE_MONTH_LAYOUT = "dayOfTheMonthLayout"
        private const val DAY_OF_THE_MONTH_TEXT = "dayOfTheMonthText"
        private const val DAY_OF_THE_MONTH_BACKGROUND = "dayOfTheMonthBackground"
        private const val DAY_OF_THE_MONTH_CIRCLE_IMAGE_1 = "dayOfTheMonthCircleImage1"
        private const val DAY_OF_THE_MONTH_CIRCLE_IMAGE_2 = "dayOfTheMonthCircleImage2"
    }

    private var dateTitle: TextView? = null
    private var leftButton: ImageView? = null
    private var rightButton: ImageView? = null
    private var rootViews : View? = null
    private var shortWeekDays = false
    private var robotoCalendarMonthLayout: ViewGroup? = null

    @NonNull
    private var currentCalendar = Calendar.getInstance()
    @Nullable
    private var lastSelectedDayCalendar: Calendar? = null

    private var calendarListener : CalendarListener ?= null


    interface CalendarListener {
        fun onDayClick(date: Date?)
        fun onDayLongClick(date: Date?)
        fun onRightButtonClick()
        fun onLeftButtonClick()
    }

    private val onDayOfMonthClickListener = OnClickListener { view ->
            // Extract day selected
            val dayOfTheMonthContainer = view as ViewGroup
            var tagId = dayOfTheMonthContainer.tag as String
            tagId = tagId.substring(
                CalendarView.DAY_OF_THE_MONTH_LAYOUT.length,
                tagId.length
            )
            val dayOfTheMonthText =
                view.findViewWithTag<TextView>(CalendarView.DAY_OF_THE_MONTH_TEXT + tagId)
            // Extract the day from the text
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = currentCalendar[Calendar.YEAR]
            calendar[Calendar.MONTH] = currentCalendar[Calendar.MONTH]
            calendar[Calendar.DAY_OF_MONTH] = Integer.valueOf(dayOfTheMonthText.text.toString())
            markDayAsSelectedDay(calendar.time)
            // Fire event
            checkNotNull(calendarListener) { "You must assign a valid Calendar Listener first!" }
            calendarListener?.onDayClick(calendar.time)
        }

    private val onDayOfMonthLongClickListener = OnLongClickListener { view ->
            // Extract day selected
            val dayOfTheMonthContainer = view as ViewGroup
            var tagId = dayOfTheMonthContainer.tag as String
            tagId = tagId.substring(DAY_OF_THE_MONTH_LAYOUT.length, tagId.length)
            val dayOfTheMonthText =
                view.findViewWithTag<TextView>(DAY_OF_THE_MONTH_TEXT + tagId)
            // Extract the day from the text
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = currentCalendar[Calendar.YEAR]
            calendar[Calendar.MONTH] = currentCalendar[Calendar.MONTH]
            calendar[Calendar.DAY_OF_MONTH] = Integer.valueOf(dayOfTheMonthText.text.toString())
            markDayAsSelectedDay(calendar.time)
            // Fire event
            checkNotNull(calendarListener) { "You must assign a valid RobotoCalendarListener first!" }
            calendarListener?.onDayLongClick(calendar.time)
            true
        }

    constructor(context: Context?) : super(context) { init(null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init(attrs) }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init(attrs) }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) { init(attrs) }


    private fun init(set: AttributeSet?) {
        if (isInEditMode) {
            return
        }
        val inflate =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        rootViews = inflate.inflate(R.layout.layout_attendance_front, this, true)
        findViewsById(rootView!!)
        setUpEventListeners()
        currentCalendar = Calendar.getInstance()
        setDate(currentCalendar.time)
    }

    private fun setUpEventListeners() {
        leftButton!!.setOnClickListener { view: View? ->
            checkNotNull(calendarListener) { "You must assign a valid RobotoCalendarListener first!" }
            // Decrease month
            currentCalendar.add(Calendar.MONTH, -1)
            lastSelectedDayCalendar = null
            updateView()
            calendarListener?.onLeftButtonClick()
        }
        rightButton!!.setOnClickListener { view: View? ->
            checkNotNull(calendarListener) { "You must assign a valid RobotoCalendarListener first!" }
            // Increase month
            currentCalendar.add(Calendar.MONTH, 1)
            lastSelectedDayCalendar = null
            updateView()
            calendarListener?.onRightButtonClick()
        }
    }

    private fun findViewsById(view: View) {
        robotoCalendarMonthLayout = view.findViewById(R.id.robotoCalendarDateTitleContainer)
        leftButton = view.findViewById(R.id.leftButton)
        rightButton = view.findViewById(R.id.rightButton)
        dateTitle = view.findViewById(R.id.monthText)
        val inflate =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (i in 0..41) {
            val weekIndex = i % 7 + 1
            val dayOfTheWeekLayout =
                view.findViewWithTag<ViewGroup>(DAY_OF_THE_WEEK_LAYOUT + weekIndex)
            // Create day of the month
            val dayOfTheMonthLayout: View =
                inflate.inflate(R.layout.layout_calendar_day_of_the_month, null)
            val dayOfTheMonthText =
                dayOfTheMonthLayout.findViewWithTag<View>(DAY_OF_THE_MONTH_TEXT)
            val dayOfTheMonthBackground =
                dayOfTheMonthLayout.findViewWithTag<View>(DAY_OF_THE_MONTH_BACKGROUND)
            val dayOfTheMonthCircleImage1 =
                dayOfTheMonthLayout.findViewWithTag<View>(DAY_OF_THE_MONTH_CIRCLE_IMAGE_1)
            val dayOfTheMonthCircleImage2 =
                dayOfTheMonthLayout.findViewWithTag<View>(DAY_OF_THE_MONTH_CIRCLE_IMAGE_2)
            // Set tags to identify them
            val viewIndex = i + 1
            dayOfTheMonthLayout.tag = DAY_OF_THE_MONTH_LAYOUT + viewIndex
            dayOfTheMonthText.tag = DAY_OF_THE_MONTH_TEXT + viewIndex
            dayOfTheMonthBackground.tag = DAY_OF_THE_MONTH_BACKGROUND + viewIndex
            dayOfTheMonthCircleImage1.tag = DAY_OF_THE_MONTH_CIRCLE_IMAGE_1 + viewIndex
            dayOfTheMonthCircleImage2.tag = DAY_OF_THE_MONTH_CIRCLE_IMAGE_2 + viewIndex
            dayOfTheWeekLayout.addView(dayOfTheMonthLayout)
        }
    }

    fun clearSelectedDay() {
        if (lastSelectedDayCalendar != null) {
            val dayOfTheMonthBackground: ViewGroup = getDayOfMonthBackground(lastSelectedDayCalendar!!)!!
            // If it's today, keep the current day style
            val nowCalendar = Calendar.getInstance()
            if (nowCalendar[Calendar.YEAR] == lastSelectedDayCalendar!![Calendar.YEAR] && nowCalendar[Calendar.DAY_OF_YEAR] == lastSelectedDayCalendar!![Calendar.DAY_OF_YEAR]
            ) {
                dayOfTheMonthBackground.setBackgroundResource(R.drawable.ring)
            } else {
                dayOfTheMonthBackground.setBackgroundResource(android.R.color.transparent)
            }
            val dayOfTheMonth: FontView = getDayOfMonthText(lastSelectedDayCalendar!!)!!
            dayOfTheMonth.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.roboto_calendar_day_of_the_month_font
                )
            )
            val circleImage1: ImageView = getCircleImage1(lastSelectedDayCalendar!!)!!
            val circleImage2: ImageView = getCircleImage2(lastSelectedDayCalendar!!)!!
            if (circleImage1.visibility == View.VISIBLE) {
                DrawableCompat.setTint(
                    circleImage1.drawable,
                    ContextCompat.getColor(context, R.color.roboto_calendar_circle_1)
                )
            }
            if (circleImage2.visibility == View.VISIBLE) {
                DrawableCompat.setTint(
                    circleImage2.drawable,
                    ContextCompat.getColor(context, R.color.roboto_calendar_circle_2)
                )
            }
        }
    }

    fun markDayAsSelectedDay(@NonNull date: Date?) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        // Clear previous current day mark
        clearSelectedDay()
        // Store current values as last values
        lastSelectedDayCalendar = calendar
        // Mark current day as selected
        val dayOfTheMonthBackground: ViewGroup = getDayOfMonthBackground(calendar)!!
        dayOfTheMonthBackground.setBackgroundResource(R.drawable.circle)
        val dayOfTheMonth: FontView = getDayOfMonthText(calendar)!!
        dayOfTheMonth.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.roboto_calendar_selected_day_font
            )
        )
        val circleImage1: ImageView = getCircleImage1(calendar)!!
        val circleImage2: ImageView = getCircleImage2(calendar)!!
        if (circleImage1.visibility == View.VISIBLE) {
            DrawableCompat.setTint(
                circleImage1.drawable,
                ContextCompat.getColor(context, R.color.roboto_calendar_selected_day_font)
            )
        }
        if (circleImage2.visibility == View.VISIBLE) {
            DrawableCompat.setTint(
                circleImage2.drawable,
                ContextCompat.getColor(context, R.color.roboto_calendar_selected_day_font)
            )
        }
    }

    private fun setUpMonthLayout() {
        var dateText =
            DateFormatSymbols(Locale.getDefault()).months[currentCalendar[Calendar.MONTH]]
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length)
        val calendar = Calendar.getInstance()
        if (currentCalendar[Calendar.YEAR] == calendar[Calendar.YEAR]) {
            dateTitle!!.text = dateText
        } else {
            dateTitle!!.text = String.format(
                "%s %s",
                dateText,
                currentCalendar[Calendar.YEAR]
            )
        }
    }

    private fun setUpWeekDaysLayout() {
        var dayOfWeek: TextView
        var dayOfTheWeekString: String
        val weekDaysArray =
            DateFormatSymbols(Locale.getDefault()).weekdays
        val length = weekDaysArray.size
        for (i in 1 until length) {
            dayOfWeek = rootView!!.findViewWithTag(DAY_OF_THE_WEEK_TEXT + getWeekIndex(i, currentCalendar))
            dayOfTheWeekString = weekDaysArray[i]
            dayOfTheWeekString = if (shortWeekDays) {
                checkSpecificLocales(dayOfTheWeekString, i)!!
            } else {
                dayOfTheWeekString.substring(0, 1).toUpperCase() + dayOfTheWeekString.substring(
                    1,
                    3
                )
            }
            dayOfWeek.text = dayOfTheWeekString
        }
    }

    private fun setUpDaysOfMonthLayout() {
        var dayOfTheMonthText: TextView
        var circleImage1: View
        var circleImage2: View
        var dayOfTheMonthContainer: ViewGroup
        var dayOfTheMonthBackground: ViewGroup
        for (i in 1..42) {
            dayOfTheMonthContainer =
                rootView!!.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + i)
            dayOfTheMonthBackground =
                rootView!!.findViewWithTag(DAY_OF_THE_MONTH_BACKGROUND + i)
            dayOfTheMonthText =
                rootView!!.findViewWithTag(DAY_OF_THE_MONTH_TEXT + i)
            circleImage1 =
                rootView!!.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_1 + i)
            circleImage2 =
                rootView!!.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_2 + i)
            dayOfTheMonthText.visibility = View.INVISIBLE
            circleImage1.visibility = View.GONE
            circleImage2.visibility = View.GONE
            // Apply styles
            dayOfTheMonthText.setBackgroundResource(android.R.color.transparent)
            dayOfTheMonthText.setTypeface(null, Typeface.NORMAL)
            dayOfTheMonthText.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.roboto_calendar_day_of_the_month_font
                )
            )
            dayOfTheMonthContainer.setBackgroundResource(android.R.color.transparent)
            dayOfTheMonthContainer.setOnClickListener(null)
            dayOfTheMonthBackground.setBackgroundResource(android.R.color.transparent)
        }
    }

    private fun checkSpecificLocales(dayOfTheWeekString: String, i: Int): String? { // Set Wednesday as "X" in Spanish Locale.getDefault()
        var dayOfTheWeekString = dayOfTheWeekString
        dayOfTheWeekString = if (i == 4 && "ES" == Locale.getDefault().country) {
            "X"
        } else {
            dayOfTheWeekString.substring(0, 1).toUpperCase()
        }
        return dayOfTheWeekString
    }

    private fun setUpDaysInCalendar() {
        val auxCalendar =
            Calendar.getInstance(Locale.getDefault())
        auxCalendar.time = currentCalendar.time
        auxCalendar[Calendar.DAY_OF_MONTH] = 1
        val firstDayOfMonth = auxCalendar[Calendar.DAY_OF_WEEK]
        var dayOfTheMonthText: TextView?
        var dayOfTheMonthContainer: ViewGroup
        var dayOfTheMonthLayout: ViewGroup
        // Calculate dayOfTheMonthIndex
        var dayOfTheMonthIndex: Int = getWeekIndex(firstDayOfMonth, auxCalendar)
        run {
            var i = 1
            while (i <= auxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                dayOfTheMonthContainer =
                    rootView!!.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + dayOfTheMonthIndex)
                dayOfTheMonthText =
                    rootView!!.findViewWithTag(DAY_OF_THE_MONTH_TEXT + dayOfTheMonthIndex)
                if (dayOfTheMonthText == null) {
                    break
                }
                dayOfTheMonthContainer.setOnClickListener(onDayOfMonthClickListener)
                dayOfTheMonthContainer.setOnLongClickListener(onDayOfMonthLongClickListener)
                dayOfTheMonthText!!.visibility = View.VISIBLE
                dayOfTheMonthText!!.text = i.toString()
                i++
                dayOfTheMonthIndex++
            }
        }
        for (i in 36..42) {
            dayOfTheMonthText =
                rootView!!.findViewWithTag(DAY_OF_THE_MONTH_TEXT + i)
            dayOfTheMonthLayout =
                rootView!!.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + i)
            if (dayOfTheMonthText?.visibility == View.INVISIBLE) {
                dayOfTheMonthLayout.visibility = View.GONE
            } else {
                dayOfTheMonthLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun markDayAsCurrentDay() { // If it's the current month, mark current day
        val nowCalendar = Calendar.getInstance()
        if (nowCalendar[Calendar.YEAR] == currentCalendar[Calendar.YEAR] && nowCalendar[Calendar.MONTH] == currentCalendar[Calendar.MONTH]
        ) {
            val currentCalendar = Calendar.getInstance()
            currentCalendar.time = nowCalendar.time
            val dayOfTheMonthBackground = getDayOfMonthBackground(currentCalendar)
            dayOfTheMonthBackground!!.setBackgroundResource(R.drawable.ring)
        }
    }

    private fun updateView() {
        setUpMonthLayout()
        setUpWeekDaysLayout()
        setUpDaysOfMonthLayout()
        setUpDaysInCalendar()
        markDayAsCurrentDay()
    }

    /**
     * Set an specific calendar to the view and update de view
     *
     * @param date, the selected date
     */
    fun setDate(@NonNull date: Date?) {
        currentCalendar.time = date
        updateView()
    }

    @NonNull
    fun getDate(): Date? {
        return currentCalendar.time
    }

    @Nullable
    fun getSelectedDay(): Date? {
        return lastSelectedDayCalendar!!.time
    }

    private fun getDayOfMonthBackground(currentCalendar: Calendar): ViewGroup? {
        return getView(DAY_OF_THE_MONTH_BACKGROUND, currentCalendar) as ViewGroup?
    }

    private fun getDayOfMonthText(currentCalendar: Calendar): FontView? {
        return getView(DAY_OF_THE_MONTH_TEXT, currentCalendar) as FontView?
    }

    private fun getCircleImage1(currentCalendar: Calendar): ImageView? {
        return getView(DAY_OF_THE_MONTH_CIRCLE_IMAGE_1, currentCalendar) as ImageView?
    }

    private fun getCircleImage2(currentCalendar: Calendar): ImageView? {
        return getView(DAY_OF_THE_MONTH_CIRCLE_IMAGE_2, currentCalendar) as ImageView?
    }

    private fun getView(key: String, currentCalendar: Calendar): View? {
        val index: Int = getDayIndexByDate(currentCalendar)
        return rootView!!.findViewWithTag(key + index)
    }

    private fun getDayIndexByDate(currentCalendar: Calendar): Int {
        val monthOffset: Int = getMonthOffset(currentCalendar)
        val currentDay = currentCalendar[Calendar.DAY_OF_MONTH]
        return currentDay + monthOffset
    }

    private fun getMonthOffset(currentCalendar: Calendar): Int {
        val calendar = Calendar.getInstance()
        calendar.time = currentCalendar.time
        calendar[Calendar.DAY_OF_MONTH] = 1
        val firstDayWeekPosition = calendar.firstDayOfWeek
        val dayPosition = calendar[Calendar.DAY_OF_WEEK]
        return if (firstDayWeekPosition == 1) {
            dayPosition - 1
        } else {
            if (dayPosition == 1) {
                6
            } else {
                dayPosition - 2
            }
        }
    }

    private fun getWeekIndex(weekIndex: Int, currentCalendar: Calendar): Int {
        val firstDayWeekPosition = currentCalendar.firstDayOfWeek
        return if (firstDayWeekPosition == 1) {
            weekIndex
        } else {
            if (weekIndex == 1) {
                7
            } else {
                weekIndex - 1
            }
        }
    }

    fun setShortWeekDays(shortWeekDays: Boolean) {
        this.shortWeekDays = shortWeekDays
    }

    private fun areInTheSameDay(@NonNull calendarOne: Calendar, @NonNull calendarTwo: Calendar): Boolean {
        return calendarOne[Calendar.YEAR] == calendarTwo[Calendar.YEAR] && calendarOne[Calendar.DAY_OF_YEAR] == calendarTwo[Calendar.DAY_OF_YEAR]
    }
}