package com.pbd.psi.ui.transaction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pbd.psi.R
import com.pbd.psi.databinding.ExpenseCardBinding
import com.pbd.psi.databinding.IncomeCardBinding
import com.pbd.psi.room.Category
import com.pbd.psi.room.TransactionEntity
import java.lang.IllegalArgumentException

class TransactionViewAdapter : RecyclerView.Adapter<TransactionViewHolder>() {

    var transactionItems = arrayListOf<TransactionEntity>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var itemClickListener: ((view: View,item: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        when(viewType){
            R.layout.income_card -> return TransactionViewHolder.IncomeViewHolder(
                IncomeCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.expense_card -> return TransactionViewHolder.ExpenseViewHolder(
                ExpenseCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw  IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        when(holder){
            is TransactionViewHolder.IncomeViewHolder ->
                holder.bind(
                    transactionItems[position].id,
                    transactionItems[position].name,
                    transactionItems[position].date.toString(),
                    transactionItems[position].amount
                )
            is TransactionViewHolder.ExpenseViewHolder -> {
                holder.bind(
                    transactionItems[position].id,
                    transactionItems[position].name,
                    transactionItems[position].date.toString(),
                    transactionItems[position].amount,
                    transactionItems[position].location,
                    transactionItems[position].longitude,
                    transactionItems[position].latitude,
                )
            }
        }
    }

    override fun getItemCount(): Int = transactionItems.size

    override fun getItemViewType(position: Int): Int {
        return when(transactionItems[position].category) {
            Category.INCOME -> R.layout.income_card
            Category.EXPENSE -> R.layout.expense_card
        }
    }
}