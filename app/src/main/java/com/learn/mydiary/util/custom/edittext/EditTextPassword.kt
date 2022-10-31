package com.learn.mydiary.util.custom.edittext

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.learn.mydiary.R

class EditTextPassword : AppCompatEditText, View.OnTouchListener {

    private lateinit var btnClear: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        btnClear = ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
        txtColor = ContextCompat.getColor(context, android.R.color.black)
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // DO NOTHING
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                if (s.toString().length > 5) hideError() else showError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isBtnClearClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (btnClear.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isBtnClearClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - btnClear.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isBtnClearClicked = true
                }
            }
            if (isBtnClearClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        btnClear =
                            ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        btnClear =
                            ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            }
            else return false
        }
        return false
    }

    private fun showClearButton() {
        setButtonDrawable(endOfTheText = btnClear)
    }

    private fun hideClearButton() {
        setButtonDrawable()
    }

    private fun showError() {
        txtColor = ContextCompat.getColor(context, android.R.color.holo_red_light)
        error = "Password kurang dari 6 huruf"
    }

    private fun hideError() {
        txtColor = ContextCompat.getColor(context, android.R.color.black)
    }

    private fun setButtonDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null,
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText,
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        setTextColor(txtColor)
    }

}