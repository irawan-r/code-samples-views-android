package com.amora.firebase.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amora.firebase.MainActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DashboardViewModel : ViewModel() {
    private val firebaseAnalytics = Firebase.analytics
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _numDice = MutableStateFlow(0)
    val numDice = _numDice.asStateFlow()

    private val diceRolled = MutableStateFlow(0)

    fun rollDice() {
        _numDice.update {
            Random.nextInt(1, 7)
        }
        diceRolled.update {
            it + 1
        }

        firebaseAnalytics.logEvent("roll_dice") {
            param("rolled", diceRolled.value.toString())
            param("number", _numDice.value.toString())
        }
    }
}