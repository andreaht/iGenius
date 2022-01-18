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
import com.example.github.igenius.utils.setup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.udacity.project4.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class RepositoriesListFragment : BaseFragment() {
    // You want Dagger to provide an instance of ProjectsListViewModel from the graph
    @Inject
    override lateinit var _viewModel: RepositoriesListViewModel

    private lateinit var binding: FragmentRepositoriesBinding

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

        binding.refreshLayout.setOnRefreshListener { _viewModel.loadLocalRepos() }

        //link beer to bottom sheet dialog
        _viewModel.showRepositoryInfo.observe(viewLifecycleOwner, { repositoryDataItem ->
            repositoryDataItem?.let {
                //binding setup
                val myDrawerView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_layout, null)
                val bsdBinding = BottomSheetDialogLayoutBinding.inflate(layoutInflater, myDrawerView as ViewGroup, false)
                bsdBinding.repositoryDataItem = repositoryDataItem

                //show the view
                val bottomSheetDialog = BottomSheetDialog(requireContext());
                bottomSheetDialog.setContentView(bsdBinding.content)
                bottomSheetDialog.show()
            }
        })

        //Define and assign recyclerview beer adapter
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
        //load the reminders list on the ui
        _viewModel.loadLocalRepos()
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
