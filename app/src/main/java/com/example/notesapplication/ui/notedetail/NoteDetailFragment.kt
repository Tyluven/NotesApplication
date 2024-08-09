package com.example.notesapplication.ui.notedetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notesapplication.databinding.FragmentNoteDetailBinding
import com.example.notesapplication.ui.uimodel.NoteUiModel
import com.example.notesapplication.viewmodel.NoteViewModel

class NoteDetailFragment : Fragment() {

    companion object {
        private const val NOTE_ARG = "NOTE_ARG"
        fun createBundle(note: NoteUiModel) = bundleOf(NOTE_ARG to note)

        fun getBundle(bundle: Bundle) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(NOTE_ARG, NoteUiModel::class.java)
        } else {
            bundle.getParcelable(NOTE_ARG)
        }
    }

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}