package com.pbd.psi.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pbd.psi.R
import com.pbd.psi.databinding.FragmentTransactionBinding
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.AppDatabase

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val appDatabase = AppDatabase.getDatabase(requireContext())
        val repository = TransactionRepository(appDatabase)
        val viewModel = TransactionViewModel(repository)
//        binding.transList.text = viewModel.transactionList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}