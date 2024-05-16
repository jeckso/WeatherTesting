package com.test.stfalcontest.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeckso.reddit.android.adapter.OnItemClickListener
import com.test.stfalcontest.databinding.FragmentDetailsBinding
import com.test.stfalcontest.view.base.BaseFragment
import com.test.stfalcontest.view.details.adapter.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment constructor() : BaseFragment<DetailsViewModel, FragmentDetailsBinding>(),
    OnItemClickListener<DetailVM> {

    override val viewModel: DetailsViewModel by viewModels()

    private val detailsAdapter = DetailsAdapter(this)

    val args: DetailsFragmentArgs by navArgs()


    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.setupRV()
        detailsAdapter.items = args.detailsList.toList()
        viewBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onItemClick(item: DetailVM, position: Int) {

    }

    private fun FragmentDetailsBinding.setupRV() {
        with(detailsRV) {
            adapter = detailsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

}