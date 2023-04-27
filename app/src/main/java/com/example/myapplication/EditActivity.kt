package com.example.myapplication

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityEditBinding
import com.example.myapplication.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )




        binding.birthdateLayer.setOnClickListener {
            val listener = OnDateSetListener{ _, year, month, dayOfMonth ->
                binding.birthdateTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this,
                listener,
                2000,1,1
            ).show()
        }

        binding.warningCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.warningEditText.isVisible = isChecked
        }

        binding.warningEditText.isVisible = binding.warningCheckBox.isChecked

        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }

    }




    private fun saveData(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()){
            putString(NAME, binding.nameEditText.text.toString())
            putString(BLOOD_TYPE,getBloodType() )
            putString(EMERGENCY_CONTACT, binding.emergencyContactEditText.text.toString())
            putString(BIRTHDATE, binding.birthdateTextView.text.toString())
            putString(WARNING,getWarning() )
            apply()
        }

        Toast.makeText(this, "저장을 완료 했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String{
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    private fun getWarning(): String{
        return if(binding.warningCheckBox.isChecked) binding.warningEditText.text.toString() else ""
    }








}