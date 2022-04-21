package ua.nanit.extop.ui.doublerate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.nanit.extop.R
import ua.nanit.extop.ui.base.BaseBottomSheet

class DoubleRateBottomSheet : BaseBottomSheet() {

    companion object {
        const val TAG = "DoubleExchangeBottomSheet"
    }

    var firstAmountIn: String? = null
    var firstAmountOut: String? = null
    var firstCurrencyIn: String? = null
    var firstCurrencyOut: String? = null
    var firstExchanger: String? = null

    var secondAmountIn: String? = null
    var secondAmountOut: String? = null
    var secondCurrencyIn: String? = null
    var secondCurrencyOut: String? = null
    var secondExchanger: String? = null

    var firstStepClick: () -> Unit = {}
    var secondStepClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsheet_double, container, false)

        val btnStepFirst = view.findViewById<StepButton>(R.id.double_btn_first)
        val btnStepSecond = view.findViewById<StepButton>(R.id.double_btn_second)

        btnStepFirst.amountIn.text = firstAmountIn
        btnStepFirst.amountOut.text = firstAmountOut
        btnStepFirst.currencyIn.text = firstCurrencyIn
        btnStepFirst.currencyOut.text = firstCurrencyOut
        btnStepFirst.exchanger.text = firstExchanger

        btnStepSecond.amountIn.text = secondAmountIn
        btnStepSecond.amountOut.text = secondAmountOut
        btnStepSecond.currencyIn.text = secondCurrencyIn
        btnStepSecond.currencyOut.text = secondCurrencyOut
        btnStepSecond.exchanger.text = secondExchanger

        btnStepFirst.setClickListener(firstStepClick)
        btnStepSecond.setClickListener(secondStepClick)

        return view
    }

    override fun tag(): String {
        return TAG
    }
}