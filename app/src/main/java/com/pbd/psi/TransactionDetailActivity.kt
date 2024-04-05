package com.pbd.psi

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            if (trans != null) {
                displayData = trans
                updateUI(trans)
            } else {
                finish()
            }
        }
    }

    private fun updateUI(transaction: TransactionEntity) {
        binding.updateName.setText(transaction.name)
        binding.updateAmount.setText(transaction.amount.toString())
        binding.updateLat.setText(transaction.latitude.toString())
        binding.updateLon.setText(transaction.longitude.toString())
        val drawableRes = if (transaction.category == Category.EXPENSE) {
            R.drawable.expense_symbol
        } else {
            R.drawable.income_symbol
        }
        binding.updateCategory.setImageDrawable(AppCompatResources.getDrawable(applicationContext, drawableRes))
    }


    override fun onStart() {
        super.onStart()

        binding.backButtonDetail.setOnClickListener{
            finish()
        }

        binding.deleteButton.setOnClickListener {
            try {
                viewModel.deleteTransaction(displayData)
                Toast.makeText(applicationContext, "Transaksi ${displayData.name} berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Gagal menghapus transaksi", Toast.LENGTH_SHORT).show()
            }
        }
        binding.updateButton.setOnClickListener {
            val updatedName = binding.updateName.text.toString()
            val updatedNAmount = binding.updateAmount.text.toString().toInt()
            val updatedLat = binding.updateLat.text.toString().toDouble()
            val updatedLon = binding.updateLon.text.toString().toDouble()
            val transUpdated = TransactionEntity(
                displayData.id,
                updatedName,
                displayData.category,
                updatedNAmount,
                displayData.date,
                displayData.location,
                updatedLon,
                updatedLat
            )
            try {

                viewModel.updateTransaction(transUpdated)
                Toast.makeText(
                    applicationContext,
                    "Transaksi ".plus(updatedName).plus(" berhasil diubah"),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Transaksi ".plus(updatedName).plus(" gagal diubah"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}