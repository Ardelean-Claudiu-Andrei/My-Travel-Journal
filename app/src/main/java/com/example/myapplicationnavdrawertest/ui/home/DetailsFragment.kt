package com.example.myapplicationnavdrawertest.ui.home

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.icu.util.Calendar
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationnavdrawertest.Locations
import com.example.myapplicationnavdrawertest.R
import com.example.mytraveljournal.ui.home.FavoriteToggleListener
import com.example.mytraveljournal.ui.home.PhotoGridAdapter
import com.example.mytraveljournal.ui.home.PhotoModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class DetailsFragment : Fragment(), OnMapReadyCallback {


    lateinit var updatedLoc: Locations
    var locNameText = ""
    var locCityAndStateText = ""
    var locCountryText = ""
    var locVisitedDateText = ""
    var locRatingFloat: Float = 0.0f

    // Create a placeholder for the weather info TextView
    private lateinit var weatherInfoTextView: TextView
    private lateinit var datePickerEditText: EditText

    private var mGoogleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private var mapView: View? = null

    private var favoriteToggleListener: FavoriteToggleListener? = null

    fun setFavoriteToggleListener(listener: FavoriteToggleListener) {
        this.favoriteToggleListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        mapView = view.findViewById(R.id.map_fragment)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedLocation = arguments?.getParcelable<Locations>("selectedLocation")
        if (selectedLocation != null) {
            val imageResId = selectedLocation.image
            val locationName = selectedLocation.name
            val locationCity = selectedLocation.cityAndState
            val locationCountry = selectedLocation.country
//            val lastVisitedDate = selectedLocation.lastVisitDate



            val locationImage = view.findViewById<ImageView>(R.id.location_imageV)
            locationImage.setImageResource(imageResId)
            fetchWeatherData(locationName)

            val locationEditText = view.findViewById<EditText>(R.id.location_ET)
            locationEditText.setText(locationName)
            val locationCityEditText = view.findViewById<EditText>(R.id.cityAndState_ET)
            locationCityEditText.setText(locationCity)
            val locationCountryEditText = view.findViewById<EditText>(R.id.country_ET)
            locationCountryEditText.setText(locationCountry)
//            val lastVisitedDateEditText = view.findViewById<EditText>(R.id.visitDate_ET)
//            lastVisitedDateEditText.setText(lastVisitedDate)


            weatherInfoTextView = view.findViewById(R.id.weather_info)

            if (selectedLocation != null) {
                val favoriteToggleButton = view.findViewById<ToggleButton>(R.id.favorite_toggle_button)
                favoriteToggleButton.isChecked = selectedLocation.isFavorite

                favoriteToggleButton.setOnCheckedChangeListener { _, isChecked ->
                    selectedLocation.isFavorite = isChecked
                    // You might want to update the UI or persist this change to a database
                }
            }

            val favoriteToggleButton = view?.findViewById<ToggleButton>(R.id.favorite_toggle_button)

            if (favoriteToggleButton != null) {
                favoriteToggleButton.setOnCheckedChangeListener { _, isChecked ->
                    selectedLocation.isFavorite = isChecked
                    (activity as? FavoriteToggleListener)?.onFavoriteToggled(isChecked)
                }
            }

        }

        datePickerEditText = view.findViewById(R.id.datePickerDetails)
        datePickerEditText.setText(selectedLocation?.lastVisitDate)
        datePickerEditText.setOnClickListener { showDatePickerDialog() }



        mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        mapView?.setOnTouchListener { _, _ ->
            // Disabling touch events for the map view
            view.parent.requestDisallowInterceptTouchEvent(true)
            false
        }


        val photoRecyclerView: RecyclerView = view.findViewById(R.id.photo_grid_recycler_view)


        val location = arguments?.getParcelable<Locations>("selectedLocation")
        if (location != null) {
            val photoList: List<PhotoModel> = retrievePhotoList(location.name)
            val adapter = PhotoGridAdapter(photoList)
            photoRecyclerView.adapter = adapter
        }

//        val selectedLocation = arguments?.getParcelable<Locations>("selectedLocation")
        if (selectedLocation != null) {
            val favoriteToggleButton = view.findViewById<ToggleButton>(R.id.favorite_toggle_button)
            favoriteToggleButton.isChecked = selectedLocation.isFavorite

            favoriteToggleButton.setOnCheckedChangeListener { _, isChecked ->
                selectedLocation.isFavorite = isChecked
                updateToggleButtonStyle(favoriteToggleButton, isChecked)
            }


            updateToggleButtonStyle(favoriteToggleButton, selectedLocation.isFavorite)
        }

    }

    // Function to fetch weather data
    private fun fetchWeatherData(cityName: String) {
        val formattedCityName = cityName.replace(" ", "-")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=$formattedCityName&appid=7ada52993f5c19c218878694b51bebdc&units=metric"

                val resultJson = URL(apiUrl).readText()
                val jsonObj = JSONObject(resultJson)
                val main = jsonObj.getJSONObject("main")
                val temp = main.getString("temp") + "°C"
                val feelsLike = main.getString("feels_like") + "°C"
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val weatherDescription = weather.getString("description")

                val weatherInfo = "Temperature: $temp\nFeels like: $feelsLike\nDescription: $weatherDescription"
                val primaryColor = if (isDarkTheme()) {
                    ContextCompat.getColor(requireContext(), R.color.white)
                } else {
                    ContextCompat.getColor(requireContext(), R.color.black)
                }
                withContext(Dispatchers.Main) {
                    weatherInfoTextView.text = weatherInfo
                    weatherInfoTextView.setTextColor(primaryColor)

                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }


    // Function to check if the current theme is dark
    private fun isDarkTheme(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        val selectedLocation = arguments?.getParcelable<Locations>("selectedLocation")
        if (selectedLocation != null) {
            showLocationOnMap(selectedLocation)
        }
    }

    private fun showLocationOnMap(location: Locations) {
        val geocoder = Geocoder(requireContext())

        try {
            val addresses = geocoder.getFromLocationName(location.name, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    val markerOptions = MarkerOptions().position(latLng).title(location.name)

                    mGoogleMap?.clear()
                    mGoogleMap?.addMarker(markerOptions)
                    mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                } else {
                    Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
    fun updateMapMode(mode: String) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            when (mode) {
                "satellite" -> googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
                else -> googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
            }
        }
    }


    private fun updateToggleButtonStyle(button: ToggleButton, isFavorite: Boolean) {

        if (isFavorite) {

            button.setBackgroundResource(R.drawable.favorite_icon)
        } else {

            button.setBackgroundResource(R.drawable.non_favorite_icon)
        }
    }

    private fun isLetters(str: String): Boolean {
        return str.matches("^[a-zA-Z ,-]{0,29}[a-zA-Z]$".toRegex())
    }

    private fun retrievePhotoList(locationName: String): List<PhotoModel> {
        val formattedLocationName = locationName.replace(" ", "")
        val photoList = mutableListOf<PhotoModel>()

        val photoCount = 3 // number of photos to load for location

        // Load photos based on location name
        for (i in 1..photoCount) {
            val photoResourceName = "${formattedLocationName.toLowerCase()}$i"
            val photoResourceId = resources.getIdentifier(photoResourceName, "drawable", requireContext().packageName)

            if (photoResourceId != 0) {
                photoList.add(PhotoModel(photoResourceId))
            }
        }

        return photoList
    }


    private fun updateUI(weatherData: String) {
        try {
            val jsonObj = JSONObject(weatherData)
            val main = jsonObj.getJSONObject("main")
            val temp = main.getString("temp") + "°C"

            val temperatureTextView = view?.findViewById<TextView>(R.id.weather_info)

            temperatureTextView?.text = "Temperature: $temp"
        } catch (e: JSONException) {
            e.printStackTrace()

        }
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

    fun onBackPressed() {
    }
}