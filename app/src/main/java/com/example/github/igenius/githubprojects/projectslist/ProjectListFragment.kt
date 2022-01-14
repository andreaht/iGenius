package com.example.github.igenius.githubprojects.projectslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import com.example.github.igenius.GithubProjectsApplication
import com.example.github.igenius.R
import com.example.github.igenius.authentication.AuthenticationActivity
import com.example.github.igenius.databinding.FragmentProjectsBinding
import com.example.github.igenius.githubprojects.ProjectsActivity
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import com.udacity.project4.utils.setTitle
import com.udacity.project4.utils.setup
import javax.inject.Inject

class ProjectListFragment : BaseFragment() {
    // You want Dagger to provide an instance of ProjectsListViewModel from the graph
    @Inject
    override lateinit var _viewModel: ProjectsListViewModel

    private lateinit var binding: FragmentProjectsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as GithubProjectsApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectsBinding.inflate(
            inflater, container, false
        )
        binding.viewModel = _viewModel

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))

        binding.refreshLayout.setOnRefreshListener { _viewModel.loadReminders() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        //load the reminders list on the ui
        _viewModel.loadReminders()
    }

    private fun setupRecyclerView() {
        val adapter = ProjectsListAdapter {
        }

//        setup the recycler view using the extension function
        binding.reminderssRecyclerView.setup(adapter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                // add the logout implementation
                val intent = Intent(context, AuthenticationActivity::class.java)
                    .putExtra("requestCode", ProjectsActivity.SIGN_OUT_REQUEST_CODE)
                startActivityForResult(intent, ProjectsActivity .SIGN_IN_REQUEST_CODE)
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
