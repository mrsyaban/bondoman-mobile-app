package com.pbd.psi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pbd.psi.databinding.ActivityAddTransactionBinding
import com.pbd.psi.ui.add_transaction.AddTransactionFragment

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = AddTransactionFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholderForm, fragment)
            .commit()
    }
}