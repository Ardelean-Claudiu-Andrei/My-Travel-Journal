package com.example.myapplicationnavdrawertest.ui.contactus
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.myapplicationnavdrawertest.R

class ContactUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_us, container, false)

        val contactButton: Button = view.findViewById(R.id.contactButton)
        val subjectEditText: EditText = view.findViewById(R.id.subjectEditText)
        val messageEditText: EditText = view.findViewById(R.id.messageEditText)

        contactButton.setOnClickListener {
            val subject = subjectEditText.text.toString()
            val message = messageEditText.text.toString()
            composeEmail(subject, message)
        }

        return view
    }

    private fun composeEmail(subject: String, message: String) {
        val addresses = arrayOf("aclaudiuandrei586@gmail.com") // Replace with your contact email

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}
