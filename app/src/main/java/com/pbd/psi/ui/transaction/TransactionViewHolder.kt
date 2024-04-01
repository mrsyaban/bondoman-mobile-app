package com.pbd.psi.ui.transaction

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pbd.psi.databinding.ExpenseCardBinding
import com.pbd.psi.databinding.IncomeCardBinding

sealed class TransactionViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class ExpenseViewHolder(private val binding: ExpenseCardBinding) : TransactionViewHolder(binding){
        fun bind(name: String, date: String, nominal: Int, location: String, longitude: Double, latitude: Double){
            binding.expenseName.text = name
            binding.expenseDate.text = date
            binding.expenseNominal.text = "-Rp".plus(nominal.formatDecimalSeparator())
            binding.expenseLocation.text = location
            binding.expenseLocation.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:0,0?q=" + latitude.toBigDecimal().toPlainString() + "," + longitude.toBigDecimal().toPlainString() + "(" + location +")")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                val locationContext = binding.expenseLocation.context
                if (mapIntent.resolveActivity(locationContext.packageManager) != null) {
                    locationContext.startActivity(mapIntent)
                } else {
                    Toast.makeText(locationContext, "Please install google maps to access location", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    class IncomeViewHolder(private val binding: IncomeCardBinding) : TransactionViewHolder(binding) {
        fun bind(name: String, date: String, nominal: Int){
            binding.incomeName.text = name
            binding.incomeDate.text = date
            binding.incomeNominal.text = "Rp".plus(nominal.formatDecimalSeparator())
        }
    }

    protected fun Int.formatDecimalSeparator(): String {
        return toString().reversed().chunked(3).joinToString(".").reversed()
    }
}