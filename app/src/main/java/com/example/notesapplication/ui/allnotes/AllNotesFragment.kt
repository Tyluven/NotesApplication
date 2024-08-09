package com.example.notesapplication.ui.allnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentAllNotesBinding
import com.example.notesapplication.extensions.navigateToScreen
import com.example.notesapplication.helper.SpacingItemDecorator
import com.example.notesapplication.ui.notedetail.NoteDetailFragment
import com.example.notesapplication.ui.uimodel.NoteUiModel
import com.example.notesapplication.viewmodel.NoteViewModel

class AllNotesFragment : Fragment() {

    private var _binding: FragmentAllNotesBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel: NoteViewModel by viewModels()

    private val adapter by lazy { NoteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        initListener()
    }

    private fun initListener() = with(binding) {
        btnAddNote.setOnClickListener {
            navigateToScreen(destinationId = R.id.noteDetailFragment)
        }
        adapter.onItemClickCallback = {
            navigateToScreen(
                destinationId = R.id.noteDetailFragment,
                bundle = NoteDetailFragment.createBundle(note = it)
            )
        }
    }

    private fun initObservers() {
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            handleNoteState(notes)
        }
    }

    private fun handleNoteState(notes: List<NoteUiModel>?) = with(binding) {
        rvAllNotes.isVisible = !notes.isNullOrEmpty()
        layoutEmpty.root.isVisible = notes.isNullOrEmpty()
        if (!notes.isNullOrEmpty()) {
            adapter.submitList(notes)
        }
    }

    private fun initViews() = with(binding) {
        rvAllNotes.adapter = adapter
        rvAllNotes.addItemDecoration(
            SpacingItemDecorator(resources.getDimensionPixelOffset(R.dimen.spacing4))
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}