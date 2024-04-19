package com.amora.firebase.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.amora.firebase.MainActivity
import com.amora.firebase.databinding.FragmentHomeBinding
import com.amora.firebase.ui.util.trackScreen
import com.amora.firebase.ui.util.tryCrashButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
         viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btTryMe.tryCrashButton()
    }

    override fun onStart() {
        super.onStart()
        val firebase = (requireActivity() as? MainActivity)?.firebaseAnalytics
        firebase?.trackScreen(this.javaClass.name, this.javaClass)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}