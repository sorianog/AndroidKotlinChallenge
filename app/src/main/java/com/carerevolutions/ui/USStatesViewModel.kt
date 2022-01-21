package com.carerevolutions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carerevolutions.network.CountrySubdivision
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class USStatesViewModel: ViewModel() {

    private val _usStates = MutableLiveData<List<CountrySubdivision>>()
    val usStates: LiveData<List<CountrySubdivision>>
        get() = _usStates

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        loadUSStates()
    }

    private fun loadUSStates() {
        println("#### VM: loadUSStates")
        coroutineScope.launch {
            USStates.get {
                println("#### VM: State - $it")
                _usStates.value = it
            }
        }
    }

    // Prevent leak by overriding onCleared() and cleaning up related app data (i.e Job())
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}