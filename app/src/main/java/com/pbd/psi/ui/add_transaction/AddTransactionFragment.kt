package com.pbd.psi.ui.add_transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbd.psi.R
import com.pbd.psi.databinding.FragmentAddTransactionBinding
import com.pbd.psi.repository.TransactionRepository
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTransactionViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.submitButton.setOnClickListener{
            val inputName = binding.titleInput.text.toString()
            val inputAmountStr = binding.amountInput.text.toString()

            // Check if inputAmountStr is empty or not a valid integer
            if (inputAmountStr.isNotEmpty()) {
                val inputAmount = inputAmountStr.toInt()
                viewModel.addTransaction(inputName, Category.EXPENSE, inputAmount)
                requireActivity().finish()
            } else {
                // Handle case where amount input is empty
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}