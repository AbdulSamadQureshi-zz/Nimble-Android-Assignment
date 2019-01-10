package echo.com.surveys.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import echo.com.surveys.fragment.SurveyFragment
import echo.com.surveys.model.SurveyModel

class SurveyFragmentPagerAdapter(fm: FragmentManager, private val surveys: MutableList<SurveyModel>) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return SurveyFragment.getInstance(surveys[position])
    }

    override fun getCount(): Int {
        return surveys.size
    }

    fun addItems(newItems: List<SurveyModel>?) {
        if (newItems != null && newItems.size > 0) {
            surveys.addAll(newItems)
            notifyDataSetChanged()
        }
    }
}

