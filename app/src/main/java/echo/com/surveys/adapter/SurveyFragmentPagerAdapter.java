package echo.com.surveys.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import echo.com.surveys.fragment.SurveyFragment;
import echo.com.surveys.model.SurveyModel;

import java.util.List;

public class SurveyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<SurveyModel> surveys;

    public SurveyFragmentPagerAdapter(FragmentManager fm, List<SurveyModel> surveys) {
        super(fm);
        this.surveys = surveys;
    }

    @Override
    public Fragment getItem(int position) {
        return SurveyFragment.getInstance(surveys.get(position));
    }

    @Override
    public int getCount() {
        return surveys.size();
    }
}

