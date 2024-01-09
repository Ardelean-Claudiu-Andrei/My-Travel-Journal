package com.example.mytraveljournal.ui.home

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.myapplicationnavdrawertest.R

// AddFragment.kt

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var datePickerEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        datePickerEditText = view.findViewById(R.id.datePicker)
        datePickerEditText.setOnClickListener { showDatePickerDialog() }

        // Find the spinner view by its ID
        val travelTypeSpinner: Spinner = view.findViewById(R.id.travelTypeSpinner)

        // Define an array of options for the spinner
        val travelOptions = arrayOf("Leisure", "Business", "Family", "Others")

        // Create an adapter for the spinner and set it
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, travelOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        travelTypeSpinner.adapter = adapter

        // Set a listener to handle item selection if needed
        travelTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = travelOptions[position]
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle situation where nothing is selected
            }
        }

        // Find the seek bar and mood label by their IDs
        val travelMoodSlider: SeekBar = view.findViewById(R.id.travelMoodSlider)
        val moodLabel: TextView = view.findViewById(R.id.moodLabel)

        // Set up a listener for changes in the seek bar's progress
        travelMoodSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Handle progress changes and update the mood label
                val mood: String = when (progress) {
                    0 -> "Sad"
                    1 -> "Happy"
                    else -> "Excited"
                }
                moodLabel.text = "Travel mood: $mood"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not needed for this implementation
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not needed for this implementation
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay.${selectedMonth + 1}.$selectedYear"
                datePickerEditText.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePicker.show()
    }
}
