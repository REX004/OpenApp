package com.example.a01_wskpolice.View.recyclerview.session3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_wskpolice.Model.Data.session3.evidence.Evidence
import com.example.a01_wskpolice.R
import com.example.a01_wskpolice.databinding.EvidenceItemBinding

class EvidenceAdapter(val context: Context): ListAdapter<Evidence, EvidenceAdapter.Holder>(DiffCallback()) {
    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = EvidenceItemBinding.bind(view)

        fun bind(evidence: Evidence) = with(binding){
            evidenceNumber.text = "Evidence${evidence.id}"
            evidenceText.text = evidence.description
            var textClose = false
            closeBT.setOnClickListener {
                if (textClose){
                    binding.evidenceText.visibility = View.GONE
                    textClose = false
                }else {
                    binding.evidenceText.visibility = View.VISIBLE
                    textClose = true
                }
            }
        }
    }
    class DiffCallback : DiffUtil.ItemCallback<Evidence>() {
        override fun areItemsTheSame(oldItem: Evidence, newItem: Evidence): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Evidence, newItem: Evidence): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.evidence_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}