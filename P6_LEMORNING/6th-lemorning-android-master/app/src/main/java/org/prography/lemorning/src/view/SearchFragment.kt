package org.prography.lemorning.src.view

import android.view.MotionEvent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentSearchBinding
import org.prography.lemorning.src.viewmodel.SearchViewModel
import org.prography.lemorning.utils.components.SimpleMessageDialog

class SearchFragment(override val layoutId: Int = R.layout.fragment_search) : BaseFragment<FragmentSearchBinding, SearchViewModel>() {


    override fun getViewModel(): SearchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

    override fun initView() {

        /* RecyclerView - Category */
        binding.rvCategorySearch.adapter = viewmodel.categoryAdapter
        viewmodel.categoryList.observe(this, Observer (viewmodel.categoryAdapter::setItem))
        binding.rvCategorySearch.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                if (e.action == MotionEvent.ACTION_UP) {
                    SimpleMessageDialog(
                        context = context!!,
                        message = getString(R.string.coming_soon)
                    ).show()
                }
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean = true

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }

        })

        /* Set On Click Listener */
        binding.mbtnCloseSearch.setOnClickListener { findNavController().popBackStack() }
        binding.clSearchContainer.setOnClickListener { SimpleMessageDialog(
            context = requireContext(),
            message = getString(R.string.coming_soon)
        ).show() }
    }
}