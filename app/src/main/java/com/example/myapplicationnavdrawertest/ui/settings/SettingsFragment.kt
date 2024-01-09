package com.example.myapplicationnavdrawertest.ui.settings
//import java.util.jar.Manifest
import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplicationnavdrawertest.R
import com.example.myapplicationnavdrawertest.SharedPreferenceManager
//import com.example.mytraveljournal.R
//import com.example.mytraveljournal.SharedPreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Locale


class SettingsFragment : Fragment() {

    private lateinit var changeThemeBtn: Button
    private lateinit var themeRadioGroup: RadioGroup

    private val themeTitleList = arrayOf("Light", "Dark", "Auto (System Default)")
    private val languageTitleList = arrayOf("English", "Romanian")
    private lateinit var notificationsSwitch: Switch

    // Define a constant for the notification permission request
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        initializeViews(view)
        return view
    }

    private fun initializeViews(view: View) {
        changeThemeBtn = view.findViewById(R.id.changeThemeBtn)
        themeRadioGroup = view.findViewById(R.id.themeRadioGroup)

        val sharedPreferenceManager = SharedPreferenceManager(requireContext())

        // Retrieve the stored theme or default to system default
        var storedThemeIndex = sharedPreferenceManager.theme

        // If no theme is set yet, set it to the system default
        if (storedThemeIndex == -1) {
            storedThemeIndex =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            sharedPreferenceManager.theme = storedThemeIndex
        }

        // Apply the system default theme initially
        applyTheme(storedThemeIndex)

        // Listen for changes in the selected theme
        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newIndex = themeRadioGroup.indexOfChild(view.findViewById(checkedId))
            // Store the selected theme in preferences
            sharedPreferenceManager.theme = newIndex
            // Apply the selected theme
            applyTheme(newIndex)
        }

        // Show theme change dialog on button click
        changeThemeBtn.setOnClickListener {
            val themeDialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Theme")
                .setPositiveButton("Ok") { _, _ ->
                    // Apply the selected theme
                    applyTheme(sharedPreferenceManager.theme)
                }
                .setCancelable(false)
                .setSingleChoiceItems(themeTitleList, sharedPreferenceManager.theme) { _, which ->
                    // Update the selected theme when the dialog selection changes
                    sharedPreferenceManager.theme = which
                    themeRadioGroup.check(which)
                }
            themeDialog.show()
        }

        // Handle language selection
        view.findViewById<RadioGroup>(R.id.languageRadioGroup).setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.englishRadioButton -> setLocale("en")
                R.id.romanianRadioButton -> setLocale("ro")
            }
            requireActivity().recreate()
        }

        // Initialize the notifications switch
        notificationsSwitch = view.findViewById(R.id.notificationsSwitch)

        // Listen for changes in the notifications switch state
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // If the switch is turned on, request notification permission
                requestNotificationPermission()
            } else {
                // If the switch is turned off, handle the logic accordingly
                // (e.g., disable notifications or other actions)
            }
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Restart the fragment manager to reload the fragment with the new language
        requireActivity().supportFragmentManager.beginTransaction().detach(this).attach(this).commit()
    }



    private fun applyTheme(themeIndex: Int) {
        val themeMode = when (themeIndex) {
            0 -> AppCompatDelegate.MODE_NIGHT_NO // Light theme
            1 -> AppCompatDelegate.MODE_NIGHT_YES // Dark theme
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM // System default
        }
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }


    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, perform necessary actions
        }
    }



    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Notification permission granted, perform necessary actions here
                // (e.g., enable notifications)
            } else {
                // Notification permission denied, handle it accordingly
                // (e.g., show a message or disable notifications)
            }
        }
    }
}
