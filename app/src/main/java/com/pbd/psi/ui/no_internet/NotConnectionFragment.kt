package com.pbd.psi.ui.no_internet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pbd.psi.databinding.FragmentNotConnectionBinding

class NotConnectionFragment : Fragment() {

    private lateinit var binding: FragmentNotConnectionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotConnectionBinding.inflate(layoutInflater)
        return binding.root
    }
}
