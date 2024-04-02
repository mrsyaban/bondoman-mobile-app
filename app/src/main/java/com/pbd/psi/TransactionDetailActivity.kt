package com.pbd.psi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.pbd.psi.databinding.ActivityTransactionDetailBinding
import com.pbd.psi.room.TransactionEntity
import com.pbd.psi.ui.transaction_detail.TransactionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailActivity : AppCompatActivity() {

    private val viewModel: TransactionDetailViewModel by viewModels()
    private lateinit var transactionInfo:LiveData<TransactionEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val itemId = intent.getIntExtra("id", -1)
        transactionInfo = viewModel.getTransById(itemId)
        transactionInfo.observe(this, Observer { trans ->
            binding.updateName.setText(trans.name)
        })
    }

}