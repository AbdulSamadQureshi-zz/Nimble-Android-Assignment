package echo.com.surveys.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : androidx.fragment.app.Fragment() {
    protected abstract fun handleBundle()
}
