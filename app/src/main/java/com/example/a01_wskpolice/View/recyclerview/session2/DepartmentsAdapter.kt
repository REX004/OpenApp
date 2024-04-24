package com.example.a01_wskpolice.View.recyclerview.session2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_wskpolice.Model.Data.session2.department.Department
import com.example.a01_wskpolice.R
import com.example.a01_wskpolice.View.session2.departments.ShowDepartmentActivity
import com.example.a01_wskpolice.databinding.DepartmentsItemBinding

class DepartmentsAdapter(val context: Context) : ListAdapter<Department, DepartmentsAdapter.Holder>(
    Comparator()
){
    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = DepartmentsItemBinding.bind(view)

        fun bind(department: Department) = with(binding){
            name.text = department.name
            address.text = department.address

            card.setOnClickListener {
                val intent = Intent(context, ShowDepartmentActivity::class.java).apply {
                    putExtra("departmentId", department.id)
                    putExtra("departmentAddress", department.address)
                    putExtra("departmentBoss", department.boss)
                    putExtra("departmentName", department.name)
                    putExtra("departmentPhone", department.phone)
                    putExtra("departmentEmail", department.email)
                    putExtra("departmentDescription", department.description)
                }
                context.startActivity(intent)
                }

            }
        }


    class Comparator: DiffUtil.ItemCallback<Department>(){
        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.departments_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}
