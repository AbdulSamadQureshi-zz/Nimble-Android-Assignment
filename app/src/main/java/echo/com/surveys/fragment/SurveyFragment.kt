package echo.com.surveys.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import echo.com.surveys.databinding.LayoutSurveyFragmentBinding
import echo.com.surveys.model.SurveyModel
import echo.com.surveys.util.ImageUtil


class SurveyFragment : BaseFragment() {
    lateinit var binding: LayoutSurveyFragmentBinding
    lateinit var survey: SurveyModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutSurveyFragmentBinding.inflate(inflater, container, false)
        handleBundle()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeResources()
    }

    protected fun initializeResources() {
        binding.survey = survey
        ImageUtil.loadResizedImage(activity!!, binding.image, survey.getHDUrl())
    }

    override fun handleBundle() {
        survey = arguments!!.getSerializable(KEY_SURVEY) as SurveyModel
    }

    companion object {

        private val KEY_SURVEY = "KEY_SURVEY"
        fun getInstance(survey: SurveyModel): SurveyFragment {
            val fragment = SurveyFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_SURVEY, survey)
            fragment.arguments = bundle
            return fragment
        }
    }
}
