package com.amora.firebase.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.amora.firebase.MainActivity
import com.amora.firebase.databinding.FragmentDashboardBinding
import com.amora.firebase.ui.util.trackScreen
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        initView()
    }

    private fun initView() {
        binding.btTryMe.setOnClickListener {
            viewModel.rollDice()
        }
    }

    override fun onStart() {
        super.onStart()
        recordImageView()
        val firebase = (requireActivity() as? MainActivity)?.firebaseAnalytics
        firebase?.trackScreen(this.javaClass.name, this.javaClass)
    }

    private fun recordImageView() {
        val id = "imageId"
        val name = "imageTitle"

        // [START image_view_event]
        (requireActivity() as MainActivity).firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id)
            param(FirebaseAnalytics.Param.ITEM_NAME, name)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        }
        // [END image_view_event]
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.numDice.collect {
                binding.textDashboard.text = it.toString()
            }
        }

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textDashboard.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}