package com.tuann.floatingactionbuttonexpandable

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class FloatingActionButtonExpandable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        private const val DURATION_DEFAULT = 100
    }

    private var expanded = false
    private val root: View = LayoutInflater.from(context)
        .inflate(R.layout.view_floating_action_button, this, true)
    private val cardView: CardView
    private val buttonLayout: LinearLayout
    private val ivIcon: ImageView
    private val tvContent: TextView
    private val toggle: Transition
    private var duration = 0L

    init {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.FloatingActionButtonExpandable, 0, 0
        )
        val content = arr.getString(R.styleable.FloatingActionButtonExpandable_fab_content)
        val icon = arr.getDrawable(R.styleable.FloatingActionButtonExpandable_fab_icon)
        val textColor = arr.getColor(
            R.styleable.FloatingActionButtonExpandable_fab_text_color,
            ContextCompat.getColor(context, android.R.color.white)
        )
        val textSize = arr.getDimensionPixelSize(
            R.styleable.FloatingActionButtonExpandable_fab_text_size,
            resources.getDimensionPixelSize(R.dimen.text_size_action_button_default)
        )
        duration = arr.getInteger(
            R.styleable.FloatingActionButtonExpandable_fab_duration,
            DURATION_DEFAULT
        ).toLong()
        var paddingTextIcon = arr.getDimensionPixelSize(
            R.styleable.FloatingActionButtonExpandable_fab_padding_text_icon,
            resources.getDimensionPixelSize(R.dimen.padding_text_icon_default)
        )
        if (content.isNullOrEmpty() || icon == null) {
            paddingTextIcon = 0
        }
        val bgColor = arr.getColor(
            R.styleable.FloatingActionButtonExpandable_fab_bg_color, ContextCompat.getColor(
                context,
                R.color.bg_float_action_default
            )
        )
        val fabPadding = arr.getDimensionPixelSize(
            R.styleable.FloatingActionButtonExpandable_fab_padding,
            resources.getDimensionPixelSize(R.dimen.padding_fab_default)
        )
        val expanded = arr.getBoolean(R.styleable.FloatingActionButtonExpandable_fab_expanded, true)
        arr.recycle()

        ivIcon = root.findViewById(R.id.icon)
        tvContent = root.findViewById(R.id.content)
        cardView = root.findViewById(R.id.cardView)
        buttonLayout = root.findViewById(R.id.buttonLayout)

        cardView.setCardBackgroundColor(bgColor)

        buttonLayout.setPadding(fabPadding, fabPadding, fabPadding, fabPadding)

        ivIcon.setImageDrawable(icon)

        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        tvContent.text = content
        tvContent.setTextColor(textColor)
        tvContent.setPadding(paddingTextIcon, 0, 0, 0)

        toggle = TransitionInflater.from(context)
            .inflateTransition(R.transition.float_action_button_toggle)
        setExpanded(expanded)

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                cardView.radius = height.toFloat()
            }
        })
    }

    fun expand(anim: Boolean = true) {
        if (expanded) return
        expanded = true
        execute(anim)
    }

    fun collapse(anim: Boolean = true) {
        if (!expanded) return
        expanded = false
        execute(anim)
    }

    fun toggle(anim: Boolean = true) {
        expanded = !expanded
        execute(anim)
    }

    private fun execute(anim: Boolean = true) {
        toggle.duration = if (anim) duration else 0
        TransitionManager.beginDelayedTransition(root.parent as ViewGroup, toggle)
        tvContent.visibility = if (expanded) View.VISIBLE else View.GONE
        ivIcon.isActivated = expanded
    }

    fun setDuration(duration: Long) {
        this.duration = duration
    }

    fun setContent(content: String) {
        tvContent.text = content
    }

    fun setIconActionButton(drawable: Drawable) {
        ivIcon.setImageDrawable(drawable)
    }

    fun setIconActionButton(resId: Int) {
        ivIcon.setImageResource(resId)
    }

    fun setIconActionButton(bitmap: Bitmap) {
        ivIcon.setImageBitmap(bitmap)
    }

    fun setTextColor(color: Int) {
        tvContent.setTextColor(color)
    }

    fun setBackgroundButtonColor(color: Int) {
        cardView.setCardBackgroundColor(color)
    }

    fun setPaddingTextIcon(padding: Int) {
        tvContent.setPadding(padding, 0, 0, 0)
    }

    fun setTextSize(size: Float) {
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setTextSize(unit: Int, size: Float) {
        tvContent.setTextSize(unit, size)
    }

    fun setPaddingInsideButton(padding: Int) {
        buttonLayout.setPadding(padding, padding, padding, padding)
    }

    fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
        tvContent.visibility = if (expanded) View.VISIBLE else View.GONE
        ivIcon.isActivated = expanded
    }

    override fun onSaveInstanceState(): Parcelable {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.expanded = expanded
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            if (expanded != state.expanded) {
                toggle()
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