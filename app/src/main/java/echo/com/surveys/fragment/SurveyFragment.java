package echo.com.surveys.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import echo.com.surveys.databinding.LayoutSurveyFragmentBinding;
import echo.com.surveys.model.SurveyModel;
import echo.com.surveys.util.ImageUtil;

public class SurveyFragment extends BaseFragment {
    protected LayoutSurveyFragmentBinding binding;
    protected SurveyModel survey;

    private static final String KEY_SURVEY = "KEY_SURVEY";
    public static SurveyFragment getInstance(SurveyModel survey){
        SurveyFragment fragment = new SurveyFragment();
        Bundle bundle =new Bundle();
        bundle.putSerializable(KEY_SURVEY, survey);
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
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        binding.setSurvey(survey);
        ImageUtil.loadResizedImage(getActivity(), binding.image, survey.getHDUrl());
    }

    @Override
    protected void handleBundle() {
        survey = (SurveyModel) getArguments().getSerializable(KEY_SURVEY);
    }
}
