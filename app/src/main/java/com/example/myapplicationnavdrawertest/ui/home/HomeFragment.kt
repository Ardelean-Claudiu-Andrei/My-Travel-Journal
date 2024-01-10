package com.example.myapplicationnavdrawertest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplicationnavdrawertest.Locations
import com.example.myapplicationnavdrawertest.R
import com.example.mytraveljournal.ui.home.FavoriteToggleListener


private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), FavoriteToggleListener {

    private val locationBank = mutableListOf(
        Locations(R.drawable.clujnapoca1, R.id.clujTitle_textV, "Cluj Napoca", "Cluj Napoca, Cluj", "Romania", "Last visited: 15/05/2024", 4.5f),
        Locations(R.drawable.bucuresti1, R.id.bucurestiTitle_textV, "Bucharest", "Bucharest, Ilfov", "Romania", "Last visited: 08/08/2022", 5.0f),
        Locations(R.drawable.iasi1, R.id.iasiTitle_textV, "Iasi", "Iasi, Iasi", "Romania", "Last visited: 10/08/2012", 4.0f),
        Locations(R.drawable.berlin1, R.id.berlinTitle_textV, "Berlin", "Berlin, Berlin", "Deutschland", "Last visited: 10/01/2020", 3.5f)
    )


    private var imageIndex: Int = 0
    private lateinit var location: Locations
    private lateinit var switchOnOff: SwitchCompat
    private lateinit var tvSwitchYes: TextView
    private lateinit var tvSwitchNo: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        switchOnOff = view?.findViewById(R.id.switchOnOff) ?: SwitchCompat(requireContext())
        tvSwitchYes = view?.findViewById(R.id.tvSwitchAll) ?: TextView(requireContext())
        tvSwitchNo = view?.findViewById(R.id.tvSwitchFavorite) ?: TextView(requireContext())

        // listener to the switch
        switchOnOff.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                // When switch is ON
                tvSwitchYes.setTextColor(ContextCompat.getColor(requireContext(), R.color.switchBlue))
                tvSwitchNo.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                // When switch is OFF
                tvSwitchYes.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                tvSwitchNo.setTextColor(ContextCompat.getColor(requireContext(), R.color.switchBlue))

            }
        }


        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val locationImage1 = view.findViewById<ImageView>(R.id.cluj_imageV)
        val locationImage2 = view.findViewById<ImageView>(R.id.bucuresti_imageV)
        val locationImage3 = view.findViewById<ImageView>(R.id.iasi_imageV)
        val locationImage4 = view.findViewById<ImageView>(R.id.berlin_imageV)


        val detailsFragment = DetailsFragment()
        detailsFragment.setFavoriteToggleListener(this)

        // Find and initialize favoriteToggleButton
        val favoriteToggleButton1: ToggleButton = view.findViewById(R.id.favorite_toggle_button1)
        val favoriteToggleButton2: ToggleButton = view.findViewById(R.id.favorite_toggle_button2)
        val favoriteToggleButton3: ToggleButton = view.findViewById(R.id.favorite_toggle_button3)
        val favoriteToggleButton4: ToggleButton = view.findViewById(R.id.favorite_toggle_button4)


        val addButton: Button = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
//            Toast.makeText(requireContext(), "Add button clicked!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_nav_home_to_addFragment)
        }

        // For locationImage1, locationImage2, locationImage3, locationImage4 are ImageView objects

        val locationImages = listOf(locationImage1, locationImage2, locationImage3, locationImage4)

        locationImages.forEachIndexed { index, locationImage ->
            locationImage.setOnClickListener {
                val selectedLocation = locationBank[index]

                val bundle = Bundle()
                bundle.putParcelable("selectedLocation", selectedLocation)

                findNavController().navigate(
                    R.id.action_nav_home_to_detailsFragment,
                    bundle
                )
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onFavoriteToggled(isFavorite: Boolean) {
        val favoriteToggleButton: ToggleButton? = view?.findViewById(R.id.favorite_toggle_button)
        favoriteToggleButton?.isChecked = isFavorite
    }

}