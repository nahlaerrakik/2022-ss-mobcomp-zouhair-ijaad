package de.hsfl.team46.campusflag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import de.hsfl.team46.campusflag.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    //globale initialisierung

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentStartBinding.inflate(inflater, container, false)
//        rootView = binding.root

        // Host Button

        binding.hostBtnStart.setOnClickListener {
            findNavController().navigate((R.id.action_start_to_create))
        }

        // Join Button
        binding.joinBtnStart.setOnClickListener {
            findNavController().navigate((R.id.action_start_to_join))
        }

        return binding.root
    }
}