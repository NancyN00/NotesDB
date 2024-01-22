package com.example.roomdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.roomdb.MainActivity
import com.example.roomdb.R
import com.example.roomdb.adapter.NoteAdapter
import com.example.roomdb.databinding.FragmentHomeBinding
import com.example.roomdb.model.Note
import com.example.roomdb.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {



    private var homeBinding: FragmentHomeBinding ? = null
    private val binding get() =  homeBinding!!

    //require vm to connect with UI

    private lateinit var notesViewModel : NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        setupHomeRecyclerView()

        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    private fun updateUI(note : List<Note>?){
        if (note != null)
            if (note.isNotEmpty()){
                binding.emptyNotesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            }else{
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.VISIBLE
            }
    }

    private fun setupHomeRecyclerView(){
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        //display notes in the rv, we need to observe
        //with note as an argument update the note with availability of note
        activity?.let {
            notesViewModel.getAllNotes().observe(viewLifecycleOwner){note->
                noteAdapter.differ.submitList(note)
                updateUI(note)

            }
        }
    }

    //% indicates can be zero or zero character
    //search note by observing use of vm
    private fun searchNote(query: String?){
        val searchQuery = "%$query"

        notesViewModel.searchNote(searchQuery).observe(this){list->
            noteAdapter.differ.submitList(list)

        }
    }

    //query and submit then it shows. provides the results so keep false
    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    //as the user type, it shows the query
    //!null - not null meaning the user querry is present then search thenote and return true
    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText)
        }
        return true
    }

    //the app destroyed meaning the frag is no longer in use
    override fun onDestroy() {
        super.onDestroy()
        homeBinding =null
    }

    //initialize the menu
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       //remove an existing menu and inflate menu created in the directory
        //because we don't require submit button as search will filter (false part)
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    //if clicked on this menu
    //search logic won't be handled by menu host, we will create a new function for it
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}