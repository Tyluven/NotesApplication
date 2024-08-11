package com.example.notesapplication.ui.notedetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentNoteDetailBinding
import com.example.notesapplication.ui.BaseFragment
import com.example.notesapplication.ui.uimodel.NoteUiModel
import com.example.notesapplication.util.DialogUtil
import com.example.notesapplication.viewmodel.ExecuteDbResult
import com.example.notesapplication.viewmodel.NoteViewModel

class NoteDetailFragment : BaseFragment() {

    companion object {
        private const val NOTE_ARG = "NOTE_ARG"
        fun createBundle(note: NoteUiModel) = bundleOf(NOTE_ARG to note)

        fun getBundle(bundle: Bundle?) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle?.getParcelable(NOTE_ARG, NoteUiModel::class.java)
            } else {
                bundle?.getParcelable(NOTE_ARG)
            }
    }

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    private val noteModel by lazy { getBundle(arguments) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.executeDataResult.observe(viewLifecycleOwner) {
            when (it) {
                is ExecuteDbResult.Error -> {
                    hideLoading()
                    Toast.makeText(
                        context,
                        getString(R.string.some_thing_when_wrong), Toast.LENGTH_LONG
                    ).show()
                }

                is ExecuteDbResult.Success -> {
                    hideLoading()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun initListeners() = with(binding) {
        btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        edtInputNoteTitle.doOnTextChanged { _, _, _, _ ->
            handleBtnSaveState()
        }

        edtInputNoteContent.doOnTextChanged { _, _, _, _ ->
            handleBtnSaveState()
        }

        btnSave.setOnClickListener {
            noteModel?.copy(
                title = edtInputNoteTitle.text.toString(),
                content = edtInputNoteContent.text.toString(),
                lastUpdate = System.currentTimeMillis()
            )?.let {
                viewModel.update(it)
            } ?: run {
                viewModel.insert(
                    NoteUiModel(
                        title = edtInputNoteTitle.text.toString(),
                        content = edtInputNoteContent.text.toString()
                    )
                )
            }
        }

        noteModel?.let { model ->
            btnDelete.setOnClickListener {
                DialogUtil.showConfirmDeleteNoteDialog(context = context) {
                    viewModel.delete(model)
                }
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!edtInputNoteTitle.text.isNullOrEmpty() || !edtInputNoteContent.text.isNullOrEmpty()) {
                        DialogUtil.showConfirmDiscardNoteDialog(context = context) {
                            findNavController().popBackStack()
                        }
                    } else {
                        findNavController().popBackStack()
                    }
                }
            })

    }

    private fun handleBtnSaveState() = with(binding) {
        btnSave.isVisible =
            !edtInputNoteTitle.text?.trim().isNullOrEmpty() && !edtInputNoteContent.text?.trim()
                .isNullOrEmpty()
    }

    private fun initViews() = with(binding) {
        tvHeaderTitle.text = if (noteModel != null) {
            getString(R.string.editing_note)
        } else {
            getString(R.string.create_note)
        }
        btnDelete.isInvisible = noteModel == null

        noteModel?.let {
            edtInputNoteTitle.setText(it.title)
            edtInputNoteContent.setText(it.content)
        }
        handleBtnSaveState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}