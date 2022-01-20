package com.example.github.igenius.githubrepositories.repositorieslist

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import com.example.github.igenius.GithubApplication
import com.example.github.igenius.R
import com.example.github.igenius.UserManager
import com.example.github.igenius.databinding.BottomSheetDialogLayoutBinding
import com.example.github.igenius.databinding.FragmentRepositoriesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.udacity.project4.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class RepositoriesListFragment : BaseFragment() {
    // You want Dagger to provide an instance of ProjectsListViewModel from the graph
    @Inject
    override lateinit var _viewModel: RepositoriesListViewModel

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var userManager: UserManager

    private lateinit var binding: FragmentRepositoriesBinding
    private lateinit var bsdBinding: BottomSheetDialogLayoutBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as GithubApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoriesBinding.inflate(
            inflater, container, false
        )
        binding.viewModel = _viewModel
//
        binding.refreshLayout.setOnRefreshListener {
            _viewModel.loadRepositories()
            binding.refreshLayout.isRefreshing = false
        }

        //link repository to bottom sheet dialog
        _viewModel.showRepositoryInfo.observe(viewLifecycleOwner, { repositoryDataItem ->
            repositoryDataItem?.let {
                //binding setup
                val myDrawerView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_layout, null)
                Timber.i("usermanager avatar: ${userManager.user.avatar_url}")
                bsdBinding = BottomSheetDialogLayoutBinding.inflate(layoutInflater, myDrawerView as ViewGroup, false)
                bsdBinding.repositoryDataItem = repositoryDataItem
                bsdBinding.userManager = userManager

                //star button listener
                bsdBinding.customButton.setOnClickListener {
                    _viewModel.starRepository(bsdBinding.repoName.text.toString())
                }

                //show the view
                val bottomSheetDialog = BottomSheetDialog(requireContext());
                bottomSheetDialog.setContentView(bsdBinding.content)
                bottomSheetDialog.show()
            }
        })

        //change text of star/unstar custom button
        _viewModel.showRepositoryInfoStarred.observe(viewLifecycleOwner, { starred ->
            if (this::bsdBinding.isInitialized) {
                if (starred) {
                    bsdBinding.customButton.setText(R.string.unstar)
                    bsdBinding.customButton.setButtonBackgroundColor(
                        ContextCompat.getColor(requireContext(),R.color.colorStar))
                }else {
                    bsdBinding.customButton.setText(R.string.star)
                    bsdBinding.customButton.setButtonBackgroundColor(
                        ContextCompat.getColor(requireContext(),R.color.colorPrimary))
                }
            }
        })

        _viewModel.reposList.observe(viewLifecycleOwner, {
            if (binding.motionLayout.currentState == R.id.start)
                binding.motionLayout.transitionToEnd()
        })

        //Define and assign recyclerview repository adapter
        binding.recyclerView.adapter = RepositoriesListAdapter(RepoListener {
            Timber.i("repo clicked: %s", it.title)
            _viewModel.onRepositoryClicked(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        //load the repositories list on the ui
        _viewModel.loadRepositories()
    }
}
