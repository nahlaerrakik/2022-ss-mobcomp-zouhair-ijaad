package de.hsfl.team46.campusflag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.hsfl.team46.campusflag.databinding.FragmentLobbyyBinding
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

//        manager = LinearLayoutManager(context)
//        var data = listOf(Player(24911,"wa7da",1,null,null),
//                            Player(24911,"tania",2,null,null))
//        binding.recyclerView.apply {
//            adapter = RecyclerViewAdapter(data)
//            layoutManager = manager
//
//        }
        var Players =  Timer().scheduleAtFixedRate(timerTask {
            mainViewModel.fetchPlayers()
        },2000,2)

        binding.fetchPlayersBtnLobby.setOnClickListener {
            Players.toString()
            Log.e("HA LFETCH MEN DAK L PLAYERS DIAL LOBBY",Players.toString())
        }

        binding.startGameBtnLobby.setOnClickListener {
            findNavController().navigate(R.id.action_lobbyy_to_game)
        }

        binding.leaveBtnLobby.setOnClickListener {
            findNavController().navigate(R.id.action_lobbyyFragment_to_startFragment2)
        }

//        val delay = 0 // delay for 0 sec.
//
//        val period = 10000 // repeat every 10 sec.
//
//        val timer = Timer()
//        timer.scheduleAtFixedRate(object : TimerTask() {
//            fun run() {
//                //Call function
//            }
//        }, delay, period)



        return binding.root
    }
}