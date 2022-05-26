package de.hsfl.team46.campusflag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.hsfl.team46.campusflag.databinding.FragmentLobbyyBinding
import de.hsfl.team46.campusflag.viewmodels.ViewModel
import java.util.*
import kotlin.concurrent.timerTask


class LobbyyFragment : Fragment() {

    private val mainViewModel: ViewModel by activityViewModels()

    private var _binding: FragmentLobbyyBinding? = null
    private val binding get() = _binding!!

    private lateinit var manager : RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLobbyyBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel

        Timer().scheduleAtFixedRate(timerTask {
            mainViewModel.fetchPlayers()
        },0,10000)


        binding.startGameBtnLobby.setOnClickListener {
            findNavController().navigate(R.id.action_lobbyy_to_game)
        }

        binding.leaveBtnLobby.setOnClickListener {
            mainViewModel.removePlayer { isError ->
                if (!isError){
                    findNavController().navigate(R.id.action_lobbyyFragment_to_startFragment2)
                }
            }
        }

        return binding.root
    }
}