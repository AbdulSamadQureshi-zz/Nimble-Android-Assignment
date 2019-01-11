package echo.com.surveys.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import echo.com.surveys.fragment.SurveyFragment
import echo.com.surveys.model.SurveyModel

class SurveyFragmentPagerAdapter(fm: androidx.fragment.app.FragmentManager, private val surveys: MutableList<SurveyModel>) :
    androidx.fragment.app.FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return SurveyFragment.getInstance(surveys[position])
    }

    override fun getCount(): Int {
        return surveys.size
    }

    fun addItems(newItems: List<SurveyModel>?) {
        if (newItems != null && newItems.isNotEmpty()) {
            surveys.clear()
            surveys.addAll(newItems)
            notifyDataSetChanged()
        }
    }
}

