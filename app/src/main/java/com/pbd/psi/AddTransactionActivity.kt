package com.pbd.psi

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.pbd.psi.databinding.ActivityAddTransactionBinding
import com.pbd.psi.room.Category
import com.pbd.psi.ui.add_transaction.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStart() {
        super.onStart()

        binding.backButtonAdd.setOnClickListener{
            finish()
        }

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {}

        binding.submitButton.setOnClickListener{
            // check condition
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // When permission is granted
                // Call method
                getCurrentLocation()
            } else {
                // When permission is not granted
                // Call method
                locationPermissionRequest.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }


            val inputName = binding.titleInput.text.toString()
            val inputAmountStr = binding.amountInput.text.toString()

            // Check if inputAmountStr is empty or not a valid integer
            if (inputAmountStr.isNotEmpty() && inputName.isNotEmpty()) {
                val inputAmount = inputAmountStr.toInt()
                viewModel.addTransaction(inputName, Category.EXPENSE, inputAmount)
                Toast.makeText(applicationContext, "Transaksi ".plus(inputName).plus(" berhasil ditambahkan"), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Please enter a valid amount or name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check condition
        if (requestCode == 100 && grantResults.isNotEmpty()
            && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call  method
            getCurrentLocation();
        }
        else {
            // When permission are denied
            // Display toast
            Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
        }
    }

    private fun getCurrentLocation() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, request it
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),0)
            return
        }

        // Permission is granted, proceed with location retrieval
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            fusedLocationClient.lastLocation.addOnCompleteListener(this, OnCompleteListener { task ->
                val location: Location? = task.result
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Do something with latitude and longitude
                } else {
                    val locationRequest = LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)

                }
            })
        }
    }


}