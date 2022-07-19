package com.falcon.technologies.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.falcon.technologies.R
import com.falcon.technologies.databinding.ItemLanguagePreferencesBinding
import com.falcon.technologies.interfaces.ICountryPrefListener
import com.falcon.technologies.model.CountryModel
import com.falcon.technologies.utils.Constants


class LanguagePreferencesAdapter(
    private var listener: ICountryPrefListener,
    private val context: Context
) : RecyclerView.Adapter<LanguagePreferencesAdapter.LanguageViewHolder>() {


    var countryNamesList = mutableListOf<CountryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemBinding =
            ItemLanguagePreferencesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return LanguageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {

        val item = countryNamesList[position]
        holder.bind(item, position)

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return countryNamesList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class LanguageViewHolder(private val itemBinding: ItemLanguagePreferencesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: CountryModel, pos: Int) {

            if (Constants.languagePos == pos) {
                itemBinding.ivLanguageSelector.post {
                    itemBinding.ivLanguageSelector.visibility = View.VISIBLE
                    itemBinding.langItem.background = context.resources.getDrawable(
                        R.drawable.selected_lang_bg,
                        context.resources.newTheme()
                    )
                }
            }

            itemBinding.tvLanguage.text = item.name
            itemBinding.flag.setImageResource(item.flag)



            itemBinding.langItem.setOnClickListener {
                try {
                    listener.onItemSelected(itemBinding.ivLanguageSelector, adapterPosition)
                } catch (e: Exception) {

                }

            }

        }

    }

    fun setLanguagePreferences(country: List<CountryModel>) {
        this.countryNamesList = country.toMutableList()
    }
}