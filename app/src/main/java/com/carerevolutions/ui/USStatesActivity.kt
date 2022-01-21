package com.carerevolutions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.carerevolutions.R
import com.carerevolutions.databinding.ActivityUsStatesBinding

class USStatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsStatesBinding

    private val viewModel: USStatesViewModel by lazy {
        ViewModelProvider(this).get(USStatesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsStatesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = USStatesListAdapter(mutableListOf(), this)
        binding.stateListView.adapter = adapter

        adapter.items = viewModel.usStates.value ?: emptyList()

        USStates.get { states ->
            adapter.items = states
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
