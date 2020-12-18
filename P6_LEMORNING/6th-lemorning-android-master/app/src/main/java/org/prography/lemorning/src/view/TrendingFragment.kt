package org.prography.lemorning.src.view

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentTrendingBinding
import org.prography.lemorning.src.viewmodel.TrendingViewModel
import org.prography.lemorning.utils.components.SimpleMessageDialog

class TrendingFragment(override val layoutId: Int = R.layout.fragment_trending)
    : BaseFragment<FragmentTrendingBinding, TrendingViewModel>() {


    override fun getViewModel(): TrendingViewModel {
        return ViewModelProvider(this).get(TrendingViewModel::class.java)
    }

    override fun initView() {

        /* RecyclerView - 추천 For you*/
        binding.rvForyouTrending.adapter = viewmodel.forYouAdapter
        viewmodel.forYouList.observe(this, Observer(viewmodel.forYouAdapter::setItem))

        /* RecyclerView - Popular */
        binding.rvPopularTrending.adapter = viewmodel.popularAdapter
        viewmodel.popularList.observe(this, Observer (viewmodel.popularAdapter::setItem))

        /* 검색 클릭 */
        binding.clSearchContainer.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.clSearchContainer to "search_box",
                binding.ivSearchIcon to "search_icon"
            )
            findNavController().navigate(R.id.action_to_search, null, null, extras)
        }
        binding.mbtnPeriodPopularTrending.setOnClickListener { SimpleMessageDialog(
            context = requireContext(),
            message = getString(R.string.coming_soon)
        ).show() }

        /* Move To Song */
        viewmodel.moveToSong.observe(this, Observer { it.get()?.let {
            //ActivityOptions.makeSceneTransitionAnimation(activity, binding.)
            startActivity(Intent(activity, PlaySongActivity::class.java).apply { putExtra("songNo", it) })
        } })
    }


}