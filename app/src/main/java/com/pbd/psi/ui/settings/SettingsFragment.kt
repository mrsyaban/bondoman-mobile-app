package com.pbd.psi.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pbd.psi.LoginActivity
import com.pbd.psi.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedpreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.btnKeluar.setOnClickListener {
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }
}
