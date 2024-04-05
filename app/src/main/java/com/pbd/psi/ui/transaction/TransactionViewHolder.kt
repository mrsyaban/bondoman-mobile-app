package com.pbd.psi.ui.transaction

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pbd.psi.AddTransactionActivity
import com.pbd.psi.TransactionDetailActivity
import com.pbd.psi.databinding.ExpenseCardBinding
import com.pbd.psi.databinding.IncomeCardBinding
import com.pbd.psi.room.TransactionEntity

sealed class TransactionViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    var itemClickListener: ((item: Int) -> Unit)? = null
    var locationClickListener: ((latitude:Double, longitude:Double, location:String) -> Unit)? = null

    class ExpenseViewHolder(private val binding: ExpenseCardBinding) : TransactionViewHolder(binding){
        fun bind(id: Int,name: String, date: String, nominal: Int, location: String, longitude: Double, latitude: Double){
            binding.expenseName.text = name
            binding.expenseDate.text = date
            binding.expenseNominal.text = "-Rp".plus(nominal.formatDecimalSeparator())
            binding.expenseLocation.text = location
            binding.expenseLocation.setOnClickListener {
                Log.d("aku love", "lokkkkkaaasiii")
                locationClickListener?.invoke(latitude, longitude, location)
            }

            binding.root.setOnClickListener {
                itemClickListener?.invoke(id)
            }

        }
    }

    class IncomeViewHolder(private val binding: IncomeCardBinding) : TransactionViewHolder(binding) {
        fun bind(id: Int,name: String, date: String, nominal: Int){
            binding.incomeName.text = name
            binding.incomeDate.text = date
            binding.incomeNominal.text = "Rp".plus(nominal.formatDecimalSeparator())
            binding.root.setOnClickListener {
                itemClickListener?.invoke(id)
            }
        }
    }


    protected fun Int.formatDecimalSeparator(): String {
        return toString().reversed().chunked(3).joinToString(".").reversed()
    }
}