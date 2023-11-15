package com.example.flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flo.databinding.FragmentSavedMusicFileBinding

class SavedMusicFileFragment : Fragment() {
    lateinit var binding: FragmentSavedMusicFileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedMusicFileBinding.inflate(inflater, container, false)

        return binding.root
    }
}