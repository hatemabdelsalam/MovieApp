package com.etax.movieapp.core.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    open fun variableInitialization(view: View) {}
    open fun setupListeners() {}
    open fun setupObserve() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        variableInitialization(view)
        setupListeners()
        setupObserve()
    }
}