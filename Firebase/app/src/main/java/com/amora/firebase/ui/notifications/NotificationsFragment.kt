package com.amora.firebase.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.amora.firebase.MainActivity
import com.amora.firebase.databinding.FragmentNotificationsBinding
import com.amora.firebase.ui.util.trackScreen
import com.amora.firebase.ui.util.tryCrashButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
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
        logCustomEvent()
        val firebase = (requireActivity() as? MainActivity)?.firebaseAnalytics
        firebase?.trackScreen(this.javaClass.name, this.javaClass)
    }

    private fun logCustomEvent() {
        val name = "customImage"
        val text = "I'd love to hear more about $name"

        // [START custom_event]
        (requireActivity() as MainActivity).firebaseAnalytics.logEvent("share_image") {
            param("image_name", name)
            param("full_text", text)
        }
        // [END custom_event]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}