package com.tuann.floatactionbuttonexpandable

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.tuann.floatactionbuttoncustom.R

class FloatActionButtonExpandable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        private const val DURATION_DEFAULT = 100
    }

    private var expanded = false
    private val cardView: CardView
    private val root: View = LayoutInflater.from(context)
        .inflate(R.layout.view_float_action_button, this, true)
    private val ivIcon: ImageView
    private val tvContent: TextView
    private val toggle: Transition
    private var duration = 0L

    init {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.FloatActionButtonExpandable, 0, 0
        )
        val content = arr.getString(R.styleable.FloatActionButtonExpandable_contentActionButton)
        val icon = arr.getDrawable(R.styleable.FloatActionButtonExpandable_iconActionButton)
        val textColor = arr.getColor(
            R.styleable.FloatActionButtonExpandable_textColorActionButton,
            ContextCompat.getColor(context, android.R.color.white)
        )
        val textSize = arr.getDimensionPixelSize(
            R.styleable.FloatActionButtonExpandable_textSize,
            resources.getDimensionPixelSize(R.dimen.text_size_action_button_default)
        )
        duration = arr.getInteger(
            R.styleable.FloatActionButtonExpandable_duration,
            DURATION_DEFAULT
        ).toLong()
        val paddingTextIcon = arr.getDimensionPixelSize(
            R.styleable.FloatActionButtonExpandable_paddingTextIcon,
            R.dimen.padding_text_icon_default
        )
        val bgColor = arr.getColor(
            R.styleable.FloatActionButtonExpandable_bgColor, ContextCompat.getColor(
                context,
                R.color.bg_float_action_default
            )
        )
        arr.recycle()

        ivIcon = root.findViewById(R.id.icon)
        tvContent = root.findViewById(R.id.content)
        cardView = root.findViewById(R.id.cardView)
        cardView.setCardBackgroundColor(bgColor)
        ivIcon.setImageDrawable(icon)
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        tvContent.text = content
        tvContent.setTextColor(textColor)
        tvContent.setPadding(paddingTextIcon, 0, 0, 0)
        toggle = TransitionInflater.from(context)
            .inflateTransition(R.transition.float_action_button_toggle)
        root.setOnClickListener {
            toggleExpanded()
        }

        post {
            cardView.radius = height.toFloat()
        }
    }

    private fun toggleExpanded() {
        expanded = !expanded
        toggle.duration = duration
        TransitionManager.beginDelayedTransition(root.parent as ViewGroup, toggle)
        tvContent.visibility = if (expanded) View.VISIBLE else View.GONE
        ivIcon.isActivated = expanded
    }

    fun expand() {
        if (expanded) return
        toggleExpanded()
    }

    fun collapse() {
        if (!expanded) return
        toggleExpanded()
    }

    override fun onSaveInstanceState(): Parcelable {
        val savedState =
            SavedState(super.onSaveInstanceState())
        savedState.expanded = expanded
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            if (expanded != state.expanded) {
                toggleExpanded()
            }
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    internal class SavedState : BaseSavedState {
        var expanded = false

        constructor(source: Parcel) : super(source) {
            expanded = source.readByte().toInt() != 0
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeByte((if (expanded) 1 else 0).toByte())
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}