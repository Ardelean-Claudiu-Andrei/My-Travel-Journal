package com.example.myapplicationnavdrawertest.ui.home
import com.example.myapplicationnavdrawertest.Locations
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplicationnavdrawertest.R
import com.example.myapplicationnavdrawertest.databinding.FragmentHomeBinding
import com.example.myapplicationnavdrawertest.ui.home.DetailsFragment

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

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
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        switchOnOff = view.findViewById(R.id.switchOnOff) ?: SwitchCompat(requireContext())
        tvSwitchYes = view.findViewById(R.id.tvSwitchAll) ?: TextView(requireContext())
        tvSwitchNo = view.findViewById(R.id.tvSwitchFavorite) ?: TextView(requireContext())

        switchOnOff.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                tvSwitchYes.setTextColor(ContextCompat.getColor(requireContext(), R.color.switchBlue))
                tvSwitchNo.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                // Perform logic to show favorite memories
                // refreshMemoriesDisplay(true)
            } else {
                tvSwitchYes.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                tvSwitchNo.setTextColor(ContextCompat.getColor(requireContext(), R.color.switchBlue))
                // Perform logic to show all memories
                // refreshMemoriesDisplay(false)
            }
        }

        val locationImage1 = view.findViewById<ImageView>(R.id.cluj_imageV)
        val locationImage2 = view.findViewById<ImageView>(R.id.bucuresti_imageV)
        val locationImage3 = view.findViewById<ImageView>(R.id.iasi_imageV)
        val locationImage4 = view.findViewById<ImageView>(R.id.berlin_imageV)

        val addButton: Button = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add button clicked!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_nav_home_to_addFragment)
        }

        // Set click listeners for location images
        locationImage1.setOnClickListener {
            imageIndex = 0
            val selectedLocation = locationBank[imageIndex]

            val bundle = Bundle()
            bundle.putParcelable("selectedLocation", selectedLocation)

            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle

            parentFragmentManager.commit {
                replace(R.id.fragment_container, detailsFragment)
                addToBackStack(null)
            }
        }



        locationImage2.setOnClickListener {
            imageIndex = 1
            val selectedLocation = locationBank[imageIndex]

            val bundle = Bundle()
            bundle.putParcelable("selectedLocation", selectedLocation)

            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle

            parentFragmentManager.commit {
                replace(R.id.fragment_container, detailsFragment)
                addToBackStack(null)
            }
        }

        locationImage3.setOnClickListener {
            imageIndex = 2
            val selectedLocation = locationBank[imageIndex]

            val bundle = Bundle()
            bundle.putParcelable("selectedLocation", selectedLocation)

            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle

            parentFragmentManager.commit {
                replace(R.id.fragment_container, detailsFragment)
                addToBackStack(null)
            }
        }

        locationImage4.setOnClickListener {
            imageIndex = 3
            val selectedLocation = locationBank[imageIndex]

            val bundle = Bundle()
            bundle.putParcelable("selectedLocation", selectedLocation)

            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle

            parentFragmentManager.commit {
                replace(R.id.fragment_container, detailsFragment)
                addToBackStack(null)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchOnOff = view.findViewById(R.id.switchOnOff) ?: SwitchCompat(requireContext())
        tvSwitchYes = view.findViewById(R.id.tvSwitchAll) ?: TextView(requireContext())
        tvSwitchNo = view.findViewById(R.id.tvSwitchFavorite) ?: TextView(requireContext())

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

        // Rest of your existing onViewCreated code...

    }


    private fun navigateToDetailsFragment(selectedLocation: Locations) {
        val bundle = Bundle()
        bundle.putParcelable("selectedLocation", selectedLocation)

        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = bundle

        parentFragmentManager.commit {
            replace(R.id.fragment_container, detailsFragment) //??????????????????
            addToBackStack(null)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}