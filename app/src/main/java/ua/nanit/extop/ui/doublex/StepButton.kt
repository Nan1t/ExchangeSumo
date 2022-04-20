package ua.nanit.extop.ui.doublex

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger

class StepButton : CardView {

    lateinit var root: View

    lateinit var amountIn: TextView
        private set
    lateinit var amountOut: TextView
        private set
    lateinit var currencyIn: TextView
        private set
    lateinit var currencyOut: TextView
        private set
    lateinit var exchanger: TextView
        private set

    constructor(ctx: Context) : super(ctx) {
        setupView(ctx)
    }

    constructor(ctx: Context, attr: AttributeSet?) : super(ctx, attr) {
        setupView(ctx)
    }

    fun setClickListener(listener: () -> Unit) {
        root.setOnClickListener { listener() }
    }

    private fun setupView(ctx: Context) {
        root = LayoutInflater.from(ctx)
            .inflate(R.layout.view_double_step, this)

        amountIn = root.findViewById(R.id.double_step_amount_in)
        amountOut = root.findViewById(R.id.double_step_amount_out)
        currencyIn = root.findViewById(R.id.double_step_currency_in)
        currencyOut = root.findViewById(R.id.double_step_currency_out)
        exchanger = root.findViewById(R.id.double_step_exchanger)
    }

}