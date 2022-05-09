package com.arifwidayana.simpledatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.arifwidayana.simpledatastore.databinding.ActivityMainBinding
import com.arifwidayana.simpledatastore.model.DataStoreService
import com.arifwidayana.simpledatastore.viewmodel.CountViewModel
import com.arifwidayana.simpledatastore.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private var bind: ActivityMainBinding? = null
    private val binding get() = bind!!
    private lateinit var countViewModel: CountViewModel
    private lateinit var pref: DataStoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = DataStoreService(this)
        countViewModel = ViewModelProvider(this, ViewModelFactory(pref))[CountViewModel::class.java]

        setView()
        setObserve()
    }

    private fun setView() {
        binding.apply {
            btnIncrement.setOnClickListener {
                increase()
            }
            btnDecrement.setOnClickListener {
                decrease()
            }
            btnSetValue.setOnClickListener {
                countViewModel.saveData(tvValue.text.toString().toInt())
            }
        }
    }

    private fun increase() {
        countViewModel.incrementCount()
    }

    private fun decrease() {
        countViewModel.decrementCount()
    }

    private fun setObserve() {
        binding.apply {
            countViewModel.apply {
                getData().observe(this@MainActivity) {
                    tvValue.text = it.toString()
                }

                counter.observe(this@MainActivity) { res ->
                    tvValue.text = res.toString()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }
}