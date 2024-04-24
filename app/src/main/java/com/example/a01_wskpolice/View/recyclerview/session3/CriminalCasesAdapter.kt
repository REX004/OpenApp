package com.example.a01_wskpolice.View.recyclerview.session3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_wskpolice.Model.Data.session3.criminalCase.CriminalCase
import com.example.a01_wskpolice.R
import com.example.a01_wskpolice.View.Session3.criminalCase.CriminalCaseShowActivity
import com.example.a01_wskpolice.databinding.CriminalCaseItemBinding

class CriminalCasesAdapter(val context: Context): ListAdapter<CriminalCase, CriminalCasesAdapter.Holder>(Comparator()) {

    inner class Holder(view: View):RecyclerView.ViewHolder(view){
        private val binding = CriminalCaseItemBinding.bind(view)

        fun bind(criminalCase: CriminalCase) = with(binding){
            lastName.text = criminalCase.create_date

            card.setOnClickListener {
                val intent = Intent(context, CriminalCaseShowActivity::class.java).apply {
                    putExtra("criminalId", criminalCase.id)
                    putExtra("criminalCategory", criminalCase.category)
                    putExtra("criminalDetective", criminalCase.detective)
                    putExtra("criminalClient", criminalCase.client)
                    putExtra("criminalNumber", criminalCase.number)
                    putExtra("criminalDescription", criminalCase.description)
                    putExtra("criminalData", criminalCase.create_date)
                }
                context.startActivity(intent)
            }
        }

    }

    class Comparator: DiffUtil.ItemCallback<CriminalCase>(){
        override fun areItemsTheSame(oldItem: CriminalCase, newItem: CriminalCase): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CriminalCase, newItem: CriminalCase): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.criminal_case_item, parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

}