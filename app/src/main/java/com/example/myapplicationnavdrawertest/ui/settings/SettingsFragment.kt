package com.example.myapplicationnavdrawertest.ui.settings

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Locale
interface MapModeChangeListener {
    fun onMapModeChanged(mode: String)
}
class SettingsFragment : Fragment() {

    private lateinit var changeThemeBtn: Button
    private lateinit var themeRadioGroup: RadioGroup
    private var mapModeChangeListener: MapModeChangeListener? = null
    private lateinit var notificationsSwitch: Switch
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
        notificationsSwitch = view.findViewById(R.id.notificationsSwitch)

        val sharedPreferenceManager = SharedPreferenceManager(requireContext())
        var storedThemeIndex = sharedPreferenceManager.theme
        if (storedThemeIndex == -1) {
            storedThemeIndex = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            sharedPreferenceManager.theme = storedThemeIndex
        }
        applyTheme(storedThemeIndex)

        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newIndex = themeRadioGroup.indexOfChild(view.findViewById(checkedId))
            sharedPreferenceManager.theme = newIndex
            applyTheme(newIndex)
        }

        changeThemeBtn.setOnClickListener {
            val themeDialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Theme")
                .setPositiveButton("Ok") { _, _ ->
                    applyTheme(sharedPreferenceManager.theme)
                }
                .setCancelable(false)
                .setSingleChoiceItems(
                    arrayOf("Light", "Dark", "Auto (System Default)"),
                    sharedPreferenceManager.theme
                ) { _, which ->
                    sharedPreferenceManager.theme = which
                    themeRadioGroup.check(which)
                }
            themeDialog.show()
        }

        view.findViewById<Button>(R.id.changeLanguageBtn).setOnClickListener {
            val languageDialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Language")
                .setPositiveButton("Ok") { _, _ ->
                    // Reload the activity to apply the selected language
                    requireActivity().recreate()
                }
                .setCancelable(false)
                .setSingleChoiceItems(
                    arrayOf("English", "Romana"),
                    getCurrentLanguageIndex()
                ) { dialog, which ->
                    val selectedLanguage = when (which) {
                        0 -> "en"
                        1 -> "ro"
                        else -> "en" // Default to English
                    }
                    setLocale(selectedLanguage)
                    dialog.dismiss()
                }
            languageDialog.show()
        }

        val mode = "satellite" // or any other mode you want
        mapModeChangeListener?.onMapModeChanged(mode)



        // Initialize the notifications switch
        notificationsSwitch = view.findViewById(R.id.notificationsSwitch)

        // Listen for changes in the notifications switch state
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestNotificationPermission()
            } else {
                // Handle when switch is turned off
            }
        }

    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    private fun getCurrentLanguageIndex(): Int {
        // Get the current language code and determine the index
        val currentLanguage = Locale.getDefault().language
        return when (currentLanguage) {
            "en" -> 0
            "ro" -> 1
            else -> 0 // Default to English if not found
        }
    }
    private fun applyLanguage(languageIndex: Int) {
        /* Apply language based on the languageIndex */
        /* Trigger recreation of the activity/fragment for the language change to take effect */
        requireActivity().recreate()
    }

    private fun applyTheme(themeIndex: Int) {
        val themeMode = when (themeIndex) {
            0 -> AppCompatDelegate.MODE_NIGHT_NO
            1 -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Notification permission granted, perform necessary actions here
                // For instance, enable notifications or any specific logic
            } else {
                // Notification permission denied, handle it accordingly
                // For instance, show a message explaining why permission is needed
            }
        }
    }

}
