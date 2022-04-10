package ua.nanit.extop.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.Monitoring
import ua.nanit.extop.monitoring.data.Currency
import ua.nanit.extop.monitoring.data.Rate
import java.util.concurrent.Executors

class MainViewModel(private val monitoring: Monitoring) : ViewModel() {

    companion object {
        const val PAGE_SEARCH = 0
        const val PAGE_EXCHANGERS = 1
        const val PAGE_DOUBLE_EXCHANGE = 2
        const val PAGE_SETTINGS = 3

        const val CURRENCIES_MENU_NONE = 0
        const val CURRENCIES_MENU_IN = 1
        const val CURRENCIES_MENU_OUT = 2
    }

    private val executor = Executors.newSingleThreadExecutor()

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> get() = _page

    private val _rates = MutableLiveData<List<Rate>>()
    val rates: LiveData<List<Rate>> get() = _rates

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>> get() = _currencies

    private val _currenciesMenu = MutableLiveData<Boolean>()
    val currenciesMenu: LiveData<Boolean> get() = _currenciesMenu

    private val _currencyIn = MutableLiveData<Currency>()
    val currencyIn: LiveData<Currency> get() = _currencyIn

    private val _currencyOut = MutableLiveData<Currency>()
    val currencyOut: LiveData<Currency> get() = _currencyOut

    private var currenciesMenuMode = CURRENCIES_MENU_NONE

    init {
        openSearchPage()
        refreshRates()
    }

    fun openSearchPage() {
        if (_page.value != PAGE_SEARCH)
            _page.value = PAGE_SEARCH
    }

    fun openExchangersPage() {
        if (_page.value != PAGE_EXCHANGERS)
            _page.value = PAGE_EXCHANGERS
    }

    fun openDoubleExchangePage() {
        if (_page.value != PAGE_DOUBLE_EXCHANGE)
            _page.value = PAGE_DOUBLE_EXCHANGE
    }

    fun openSettingsPage() {
        if (_page.value != PAGE_SETTINGS)
            _page.value = PAGE_SETTINGS
    }

    fun refreshRates() {
        executor.execute {
            val rates = monitoring.updateRates()

            viewModelScope.launch {
                _rates.value = rates
            }
        }
    }

    fun openCurrenciesMenu(mode: Int) {
        currenciesMenuMode = mode
        _currenciesMenu.value = !(_currenciesMenu.value ?: false)

        executor.execute {
            val currencies = monitoring.getCurrencies()

            Logger.info("Currencies: $currencies")

            viewModelScope.launch {
                _currencies.value = currencies
            }
        }
    }

    fun selectCurrency(currency: Currency) {
        when(currenciesMenuMode) {
            CURRENCIES_MENU_IN -> {
                _currencyIn.value = currency
            }
            CURRENCIES_MENU_OUT -> {
                _currencyOut.value = currency
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }

}