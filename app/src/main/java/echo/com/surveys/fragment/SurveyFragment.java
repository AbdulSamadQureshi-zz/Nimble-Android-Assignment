package echo.com.surveys.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import echo.com.surveys.databinding.LayoutSurveyFragmentBinding;
import echo.com.surveys.model.Survey;
import echo.com.surveys.util.ImageUtil;

public class SurveyFragment extends BaseFragment {
    protected LayoutSurveyFragmentBinding binding;
    protected Survey survey;

    private static final String KEY_SURVEY = "KEY_SURVEY";
    public static SurveyFragment getInstance(Survey survey){
        SurveyFragment fragment = new SurveyFragment();
        Bundle bundle =new Bundle();
        bundle.putParcelable(KEY_SURVEY, survey);
        fragment.setArguments(bundle);
        return fragment;
    }


    public SurveyFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutSurveyFragmentBinding.inflate(inflater, container, false);
        handleBundle();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeResources();
    }

    protected void initializeResources() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        binding.setSurvey(survey);
        ImageUtil.loadResizedImage(getActivity(), binding.image, survey.getUrl(), (int)dpWidth, (int)dpHeight);
    }

    @Override
    protected void handleBundle() {
        survey = getArguments().getParcelable(KEY_SURVEY);
    }
}
