package com.blogspot.kannanpvm007.material3example

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.blogspot.kannanpvm007.material3example.databinding.ActivityMainBinding
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

//        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.inputDob.setOnClickListener {
            showCalder2()
        }


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


}