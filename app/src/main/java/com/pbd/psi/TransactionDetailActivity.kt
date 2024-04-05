package com.pbd.psi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LiveData
import com.pbd.psi.databinding.ActivityTransactionDetailBinding
import com.pbd.psi.room.Category
import com.pbd.psi.room.TransactionEntity
import com.pbd.psi.ui.transaction_detail.TransactionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailActivity : AppCompatActivity() {
    private lateinit var displayData: TransactionEntity
    private lateinit var binding: ActivityTransactionDetailBinding
    private val viewModel: TransactionDetailViewModel by viewModels()
    private lateinit var transactionInfo:LiveData<TransactionEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val itemId = intent.getIntExtra("id", -1)
        transactionInfo = viewModel.getTransById(itemId)
        transactionInfo.observe(this) { trans ->
            displayData = trans
            binding.updateName.setText(trans.name)
            binding.updateAmount.setText(trans.amount)
            binding.updateLocation.setText(trans.location)
            if (trans.category == Category.EXPENSE) {
                binding.updateCategory.setImageDrawable(
                    AppCompatResources.getDrawable(
                        applicationContext, R.drawable.expense_symbol
                    )
                )
            } else {
                binding.updateCategory.setImageDrawable(
                    AppCompatResources.getDrawable(
                        applicationContext, R.drawable.income_symbol
                    )
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.backButtonDetail.setOnClickListener{
            finish()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteTransaction(displayData)
        }
    }

}