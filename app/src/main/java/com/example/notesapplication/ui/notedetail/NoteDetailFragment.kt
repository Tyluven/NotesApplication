package com.example.notesapplication.ui.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notesapplication.databinding.FragmentNoteDetailBinding

class NoteDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NoteDetailFragment()
    }

    private var _binding: FragmentNoteDetailBinding? = null

    private val binding get() = _binding!!

    private val viewModel: NoteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}