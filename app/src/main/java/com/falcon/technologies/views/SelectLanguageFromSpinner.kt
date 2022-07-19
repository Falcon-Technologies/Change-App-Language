package com.falcon.technologies.views

import android.os.Bundle
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.falcon.technologies.adapters.CustomSpinnerAdapter
import com.falcon.technologies.databinding.ActivitySelectLanguageFromSpinnerBinding
import com.falcon.technologies.model.CountryData


class SelectLanguageFromSpinner : AppCompatActivity() {
    private lateinit var binding: ActivitySelectLanguageFromSpinnerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLanguageFromSpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCustomAdapterSpinner()
    }

    fun setCustomAdapterSpinner() {

        val country_lists = arrayListOf<CountryData>()

        country_lists.add(CountryData("India"))
        country_lists.add(CountryData("United States"))
        country_lists.add(CountryData("Indonesia"))
        country_lists.add(CountryData("Pak"))
        country_lists.add(CountryData("China"))
        country_lists.add(CountryData("India"))
        country_lists.add(CountryData("United States"))
        country_lists.add(CountryData("Indonesia"))
        country_lists.add(CountryData("France"))
        country_lists.add(CountryData("China"))
        country_lists.add(CountryData("India"))
        country_lists.add(CountryData("United States"))
        country_lists.add(CountryData("Indonesia"))
        country_lists.add(CountryData("France"))
        country_lists.add(CountryData("China"))
        country_lists.add(CountryData("India"))
        country_lists.add(CountryData("United States"))
        country_lists.add(CountryData("Indonesia"))
        country_lists.add(CountryData("France"))
        country_lists.add(CountryData("China"))

        val adapter = CustomSpinnerAdapter(this, country_lists)



        binding.asd.setAdapter(adapter)

        binding.asd.setText(country_lists[3].countryName, false)

        binding.asd.onItemClickListener=
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                Toast.makeText(
                    this@SelectLanguageFromSpinner,
                    "sohaib mughal",
                    Toast.LENGTH_SHORT
                ).show()
                binding.asd.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_idle_alarm , 0, 0, 0);

            }

        // dynamically adding data after setting adapter to spinner

        country_lists.add(CountryData("Japan"))
        country_lists.add(CountryData("New Zealand"))
        country_lists.add(CountryData("Other"))

        adapter.notifyDataSetChanged()
    }
    fun TextView.leftDrawable(@DrawableRes id: Int = 0 ) {
        this.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
    }
}