package com.example.bottomnavigationviewpagger2retrofit2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.example.bottomnavigationviewpagger2retrofit2.databinding.ActivityMainBinding
import com.example.bottomnavigationviewpagger2retrofit2.earth.model.EarthData
import com.example.bottomnavigationviewpagger2retrofit2.earth.model.EarthViewModel
import com.example.bottomnavigationviewpagger2retrofit2.earth.view.ViewPagerEarthFragmentAdapter
import com.example.bottomnavigationviewpagger2retrofit2.mars.model.MarsData
import com.example.bottomnavigationviewpagger2retrofit2.mars.model.MarsViewModel
import com.example.bottomnavigationviewpagger2retrofit2.mars.view.ViewPagerMarsFragmentAdapter
import com.example.bottomnavigationviewpagger2retrofit2.pod.model.PodData
import com.example.bottomnavigationviewpagger2retrofit2.pod.model.PodViewModel
import com.example.bottomnavigationviewpagger2retrofit2.pod.view.ViewPagerPodFragmentAdapter
import com.example.bottomnavigationviewpagger2retrofit2.sun.model.SunData
import com.example.bottomnavigationviewpagger2retrofit2.sun.model.SunViewModel
import com.example.bottomnavigationviewpagger2retrofit2.sun.view.ViewPagerSunFragmentAdapter
import com.example.bottomnavigationviewpagger2retrofit2.util.DepthPageTransformer
import com.example.bottomnavigationviewpagger2retrofit2.settings.SettingsFragment
import java.text.SimpleDateFormat
import java.util.*

const val ThemeDefault = "DEFAULT"
const val ThemeSpace = "SPACE"
const val ThemeMoon = "MOON"
const val ThemeMars = "MARS"

class MainActivity : AppCompatActivity() {

    private val KEY_SP = "sp"
    private val KEY_CURRNT_THEME = "current_theme"

    private lateinit var binding: ActivityMainBinding

    private lateinit var urlPodList: ArrayList<String>
    private lateinit var datePodList: ArrayList<String>
    private lateinit var explanationPodList: ArrayList<String>
    private lateinit var podViewModel: PodViewModel

    private lateinit var earthViewModel: EarthViewModel
    private lateinit var urlEarthList: ArrayList<String>
    private lateinit var dateEarthList: ArrayList<String>

    private lateinit var marsViewModel: MarsViewModel
    private lateinit var urlMarsList: ArrayList<String>
    private lateinit var dateMarsList: ArrayList<String>

    private lateinit var sunViewModel: SunViewModel
    private lateinit var startDateSunList: ArrayList<String>
    private lateinit var peakDateSunList: ArrayList<String>
    private lateinit var endDateSunList: ArrayList<String>
    private lateinit var classTypeSunList: ArrayList<String>
    private lateinit var sourceLocationSunList: ArrayList<String>

    private lateinit var dateOfCalendar: Calendar
    private lateinit var startDate: String
    private lateinit var endDate: String
    private lateinit var dateOfMarsRoverPictures: String
    private lateinit var startDateOfSunFlare: String
    private lateinit var endDateOfSunFlare: String
    private var typeLoad: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getStyle(getCurrentTheme()))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        urlPodList = arrayListOf()
        datePodList = arrayListOf()
        explanationPodList = arrayListOf()

        urlEarthList = arrayListOf()
        dateEarthList = arrayListOf()

        urlMarsList = arrayListOf()
        dateMarsList = arrayListOf()

        startDateSunList = arrayListOf()
        endDateSunList = arrayListOf()
        peakDateSunList = arrayListOf()
        classTypeSunList = arrayListOf()
        sourceLocationSunList = arrayListOf()

        dateOfCalendar = Calendar.getInstance()

        val currentYear = dateOfCalendar.get(Calendar.YEAR)
        val currentMonth = dateOfCalendar.get(Calendar.MONTH)
        startDateOfSunFlare = "$currentYear-${currentMonth + 1}-1"
        endDateOfSunFlare = when (currentMonth) {
            0, 2, 4, 6, 7, 9, 11 -> {
                "$currentYear-${currentMonth + 1}-31"
            }
            3, 5, 8, 10 -> {
                "$currentYear-${currentMonth + 1}-30"
            }
            else -> {
                "$currentYear-${currentMonth + 1}-28"
            }
        }

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        endDate = simpleDateFormat.format(dateOfCalendar.time)
        dateOfCalendar.add(Calendar.DATE, -1)
        dateOfMarsRoverPictures = simpleDateFormat.format(dateOfCalendar.time)
        dateOfCalendar.add(Calendar.DATE, -5)
        startDate = simpleDateFormat.format(dateOfCalendar.time)

        podViewModel = ViewModelProviders.of(this).get(PodViewModel::class.java)
        podViewModel.getLiveData().observe(this) { renderPodData(it) }
        podViewModel.sendServerRequest(startDate, endDate)

        earthViewModel = ViewModelProviders.of(this).get(EarthViewModel::class.java)
        earthViewModel.getLiveData().observe(this) { renderEarthData(it) }
        earthViewModel.sendServerRequest()

        marsViewModel = ViewModelProviders.of(this).get(MarsViewModel::class.java)
        marsViewModel.getLiveData().observe(this) { renderMarsData(it) }
        marsViewModel.sendServerRequest(dateOfMarsRoverPictures)

        sunViewModel = ViewModelProviders.of(this).get(SunViewModel::class.java)
        sunViewModel.getLiveData().observe(this) { renderSunDate(it) }
        sunViewModel.sendServerRequest(startDateOfSunFlare, endDateOfSunFlare)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fragmentContainer.setPageTransformer(DepthPageTransformer())

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigation_view_menu_pod -> {
                    urlPodList.reverse()
                    datePodList.reverse()
                    explanationPodList.reverse()
                    binding.fragmentContainer.adapter = ViewPagerPodFragmentAdapter(
                        this,
                        urlPodList,
                        datePodList,
                        explanationPodList
                    )
                    true
                }
                R.id.bottom_navigation_view_menu_earth -> {
                    binding.fragmentContainer.adapter =
                        ViewPagerEarthFragmentAdapter(this, urlEarthList, dateEarthList)
                    true
                }
                R.id.bottom_navigation_view_menu_mars -> {
                    binding.fragmentContainer.adapter =
                        ViewPagerMarsFragmentAdapter(this, urlMarsList, dateMarsList)
                    true
                }
                R.id.bottom_navigation_view_menu_sun -> {
                    binding.fragmentContainer.adapter = ViewPagerSunFragmentAdapter(
                        this,
                        startDateSunList,
                        peakDateSunList,
                        endDateSunList,
                        classTypeSunList,
                        sourceLocationSunList,
                        typeLoad
                    )
                    true
                }
                else -> false
            }
        }

        /*urlListStart = arrayListOf()
        urlListStart.add("https://apod.nasa.gov/apod/image/2208/Crab_HubbleChandraSpitzer_1080.jpg")
        binding.fragmentContainer.adapter = ViewPagerPodFragmentAdapter(this, urlListStart)*/
        //binding.bottomNavigationView.selectedItemId = R.id.bottom_navigation_view_menu_pod
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                this.supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance(), "settings").addToBackStack("").commit()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderPodData(podData: PodData?) {
        when (podData) {
            is PodData.Success -> {
                for (element in podData.serverResponseData) {
                    element.url?.let { urlPodList.add(it) }
                    element.date?.let { datePodList.add(it) }
                    element.explanation?.let { explanationPodList.add(it) }
                }
            }
            is PodData.Loading -> {

            }
            is PodData.Error -> {
                //Toast.makeText(this, "PodData.Error", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    private fun renderEarthData(earthData: EarthData?) {
        when (earthData) {
            is EarthData.Success -> {
                for (element in earthData.serverResponseData) {
                    element.image.let { urlEarthList.add(it) }
                    element.date.let { dateEarthList.add(it) }
                }
            }
            is EarthData.Loading -> {

            }
            is EarthData.Error -> {
                Toast.makeText(this, "EarthData.Error", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    private fun renderMarsData(marsData: MarsData?) {
        when (marsData) {
            is MarsData.Success -> {
                for (element in marsData.serverResponseData.photos) {
                    element.let { urlMarsList.add(it.img_src) }
                    element.let { dateMarsList.add(it.earth_date) }
                }
            }
            is MarsData.Loading -> {

            }
            is MarsData.Error -> {
                Toast.makeText(this, "MarsData.Error", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    private fun renderSunDate(sunData: SunData?) {
        when (sunData) {
            is SunData.Success -> {
                for (element in sunData.serverResponseData) {
                    element.beginTime.let { startDateSunList.add(it) }
                    element.peakTime.let { peakDateSunList.add(it) }
                    element.endTime.let { endDateSunList.add(it) }
                    element.classType.let { classTypeSunList.add(it) }
                    element.sourceLocation.let { sourceLocationSunList.add(it) }
                }
                typeLoad = 1
            }
            is SunData.Loading -> {

            }
            is SunData.Error -> {
                Toast.makeText(this, "SunData.Error", Toast.LENGTH_LONG).show()
                typeLoad = 0
            }
            else -> {}
        }
    }

    fun setCurrentTheme(currentTheme: String) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CURRNT_THEME, currentTheme)
        editor.apply()
    }

    private fun getCurrentTheme(): String? {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getString(KEY_CURRNT_THEME, "")
    }

    private fun getStyle(currentTheme: String?): Int {
        return when(currentTheme) {
            ThemeSpace -> R.style.Theme_NasaPictureOfTheDayCosmic
            ThemeMoon -> R.style.Theme_NasaPictureOfTheDayMoon
            ThemeMars -> R.style.Theme_NasaPictureOfTheDayMars
            else -> R.style.Theme_NasaPictureOfTheDay
        }
    }
}