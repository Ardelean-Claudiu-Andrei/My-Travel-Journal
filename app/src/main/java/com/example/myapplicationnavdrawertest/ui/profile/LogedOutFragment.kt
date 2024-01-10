package com.example.myapplicationnavdrawertest.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.myapplicationnavdrawertest.R

class LogedOutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loged_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logInBtn: Button = view.findViewById(R.id.buttonLogOut)
        logInBtn.setOnClickListener {
//            Toast.makeText(requireContext(), "Add button clicked!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_logedOutFragment_to_nav_profile)
        }
    }
}