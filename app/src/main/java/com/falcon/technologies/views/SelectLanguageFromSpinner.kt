package com.falcon.technologies.views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.falcon.technologies.R
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

        val country_list = arrayListOf<CountryData>()

        country_list.add(CountryData("India"))
        country_list.add(CountryData("United States"))
        country_list.add(CountryData("Indonesia"))
        country_list.add(CountryData("Pak"))
        country_list.add(CountryData("China"))
        country_list.add(CountryData("India"))
        country_list.add(CountryData("United States"))
        country_list.add(CountryData("Indonesia"))
        country_list.add(CountryData("France"))
        country_list.add(CountryData("China"))
        country_list.add(CountryData("India"))
        country_list.add(CountryData("United States"))
        country_list.add(CountryData("Indonesia"))
        country_list.add(CountryData("France"))
        country_list.add(CountryData("China"))
        country_list.add(CountryData("India"))
        country_list.add(CountryData("United States"))
        country_list.add(CountryData("Indonesia"))
        country_list.add(CountryData("France"))
        country_list.add(CountryData("China"))

        val adapter = CustomSpinnerAdapter(this, country_list)



        binding.asd.setAdapter(adapter)

        binding.asd.setText(country_list[3].countryName, false)

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

        country_list.add(CountryData("Japan"))
        country_list.add(CountryData("New Zealand"))
        country_list.add(CountryData("Other"))

        adapter.notifyDataSetChanged()
    }
    fun TextView.leftDrawable(@DrawableRes id: Int = 0 ) {
        this.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
    }
}