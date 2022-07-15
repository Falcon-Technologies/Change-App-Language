package com.falcon.technologies.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.technologies.R
import com.falcon.technologies.adapters.LanguagePreferencesAdapter
import com.falcon.technologies.databinding.ActivityMainBinding
import com.falcon.technologies.interfaces.ICountryPrefListener
import com.falcon.technologies.model.CountryModel
import com.falcon.technologies.utils.Constants
import com.falcon.technologies.utils.PrefValues
import kotlinx.coroutines.*
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ICountryPrefListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var adapter: LanguagePreferencesAdapter
    private lateinit var item: CountryModel
    private var langPos: Int = 0
    private lateinit var pref: PrefValues
    private var tempCountryList = ArrayList<CountryModel>()
    private var filteredCountryList = ArrayList<CountryModel>()

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initControls()
    }


    private fun initControls() {
        pref = PrefValues()
        Constants.languagePos = pref.getLangPref(this, "AppLanguage")
        tempCountryList.addAll(Constants.appLanguages)
        setUpLanguageRecycler()
        loadAllLanguages()
        textWatcher()
    }

    private fun textWatcher() {
        mBinding.searchLang.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(p0: Editable?) {
                filteredCountryList.clear()
                for (i in tempCountryList.indices) {
                    if (tempCountryList[i].name.lowercase(Locale.ROOT).contains(
                            p0.toString().lowercase(
                                Locale.ROOT
                            )
                        )
                    ) {
                        filteredCountryList.add(tempCountryList[i])
                    }
                }
                adapter.setLanguagePreferences(filteredCountryList)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun setUpLanguageRecycler() {

        val favoritesLayoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.setItemViewCacheSize(10)
        mBinding.recyclerView.layoutManager =
            favoritesLayoutManager
    }

    private fun loadAllLanguages() {

        adapter = LanguagePreferencesAdapter(this, this)
        CoroutineScope(Dispatchers.IO + handler).launch {
            adapter.setLanguagePreferences(Constants.appLanguages)
            withContext(Dispatchers.Main) {
                mBinding.recyclerView.adapter = adapter
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onItemSelected(view: View, position: Int) {

        item = adapter.countryNamesList[position]

        if (!adapter.countryNamesList[position].selected) {

            langPos = position
            showLanguageDialog()
            // System.out.println("selected pos: " + position)

            val holder = mBinding.recyclerView.findViewHolderForAdapterPosition(position)
            holder?.itemView?.findViewById<View>(R.id.ivLanguageSelector)?.visibility = View.VISIBLE
            holder?.itemView?.findViewById<View>(R.id.lang_item)?.background =
                resources.getDrawable(R.drawable.selected_lang_bg, resources.newTheme())

            adapter.countryNamesList[position].selected = true

            Constants.languagePos = langPos

            adapter.countryNamesList.forEachIndexed { index, _ ->
                if (index != position) {

                    //System.out.println("non selected pos: " + index)
                    val holder1 =
                        mBinding.recyclerView.findViewHolderForAdapterPosition(
                            index
                        )
                    holder1?.itemView?.findViewById<View>(R.id.ivLanguageSelector)?.visibility =
                        View.GONE
                    holder1?.itemView?.findViewById<View>(R.id.lang_item)?.background = null

                    adapter.countryNamesList[index].selected =
                        false

                }

            }

        }

    }

    private fun setLocale(
        context: Context,
        locale: String
    ) {
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(locale))
        res.updateConfiguration(conf, dm)
    }


    @SuppressLint("SetTextI18n", "InflateParams", "UseCompatLoadingForDrawables")
    private fun showLanguageDialog() {
        val dialog =
            Dialog(this, R.style.Theme_Dialog)
        val view: View =
            layoutInflater.inflate(
                R.layout.change_lang_dialog,
                null
            )
        val restartApp =
            view.findViewById<TextView>(R.id.change)
        val cancel =
            view.findViewById<TextView>(R.id.cancel)


        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()

        restartApp.setOnClickListener {
            pref.insertLangPref(
                this,
                "AppLanguage",
                langPos
            )

            val lang =
                Constants.appLanguages[langPos]
            setLocale(this, lang.id)

            dialog.dismiss()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            finishAffinity()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
            Constants.languagePos =
                pref.getLangPref(
                    this,
                    "AppLanguage"
                )
            adapter.countryNamesList.forEachIndexed { index, _ ->
                //System.out.println("non selected pos: " + index)
                val holder =
                    mBinding.recyclerView.findViewHolderForAdapterPosition(
                        index
                    )
                holder?.itemView?.findViewById<View>(R.id.ivLanguageSelector)?.visibility =
                    View.GONE
                holder?.itemView?.findViewById<View>(R.id.lang_item)?.background = null

                adapter.countryNamesList[index].selected =
                    false

            }

            val holder =
                mBinding.recyclerView.findViewHolderForAdapterPosition(
                    pref.getLangPref(
                        this,
                        "AppLanguage"
                    )
                )

            holder?.itemView?.findViewById<View>(R.id.ivLanguageSelector)?.visibility = View.VISIBLE
            holder?.itemView?.findViewById<View>(R.id.lang_item)?.background =
                resources.getDrawable(R.drawable.selected_lang_bg, resources.newTheme())


            val prefPos = pref.getLangPref(
                this,
                "AppLanguage"
            )
            if (prefPos == -1)
                return@setOnClickListener
            adapter.countryNamesList[prefPos].selected = true
        }

    }
}