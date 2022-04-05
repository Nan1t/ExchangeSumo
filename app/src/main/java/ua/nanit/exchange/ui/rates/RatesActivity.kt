package ua.nanit.exchange.ui.rates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ua.nanit.exchange.R

class RatesActivity : AppCompatActivity() {

    private lateinit var viewModel: RatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        viewModel = ViewModelProvider(this, RatesVmFactory(this))
            .get(RatesViewModel::class.java)

        val currencyFrom = findViewById<AutoCompleteTextView>(R.id.main_search_currency_from)
        val currencyTo = findViewById<AutoCompleteTextView>(R.id.main_search_currency_to)
        val amountFrom = findViewById<TextInputEditText>(R.id.main_search_amount_from)
        val amountTo = findViewById<TextInputEditText>(R.id.main_search_amount_to)
        val calcCommissions = findViewById<SwitchCompat>(R.id.main_search_switch_commissions)

        val list = findViewById<RecyclerView>(R.id.main_list)
        val adapter = RateAdapter()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        viewModel.currenciesLiveData.observe(this) {

        }

        viewModel.ratesLiveData.observe(this) {
            adapter.list.clear()
            adapter.list.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

}