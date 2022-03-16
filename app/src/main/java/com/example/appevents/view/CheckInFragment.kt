package com.example.appevents.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.appevents.MainActivity
import com.example.appevents.R
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.databinding.FragmentCheckInBinding
import com.example.appevents.exception.CustomException
import com.example.appevents.model.CheckIn
import com.example.appevents.util.Util
import com.example.appevents.viewmodeltest.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CheckInFragment : Fragment(R.layout.fragment_check_in) {
    private lateinit var binding: FragmentCheckInBinding
    private lateinit var homeViewModel: HomeViewModel
    private var util = Util()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckInBinding.bind(view)

        val main = activity as MainActivity
        main.setToolbarTitle(getString(R.string.text_toolbar_check_in))

        homeViewModel = ViewModelProvider(this, HomeViewModel.HomeViewModelFactory(EventRepository())).get(HomeViewModel::class.java)
        checkInListener()

        val eventName = requireArguments().getString("eventName")
        val eventPrice = requireArguments().getString("eventPrice")
        val eventId = requireArguments().getString("eventID")

        binding.btnCheckIn.setOnClickListener {
            if (eventId != null) {
                doCheckIn(eventId)
                util.hideKeyboard(requireContext(),requireView())
            }
        }

        binding.txtEventNameCheckIn.text = eventName
        binding.txtEventPriceCheckIn.text = String.format("%s %s", getString(R.string.txt_event_price), eventPrice)
    }

    private fun checkInListener() {
        homeViewModel.checkInLiveData.observe(viewLifecycleOwner) { response ->
            println(response)
            when(response) {
                is Boolean -> {
                    if(response) {
                        checkInDialog(resources.getString(R.string.success), resources.getString(R.string.success_checkin))
                    }
                    else {
                        checkInDialog(resources.getString(R.string.error), resources.getString(R.string.error_checkin))
                    }
                }

                is CustomException -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.error_checkin),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun checkInDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                Navigation.findNavController(requireView()).popBackStack()
            }
            .show()
    }

    private fun inputErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.error))
            .setMessage(resources.getString(R.string.invalid_name_email))
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun doCheckIn(eventID: String) {
        val email = binding.inputEmail.text.toString()
        val name = binding.inputName.text.toString()

        if(util.isEmailValid(email) && util.isNameValid(name)) {
            homeViewModel.doCheckIn(CheckIn(eventID, name, email))
        }
        else {
            inputErrorDialog()
        }
    }
}