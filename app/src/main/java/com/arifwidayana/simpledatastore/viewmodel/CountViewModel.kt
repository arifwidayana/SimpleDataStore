package com.arifwidayana.simpledatastore.viewmodel

import androidx.lifecycle.*
import com.arifwidayana.simpledatastore.model.DataStoreService
import kotlinx.coroutines.launch

class CountViewModel(private val countPref: DataStoreService): ViewModel() {
    val counter: MutableLiveData<Int> = MutableLiveData(0)

    fun incrementCount() {
        counter.postValue(counter.value?.plus(1))
    }

    fun decrementCount() {
        counter.value?.let {
            when {
                it > 0 -> counter.postValue(counter.value?.minus(1))
            }
        }
    }

    fun saveData(value: Int) {
        viewModelScope.launch {
            countPref.setCount(value)
        }
    }

    fun getData(): LiveData<Int> {
        return countPref.getCount().asLiveData()
    }
}