package ua.nanit.exchange.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.exchange.R
import ua.nanit.exchange.data.RateValue

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rates = ArrayList<RateValue>()

        for (i in 0..15) {
            rates.add(RateValue("org_$i", 1f, 100f, 5f * i, "100"))
        }

        val list = findViewById<RecyclerView>(R.id.main_list)
        val adapter = RateAdapter(rates)

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
    }

}