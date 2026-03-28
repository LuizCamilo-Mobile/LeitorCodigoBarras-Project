package br.com.tanalista.ui.scanner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.tanalista.R
import br.com.tanalista.TaNaListaApp
import br.com.tanalista.databinding.FragmentScannerBinding
import br.com.tanalista.ui.MainViewModel
import br.com.tanalista.ui.MainViewModelFactory
import kotlinx.coroutines.launch

class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as TaNaListaApp).container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScannerBinding.bind(view)

        binding.btnMockScan.setOnClickListener {
            val value = binding.etBarcodeValue.text.toString()
            viewModel.mockScan(value)
        }

        collectState()
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                    state.errorMessage?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        viewModel.clearError()
                    }

                    if (state.lastScannedValue.isNotBlank()) {
                        binding.tvResult.text = "Último salvo: ${state.lastScannedValue}"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}