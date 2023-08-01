package com.eduuh.medilabsapp.ui.dependants

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.eduuh.medilabsapp.R
import com.eduuh.medilabsapp.ViewDependants
import com.eduuh.medilabsapp.constants.Constants
import com.eduuh.medilabsapp.helpers.ApiHelper
import com.eduuh.medilabsapp.helpers.PrefsHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class DependantFragment : Fragment() {
    private lateinit var buttonDatePicker: Button
    private lateinit var editTextDate: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_dependant, container, false)
        //Code here
        buttonDatePicker = root.findViewById(R.id.buttonDatePicker)
        editTextDate = root.findViewById(R.id.editTextDate)

        buttonDatePicker.setOnClickListener {
            showDatePickerDialog()
        }//end onclick

        val mydependants  = root.findViewById<MaterialButton>(R.id.mydependants)
        mydependants.setOnClickListener {
            startActivity(Intent(requireContext(), ViewDependants::class.java))
        }

        //post to API
        val adddependant = root.findViewById<MaterialButton>(R.id.adddependant)
        adddependant.setOnClickListener {
            //Push/Post data to APi.
            val surname = root.findViewById<TextInputEditText>(R.id.surname)
            val others = root.findViewById<TextInputEditText>(R.id.others)
            val female = root.findViewById<RadioButton>(R.id.radioFemale)
            val male = root.findViewById<RadioButton>(R.id.radioMale)
            var gender = "N/A"
            if (female.isSelected) {
                gender = "Female"
            }
            if (male.isSelected) {
                gender = "Male"
            }
            val member_id = PrefsHelper.getPrefs(requireContext(), "member_id")

            val api = Constants.BASE_URL+"/add_dependant"
            val helper = ApiHelper(requireContext())
            val body = JSONObject()
            body.put("surname", surname.text.toString())
            body.put("others", others.text.toString())
            body.put("dob", editTextDate.text.toString())
            body.put("gender", gender)
            body.put("member_id", member_id)
            helper.post(api, body, object : ApiHelper.CallBack {
                override fun onSuccess(result: JSONArray?) {
                }

                override fun onSuccess(result: JSONObject?) {
                    Toast.makeText(requireContext(), result.toString(),
                        Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(result: String?) {
                }
            });

        }//end listener

        return root

    }//end oncreate view



    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        // Create a date picker dialog and set the current date as the default selection
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = formatDate(year, month, day)
                editTextDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the date picker dialog
        datePickerDialog.show()
    }
    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}//last brace