package echo.com.surveys.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import echo.com.surveys.R
import echo.com.surveys.SurveyApplication
import echo.com.surveys.adapter.IndexAdapter
import echo.com.surveys.adapter.SurveyFragmentPagerAdapter
import echo.com.surveys.model.Auth
import echo.com.surveys.model.AuthRequest
import echo.com.surveys.model.Survey
import echo.com.surveys.rest.ApiUtils
import echo.com.surveys.util.DialogUtils
import echo.com.surveys.util.SharedPrefUtility
import kotlinx.android.synthetic.main.app_bar_survey.*
import kotlinx.android.synthetic.main.content_survey.*
import kotlinx.android.synthetic.main.layout_survey_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject




class SurveyActivity : BaseFragmentActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var pagerAdapter: SurveyFragmentPagerAdapter
    lateinit var indexAdapter: IndexAdapter
    var lastSelectedPosition = 0

    var surveys: ArrayList<Survey> = ArrayList()
    var indexes: ArrayList<Survey> = ArrayList()
    @Inject
    lateinit var sharedPrefUtility: SharedPrefUtility
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_survey_activity)
        setSupportActionBar(toolbar)
        SurveyApplication.getInstance().component().inject(this)

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
        initPagerAdapter()
        initIndexAdapter()
        reloadSurveys()
    }

    private fun initPagerAdapter() {
        pagerAdapter = SurveyFragmentPagerAdapter(getSupportFragmentManager(), surveys)
        viewPager.adapter = pagerAdapter
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                indexes.get(lastSelectedPosition).isSelected= false
                indexes.get(p0).isSelected= true
                lastSelectedPosition = p0
                indexAdapter.notifyDataSetChanged()
            }

        })
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(p0: Int) {
//            }
//
//            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//            }
//
//            override fun onPageSelected(p0: Int) {
//                indexes.get(lastSelectedPosition).isSelected= false
//                indexes.get(p0).isSelected= true
//                lastSelectedPosition = p0
//                indexAdapter.notifyDataSetChanged()
//            }
//        })
    }

    private fun initIndexAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this@SurveyActivity, LinearLayoutManager.VERTICAL, false)
        indexAdapter = IndexAdapter(indexes)
        recyclerView.adapter = indexAdapter

    }


    fun getAccessToken() {
        val authRequest = AuthRequest()
        showProgress()
        ApiUtils.getAPIService(this).getToken(authRequest).enqueue(object : Callback<Auth> {
            override fun onFailure(call: Call<Auth>, t: Throwable) {
                hideProgres()
                DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
            }

            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                hideProgres()
                if (response.body() != null) {
                    sharedPrefUtility?.updateAuth(response.body())
                    loadSurveys(false)
                }
            }

        })
    }

    fun loadSurveys(showProgress: Boolean) {
        val token = sharedPrefUtility?.auth?.accessToken
        if (showProgress) {
            showProgress()
        }
        ApiUtils.getAPIService(this).getSurveys(token).enqueue(object : Callback<List<Survey>> {
            override fun onFailure(call: Call<List<Survey>>, t: Throwable) {
                hideProgres()
                DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
            }

            override fun onResponse(call: Call<List<Survey>>, response: Response<List<Survey>>) {
                hideProgres()
                if (response.body() != null) {
                    if(surveys.size == 0 && response.body()!!.isNotEmpty()){
                        response.body()!![0].isSelected = true
                    }
                    updateIndexRecyclerView(response.body()!!)
                    updateViewPager(response.body()!!)
                } else {
                    DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
                }
            }
        })

    }

    fun updateViewPager(newSurveys: List<Survey>) {
        surveys.addAll(newSurveys)
        pagerAdapter.notifyDataSetChanged()
    }

    fun updateIndexRecyclerView(newIndixes: List<Survey>) {
        indexes.addAll(newIndixes)
        indexAdapter.notifyDataSetChanged()
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
                viewPager.setCurrentItem(0, false)
                reloadSurveys()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun reloadIndexes() {
        indexes.clear()
        indexAdapter.notifyDataSetChanged()
    }

    fun reloadSurveys() {
        if (surveys.size > 0) {
            surveys.clear()
            pagerAdapter.notifyDataSetChanged()
            reloadIndexes()
        }

        val auth = sharedPrefUtility?.auth
        if (auth != null && !TextUtils.isEmpty(auth.accessToken)) {
            loadSurveys(true)
        } else {
            getAccessToken()
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
}
