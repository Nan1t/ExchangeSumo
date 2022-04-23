package ua.nanit.extop.ui.doublerate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import ua.nanit.extop.databinding.ViewDoubleStepBinding

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
        val binding = ViewDoubleStepBinding.inflate(LayoutInflater.from(ctx), this, true)

        amountIn = binding.doubleStepAmountIn
        amountOut = binding.doubleStepAmountOut
        currencyIn = binding.doubleStepCurrencyIn
        currencyOut = binding.doubleStepCurrencyOut
        exchanger = binding.doubleStepExchanger

        root = binding.root
    }

}