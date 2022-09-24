package com.wiryadev.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.wiryadev.core.utils.getErrorMessageByException

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingFactory.invoke(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun initView()

    open fun observeData() {}

    open fun showError(
        isErrorEnabled: Boolean,
        exception: Exception
    ) {
        if (isErrorEnabled) {
            Snackbar.make(
                binding.root,
                requireContext().getErrorMessageByException(exception),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}