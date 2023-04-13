package com.blogspot.kannanpvm007.material3example

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.blogspot.kannanpvm007.material3example.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        binding.inputDob.setOnClickListener {
            showCalder2()
        }
        setBusDropDown()
        setClassDropDown()

        binding.submit.setOnClickListener {
            submit()
        }

    }
private fun submit(){
    if (isImportedDataFiled()){}
}
    private fun isImportedDataFiled(): Boolean {
        if (getNameFiled()) {
            if (getFatherNameFiled()) {
                if (getModerNameFiled()) {
                    if (isGenderChecked()) {
                        if (isMobileFiled()) {
                            if (isBusFiled()) {
                                if (isClassFiled()) {
                                    if (isAddressFiled()) {
                                        if (isPinCodeFiled()) {
                                            return true
                                        } else showAlert(this, "", "Please Ender Pin Code")
                                        binding.pinCode.requestFocus()

                                    } else showAlert(this, "", "Please Ender Address")
                                    binding.address.requestFocus()
                                } else showAlert(this, "", "Please Select Class")
                                binding.inputClass.performClick()
                            } else showAlert(this, "", "Please Select Bus")
                            binding.inputBus.performClick()
                        } else showAlert(this, "", "Please Ender Phone Number")
                        binding.mobile.requestFocus()

                    } else showAlert(this, "", "Please Select Gender")

                } else showAlert(this, "", "Please Ender Mother Name")
                binding.Mname.requestFocus()
            } else showAlert(this, "", "Please Ender Father Name")
                binding.Fname.requestFocus()
        } else showAlert(this, "", "Please Ender Name")
                binding.name.requestFocus()

        return false
    }

    private fun showCalder2() {

        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
// Set the maximum date to 2 years ago from the current date
        calendar.add(Calendar.YEAR, -2)
        val maxDate = calendar.timeInMillis
// Set the minimum date to 10 years ago from the current date
        calendar.add(Calendar.YEAR, -9)
        val minDate = calendar.timeInMillis

        val datePickerDialog = DatePickerDialog(this, { _, year, month, day_of_month ->
            calendar[Calendar.YEAR] = year
            val monthCrate=month + 1
            calendar[Calendar.DAY_OF_MONTH] = day_of_month

            val sendFromDate = "$day_of_month/$monthCrate/$year"
            Log.d("TAG", "showCalder2: $sendFromDate")
            Log.d("TAG", "showCalder2: age: ${getAge(sendFromDate)}")
            binding.age.setText(getAge(sendFromDate).toString())
        }, year, month, day)
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate
        datePickerDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
    private fun getAge(dobString: String): Int {
        var date: Date? = null
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        try {
            date = sdf.parse(dobString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date == null) return 0
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob.time = date
        val year = dob[Calendar.YEAR]
        val month = dob[Calendar.MONTH]
        val day = dob[Calendar.DAY_OF_MONTH]
        dob[year, month + 1] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        Log.d("TAG", "getAge: today[Calendar.YEAR] :"+today[Calendar.YEAR])
        Log.d("TAG", "getAge: dob[Calendar.YEAR] :"+dob[Calendar.YEAR])
        Log.d("TAG", "getAge: today[Calendar.DAY_OF_YEAR] :"+today[Calendar.DAY_OF_YEAR])
        Log.d("TAG", "getAge: dob[Calendar.DAY_OF_YEAR] :"+dob[Calendar.DAY_OF_YEAR])
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        return age
    }
    private fun setBusDropDown() {
        val languages = arrayListOf<String>("tamil", "iCliniq", "Rx", "patient")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, languages
        )
        binding.busFilledExposedDropdown.setAdapter(adapter)
    }
    private fun setClassDropDown() {
        val languages = arrayListOf<String>("I", "II", "III", "IV","V")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, languages
        )
        binding.classExposedDropdown.setAdapter(adapter)
    }


    /**validate data*/
    private fun getNameFiled()= (binding.name.text.toString() != "")
    private fun getFatherNameFiled()= (binding.Fname.text.toString() != "")
    private fun getModerNameFiled()= (binding.Mname.text.toString() != "")
    private fun isGenderChecked()= (binding.radioGroup1.checkedRadioButtonId !=-1)
    private fun isMobileFiled()= (binding.mobile.text.toString() != "")
    private fun isBusFiled()= (binding.busFilledExposedDropdown.text.toString() !="")
    private fun isClassFiled()= (binding.classExposedDropdown.text.toString() !="")
    private fun isAddressFiled()= (binding.address.text.toString() !="")
    private fun isPinCodeFiled()= (binding.pinCode.text.toString() !="")
}

/**make this extention*/
private fun showAlert(context: Context, title:String, message:String){
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("OK") { dialog, which -> dialog.dismiss()   }
        .show()
}