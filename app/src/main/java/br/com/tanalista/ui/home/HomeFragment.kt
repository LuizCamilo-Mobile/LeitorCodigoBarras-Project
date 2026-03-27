package br.com.tanalista.ui.home
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import br.com.tanalista.R
import br.com.tanalista.TaNaListaApp
import br.com.tanalista.databinding.FragmentHomeBinding
import br.com.tanalista.ui.MainViewModel
import br.com.tanalista.ui.MainViewModelFactory
import kotlinx.coroutines.launch
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as TaNaListaApp).container)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        collectState()
    }
    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvLastValue.text =
                        if (state.lastScannedValue.isBlank()) "Nenhum código lido ainda"
                        else "Último código: ${state.lastScannedValue}"
                    binding.tvHistoryCount.text = "Total no histórico: ${state.history.size}"
                }
            }
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}