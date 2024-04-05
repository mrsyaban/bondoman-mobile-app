package com.pbd.psi

import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import android.widget.Toast
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.pbd.psi.databinding.ActivityAddTransactionBinding
import com.pbd.psi.room.Category
import com.pbd.psi.room.TransactionEntity
import com.pbd.psi.ui.add_transaction.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    private val viewModel: AddTransactionViewModel by viewModels()

    private lateinit var spinner: Spinner
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    val categories = arrayOf("Pengeluaran", "Pemasukan")

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private  var  currentLatitude:Double = -6.8914937198875075
    private var currentLongitude: Double =  107.61066098027624
    private var currentAddress: String =  "not found"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinner = binding.categorySelector
        val categoryAdapter = ArrayAdapter(this, R.layout.list_category, categories)
        categoryAdapter.setDropDownViewResource(R.layout.list_category)
        spinner.adapter = categoryAdapter

        val title = intent.getStringExtra("TITLE")
        val amount = intent.getIntExtra("AMOUNT", 0)

        if (!title.isNullOrEmpty()) {
            binding.titleInput.setText(title)
        }

        if (amount != 0) {
            binding.amountInput.setText(amount.toString())
        }


        Log.d("Hasil Broadcast", "Title: $title, Nominal: $amount")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            val locationTask: Task<Location> = fusedLocationClient.getLastLocation();
            locationTask.addOnSuccessListener {
                currentLatitude = it.latitude
                currentLongitude = it.longitude
            }.addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    "lokasi default digunakan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            askLocationPermission()
        }

    }

    override fun onStart() {
        super.onStart()

        binding.backButtonAdd.setOnClickListener{
            finish()
        }

        var categorySelected = "none"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long) {
                categorySelected = categories[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // default is expense
            }
        }

        binding.submitButton.setOnClickListener {

            val inputName = binding.titleInput.text.toString()
            val inputAmountStr = binding.amountInput.text.toString()

            // Check if inputAmountStr is empty or not a valid integer
            if (inputAmountStr.isNotEmpty() && inputName.isNotEmpty() && categorySelected != "none") {
                val inputAmount = inputAmountStr.toInt()
                var inputCategory = Category.INCOME
                if (categorySelected == categories[0]) {
                    inputCategory = Category.EXPENSE
                }

                //        val curDate = android.icu.util.Calendar.getInstance(Locale.)
                val curDate = Date()
                val newTransaction = TransactionEntity(0, inputName, inputCategory, inputAmount, curDate, currentAddress, currentLongitude, currentLatitude)
                viewModel.addTransaction(newTransaction)

                // handle success
                Toast.makeText(
                    applicationContext,
                    "Transaksi ".plus(inputName).plus(" berhasil ditambahkan"),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                // handle failure
                Toast.makeText(
                    applicationContext,
                    "Please enter a valid amount or name or category",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun askLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // If user denied permission previously, show rationale and request permission again
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission to function properly.")
                    .setPositiveButton("OK") { _, _ ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                            LOCATION_PERMISSION_REQUEST_CODE
                        )
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            } else {
                // Request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun setCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            val locationTask: Task<Location> = fusedLocationClient.getLastLocation();
            locationTask.addOnSuccessListener {
                currentLatitude = it.latitude
                currentLongitude = it.longitude
            }.addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    "lokasi default digunakan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                setCurrentLocation()
            } else {
                Toast.makeText(
                    applicationContext,
                    "lokasi default digunakan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}