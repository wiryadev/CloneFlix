package com.wiryadev.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.wiryadev.core.utils.getErrorMessageByException

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater) -> VB
) : AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory.invoke(layoutInflater)
        setContentView(binding.root)
        initView()
        observeData()
    }

    abstract fun initView()

    open fun observeData() {}

    open fun showError(
        isErrorEnabled: Boolean,
        exception: Exception
    ) {
        if (isErrorEnabled) {
            Snackbar.make(binding.root, getErrorMessageByException(exception), Snackbar.LENGTH_SHORT).show()
        }
    }

}