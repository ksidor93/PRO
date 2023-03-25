package com.example.e_montaysta.ui.tools

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.e_montaysta.data.adapters.ToolAdapter
import com.example.e_montaysta.data.datasource.ToolsDataSource
import com.example.e_montaysta.databinding.FragmentToolsBinding

class `ToolsFragment.kt` : Activity() {
    private var _binding: FragmentToolsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val toolsViewModel =
            ViewModelProvider(this)[ToolsViewModel::class.java]

        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = ToolAdapter(ToolsDataSource.toolList)
        binding?.toolRv?.adapter = adapter

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}