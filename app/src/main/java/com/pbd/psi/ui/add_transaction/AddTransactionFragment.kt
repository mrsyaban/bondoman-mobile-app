package com.pbd.psi.ui.add_transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.pbd.psi.R
import com.pbd.psi.databinding.FragmentAddTransactionBinding
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.Category

class AddTransactionFragment : Fragment() {


    private lateinit var binding: FragmentAddTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener{
            val inputName = binding.titleInput.text.toString()
            val inputAmountStr = binding.amountInput.text.toString()

            // Check if inputAmountStr is empty or not a valid integer
            if (inputAmountStr.isNotEmpty()) {
                val inputAmount = inputAmountStr.toInt()
                val appDatabase = AppDatabase.getDatabase(requireContext())
                val repository = TransactionRepository(appDatabase)
                val viewModel = AddTransactionViewModel(repository)
                viewModel.addTransaction(inputName, Category.EXPENSE, inputAmount)
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
            } else {
                // Handle case where amount input is empty
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }


}