package com.pbd.psi.ui.transaction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbd.psi.AddTransactionActivity
import com.pbd.psi.R
import com.pbd.psi.databinding.FragmentTransactionBinding
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.AppDatabase

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private val transactionAdapter by lazy { TransactionViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val appDatabase = AppDatabase.getDatabase(requireContext())
        val repository = TransactionRepository(appDatabase)
        val viewModel = TransactionViewModel(repository)
        viewModel.transactionList.observe(viewLifecycleOwner) { transItems ->
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
        binding.addButton.setOnClickListener{
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            startActivity(intent)
        }

    }
}