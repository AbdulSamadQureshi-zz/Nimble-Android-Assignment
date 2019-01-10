package echo.com.surveys.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import echo.com.surveys.R
import echo.com.surveys.SurveyApplication
import echo.com.surveys.adapter.IndexAdapter
import echo.com.surveys.adapter.SurveyFragmentPagerAdapter
import echo.com.surveys.model.SurveyModel
import echo.com.surveys.util.SharedPrefUtility
import echo.com.surveys.view.CustomViewPager
import kotlinx.android.synthetic.main.app_bar_survey.*
import kotlinx.android.synthetic.main.content_survey.*
import kotlinx.android.synthetic.main.layout_survey_activity.*
import javax.inject.Inject


class SurveyActivity : BaseFragmentActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var pagerAdapter: SurveyFragmentPagerAdapter
    lateinit var indexAdapter: IndexAdapter
    lateinit var surveyViewModel: SurveyViewModel
    var lastSelectedPosition = 0

    //    var surveys: ArrayList<Survey> = ArrayList()
//    var indexes: ArrayList<Survey> = ArrayList()
    @Inject
    lateinit var sharedPrefUtility: SharedPrefUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_survey_activity)
        setSupportActionBar(toolbar)
        SurveyApplication.instance?.component()?.inject(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        surveyViewModel = ViewModelProviders.of(this@SurveyActivity).get(SurveyViewModel::class.java)
        if (isNetworkConnected(this)) {
            showProgress()
            loadSurveysForFirstTime()
        } else {
            Toast.makeText(this, "No Internet found, Showing cache list in the view", Toast.LENGTH_SHORT).show()
        }
        initPagerAdapter(ArrayList())
        initIndexAdapter(ArrayList())

        surveyViewModel.getAllSurveysFromDb().observe(this, Observer<List<SurveyModel>> { surveyList ->
            Log.e(SurveyActivity::class.java.javaClass.simpleName, surveyList.toString())
            hideProgres()
            pagerAdapter.addItems(surveyList)
            indexAdapter.addItems(surveyList)
        })
    }


    private fun loadSurveysForFirstTime(){
        if(sharedPrefUtility.auth != null) {
            surveyViewModel.getSurveysFromApiAndStore(sharedPrefUtility.auth?.accessToken!!)
        } else {
            surveyViewModel.getAccessToken(sharedPrefUtility)
        }
    }
    private fun initPagerAdapter(surveyList: List<SurveyModel>?) {
        pagerAdapter = SurveyFragmentPagerAdapter(getSupportFragmentManager(), surveyList!! as MutableList<SurveyModel>)
        viewPager.adapter = pagerAdapter
        viewPager.setOnSwipeOutListener(object : CustomViewPager.OnSwipeOutListener {
            override fun onSwipeOutAtEnd() {
                if(sharedPrefUtility.auth != null){
                    surveyViewModel.getSurveys(sharedPrefUtility.auth?.accessToken!!)
                } else {
                    surveyViewModel.getAccessToken(sharedPrefUtility)
                }

//                Toast.makeText(this@SurveyActivity,"End",Toast.LENGTH_SHORT).show()
            }

            override fun onSwipeOutAtStart() {
                loadSurveysForFirstTime()
//                Toast.makeText(this@SurveyActivity,"Start",Toast.LENGTH_SHORT).show()
            }
        })
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (pagerAdapter.count == 0) {
                    return
                }
//                indexes.get(lastSelectedPosition).isSelected= false
//                indexes.get(position).isSelected= true
                indexAdapter.indexList.get(lastSelectedPosition).isSelected = false
                indexAdapter.indexList.get(position).isSelected = true
                indexAdapter.notifyDataSetChanged()
                lastSelectedPosition = position
            }

        })
    }

    private fun initIndexAdapter(surveyList: List<SurveyModel>?) {
        recyclerView.layoutManager = LinearLayoutManager(this@SurveyActivity, LinearLayoutManager.VERTICAL, false)
        indexAdapter = IndexAdapter(surveyList as MutableList<SurveyModel>)
        recyclerView.adapter = indexAdapter

    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.survey, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_refresh -> {
                loadSurveysForFirstTime()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgres() {
        progressBar.visibility = View.GONE
    }


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

}
