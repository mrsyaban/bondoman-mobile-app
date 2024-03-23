package com.pbd.psi.ui.twibbon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TwibbonViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Twibbon Fragment"
    }
    val text: LiveData<String> = _text
}