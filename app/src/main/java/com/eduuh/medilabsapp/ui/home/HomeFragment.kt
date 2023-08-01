package com.eduuh.medilabsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eduuh.medilabsapp.databinding.FragmentHomeBinding
import com.eduuh.medilabsapp.helpers.PrefsHelper
import org.json.JSONObject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //code here
        val userObject = PrefsHelper.getPrefs(requireContext(),"userObject")
        val user = JSONObject(userObject) //convert to JSONobject
        //Text View 1
        val surname = binding.surname //find view
        surname.text = "Surname:"+user.getString("surname")
        //Text View
        val others = binding.surname //find view
        surname.text = "Others:"+user.getString("others")
        val gender = binding.surname //find view
        surname.text = "Gender:"+user.getString("gender")
        val dob = binding.surname //find view
        surname.text = "Dob:"+user.getString("dob")
        val reg_date = binding.surname //find view
        surname.text = "Reg_date:"+user.getString("reg_date")
        val email = binding.surname //find view
        surname.text = "Email:"+user.getString("email")

        //gender, dob, reg_date, email

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}