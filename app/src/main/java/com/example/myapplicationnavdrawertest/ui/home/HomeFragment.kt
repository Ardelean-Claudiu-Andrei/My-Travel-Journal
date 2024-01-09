package com.example.myapplicationnavdrawertest.ui.home

//import Locations
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.myapplicationnavdrawertest.Locations
import com.example.myapplicationnavdrawertest.R
//import com.example.mytraveljournal.R
import com.example.mytraveljournal.ui.home.FavoriteToggleListener


private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), FavoriteToggleListener {

    private val locationBank = mutableListOf(
        Locations(R.drawable.clujnapoca1, R.id.clujTitle_textV, "Cluj Napoca", "Cluj Napoca, Cluj", "Romania", "Last visited: 15/05/2019", 4.5f),
        Locations(R.drawable.bucuresti1, R.id.bucurestiTitle_textV, "Bucuresti", "Bucuresti, Ilfov", "Romania", "Last visited: 23/05/2019", 5.0f),
        Locations(R.drawable.iasi1, R.id.iasiTitle_textV, "Iasi", "Iasi, Iasi", "Romania", "Last visited: 10/08/2018", 4.0f),
        Locations(R.drawable.berlin1, R.id.berlinTitle_textV, "Berlin", "Berlin, Berlin", "Deutschland", "Last visited: 23/07/2017", 3.5f)
    )

    private var imageIndex: Int = 0
    private lateinit var location: Locations
    // Declare your views
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

        // Set a listener to the switch
        switchOnOff.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                // When switch is ON
                tvSwitchYes.setTextColor(ContextCompat.getColor(requireContext(), R.color.switchBlue))
                tvSwitchNo.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                // Perform logic to show favorite memories
                // refreshMemoriesDisplay(true)
            } else {
                // When switch is OFF
                tvSwitchYes.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                tvSwitchNo.setTextColor(ContextCompat.getColor(requireContext(), R.color.switchBlue))

                // Perform logic to show all memories
                // refreshMemoriesDisplay(false)
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
//        locationImage1.setOnClickListener {
//            imageIndex = 0
//            findNavController().navigate(R.id.action_nav_home_to_detailsFragment)
//        }

        val addButton: Button = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            // Call the logic you want to execute when the button is clicked
            // For example, showing a toast message
            Toast.makeText(requireContext(), "Add button clicked!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_nav_home_to_addFragment)
        }

        // Assume locationImage1, locationImage2, locationImage3, locationImage4 are ImageView objects

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


//        locationImage1.setOnClickListener {
//            imageIndex = 0
//            val selectedLocation = locationBank[imageIndex]
//
//            val bundle = Bundle()
//            bundle.putParcelable("selectedLocation", selectedLocation)
//
//            findNavController().navigate(
//                R.id.action_nav_home_to_detailsFragment,
//                bundle
//            )
//        }
//
//
//
//        locationImage2.setOnClickListener {
//            imageIndex = 1
//            val selectedLocation = locationBank[imageIndex]
//
//            val bundle = Bundle()
//            bundle.putParcelable("selectedLocation", selectedLocation)
//
//            val detailsFragment = DetailsFragment()
//            detailsFragment.arguments = bundle
//
//            parentFragmentManager.commit {
//                replace(R.id.fragment_container, detailsFragment)
//                addToBackStack(null)
//            }
//        }
//
//        locationImage3.setOnClickListener {
//            imageIndex = 2
//            val selectedLocation = locationBank[imageIndex]
//
//            val bundle = Bundle()
//            bundle.putParcelable("selectedLocation", selectedLocation)
//
//            val detailsFragment = DetailsFragment()
//            detailsFragment.arguments = bundle
//
//            parentFragmentManager.commit {
//                replace(R.id.fragment_container, detailsFragment)
//                addToBackStack(null)
//            }
//        }
//
//        locationImage4.setOnClickListener {
//            imageIndex = 3
//            val selectedLocation = locationBank[imageIndex]
//
//            val bundle = Bundle()
//            bundle.putParcelable("selectedLocation", selectedLocation)
//
//            val detailsFragment = DetailsFragment()
//            detailsFragment.arguments = bundle
//
//            parentFragmentManager.commit {
//                replace(R.id.fragment_container, detailsFragment)
//                addToBackStack(null)
//            }
//        }

        return view
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { returnedResult ->
            when (returnedResult.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    val intent = returnedResult.data
                    val result = intent?.getParcelableExtra<Locations>("result")
                    if (result != null) {
                        val existingLocation: Locations = locationBank[imageIndex]
                        val updatedLocation: Locations = result
                        locationBank[imageIndex] = updatedLocation

                        // Update Location's title TextView
                        val titleTVId = updatedLocation.titleTV
                        val ratingStr = updatedLocation.rating.toString()
                        val introTV = view?.findViewById<TextView>(titleTVId)
                        val introStr = updatedLocation.name.plus("\n").plus(ratingStr).plus(" Stars")
                        introTV?.text = introStr

                        // Show a toast to advise if details of the location have been updated
                        val existingName = existingLocation.name
                        val updatedName = updatedLocation.name
                        val existingCity = existingLocation.cityAndState
                        val updatedCity = updatedLocation.cityAndState
                        val existingCountry = existingLocation.country
                        val updatedCountry = updatedLocation.country
                        val existingDate = existingLocation.lastVisitDate
                        val updatedDate = updatedLocation.lastVisitDate
                        val existingRating = existingLocation.rating
                        val updatedRating = updatedLocation.rating

                        if (existingName != updatedName ||
                            existingCity != updatedCity ||
                            existingCountry != updatedCountry ||
                            existingDate != updatedDate ||
                            existingRating != updatedRating
                        ) {
                            context?.let {
                                Toast.makeText(
                                    it,
                                    "${updatedLocation.name} updated!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }



    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
        // Other logic for onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
        // Other logic for onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
        // Other logic for onPause()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
        // Other logic for onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
        // Other logic for onDestroy()
    }

    override fun onFavoriteToggled(isFavorite: Boolean) {
        val favoriteToggleButton: ToggleButton? = view?.findViewById(R.id.favorite_toggle_button)
        favoriteToggleButton?.isChecked = isFavorite
    }

    private fun refreshMemoriesDisplay(showFavoritesOnly: Boolean) {
        // Your logic to filter and display the memories based on the showFavoritesOnly flag
        // Update the UI accordingly
    }

}