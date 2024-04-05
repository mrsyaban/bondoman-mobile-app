package com.pbd.psi

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pbd.psi.databinding.ActivityAddTransactionBinding
import com.pbd.psi.room.Category
import com.pbd.psi.ui.add_transaction.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    private val viewModel: AddTransactionViewModel by viewModels()


    private lateinit var autoComplete: AutoCompleteTextView
    val categories = listOf("Pengeluaran", "Pemasukan")

//    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoComplete = binding.categorySelector
        val adapter = ArrayAdapter(this, R.layout.list_category, categories)
        autoComplete.setAdapter(adapter)


//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStart() {
        super.onStart()

        lateinit var categorySelected: Any

        binding.backButtonAdd.setOnClickListener{
            finish()
        }

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
                categorySelected= adapterView.getItemAtPosition(i)

        }

        binding.submitButton.setOnClickListener {
            var inputCategory = Category.INCOME

            if (categorySelected == "Pengeluaran") {
                inputCategory = Category.EXPENSE
            }
            val inputName = binding.titleInput.text.toString()
            val inputAmountStr = binding.amountInput.text.toString()

            // Check if inputAmountStr is empty or not a valid integer
            if (inputAmountStr.isNotEmpty() && inputName.isNotEmpty()) {
                val inputAmount = inputAmountStr.toInt()
                viewModel.addTransaction(inputName, inputCategory, inputAmount)
                Toast.makeText(
                    applicationContext,
                    "Transaksi ".plus(inputName).plus(" berhasil ditambahkan"),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please enter a valid amount or name or category",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}