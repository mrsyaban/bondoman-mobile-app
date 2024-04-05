package com.pbd.psi.ui.transaction

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbd.psi.AddTransactionActivity
import com.pbd.psi.TransactionDetailActivity
import com.pbd.psi.databinding.FragmentTransactionBinding
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.AppDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private val transactionAdapter by lazy { TransactionViewAdapter() }
    private val viewModel: TransactionViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        viewModel.transactionList.observe(viewLifecycleOwner) { transItems ->
            val balanceAmount = viewModel.getBalance().toString()
            binding.balanceNominal.setText(balanceAmount)

            val transactions = requireNotNull(transItems) { "Transaction list is null" }
            val transList = ArrayList(transactions)
            transactionAdapter.transactionItems = transList
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.transactionList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
        transactionAdapter.itemClickListener = { item ->
            val intent = Intent(requireContext(), TransactionDetailActivity::class.java).apply {
                putExtra("id", item)
            }
            startActivity(intent)
        }
        transactionAdapter.locationClickListener = { latitude, longitude, location ->
            val gmmIntentUri = Uri.parse("google.streetview:cbll=${latitude},${longitude}")

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)
        }
        binding.addButton.setOnClickListener{
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            startActivity(intent)
        }

    }
}