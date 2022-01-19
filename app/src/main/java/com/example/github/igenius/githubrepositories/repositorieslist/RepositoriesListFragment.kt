package com.example.github.igenius.githubrepositories.repositorieslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import com.example.github.igenius.GithubApplication
import com.example.github.igenius.R
import com.example.github.igenius.authentication.AuthenticationActivity
import com.example.github.igenius.databinding.BottomSheetDialogLayoutBinding
import com.example.github.igenius.databinding.FragmentRepositoriesBinding
import com.example.github.igenius.githubrepositories.RepositoriesActivity
import com.example.github.igenius.utils.setDisplayHomeAsUpEnabled
import com.example.github.igenius.utils.setTitle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.udacity.project4.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class RepositoriesListFragment : BaseFragment() {
    // You want Dagger to provide an instance of ProjectsListViewModel from the graph
    @Inject
    override lateinit var _viewModel: RepositoriesListViewModel

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

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))

        binding.refreshLayout.setOnRefreshListener { _viewModel.loadRepositories() }

        //link repository to bottom sheet dialog
        _viewModel.showRepositoryInfo.observe(viewLifecycleOwner, { repositoryDataItem ->
            repositoryDataItem?.let {
                //binding setup
                val myDrawerView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_layout, null)
                bsdBinding = BottomSheetDialogLayoutBinding.inflate(layoutInflater, myDrawerView as ViewGroup, false)
                bsdBinding.repositoryDataItem = repositoryDataItem

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
                if (starred)
                    bsdBinding.customButton.setText(R.string.unstar)
                else
                    bsdBinding.customButton.setText(R.string.star)
            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                // add the logout implementation
                val intent = Intent(context, AuthenticationActivity::class.java)
                    .putExtra("requestCode", RepositoriesActivity.SIGN_OUT_REQUEST_CODE)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
//        display logout as menu item
        inflater.inflate(R.menu.main_menu, menu)
    }

}
