package com.example.roomdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.roomdb.databinding.FragmentEditNoteBinding

class EditNoteFragment : Fragment() {

    private lateinit var binding: FragmentEditNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return(binding.root)
    }
}