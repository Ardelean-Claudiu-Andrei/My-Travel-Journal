package com.example.myapplicationnavdrawertest.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationnavdrawertest.databinding.TestlayoutBinding // Update the import to match your actual binding class

class ProfileFragment : Fragment() {

    private var _binding: TestlayoutBinding? = null // Use the correct binding class
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = TestlayoutBinding.inflate(inflater, container, false) // Use the correct binding class
        val root: View = binding.root

//        val textView: TextView = binding.textGallery
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
