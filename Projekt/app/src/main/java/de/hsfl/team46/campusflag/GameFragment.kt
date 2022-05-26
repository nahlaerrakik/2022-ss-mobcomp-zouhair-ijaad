package de.hsfl.team46.campusflag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsfl.team46.campusflag.databinding.FragmentGameBinding
import de.hsfl.team46.campusflag.enums.Colors
import de.hsfl.team46.campusflag.viewmodels.ViewModel
import java.util.*
import kotlin.concurrent.timerTask

class GameFragment : Fragment() {

    private val mainViewModel: ViewModel by activityViewModels()

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGameBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel

        binding.leaveBtnGame.setOnClickListener {
            mainViewModel.removePlayer { isError ->
                if (!isError){
                    findNavController().navigate(R.id.action_game_to_start)
                }
            }
        }

        Timer().scheduleAtFixedRate(timerTask {
            mainViewModel.fetchPoints { game ->
                for (pt in game.points!!) {
                    Log.d("pppppppppppppppppppp", pt.toString())

                    if (pt.team == -1){
                        binding.positionMarker.setColorFilter(Colors.GRAY.rgb)
                    }

                    binding.positionMarker.setColorFilter(Colors.YELLOW.rgb)

                    mainViewModel.points.observe(viewLifecycleOwner) {
                        val posLong = mainViewModel.getLongScaling(pt.long)
                        val posLat = mainViewModel.getLatScaling(pt.lat)

                        //mainViewModel.getLocationRepository().moveLocationMarker(posLong, posLat, binding.positionMarker)
                    }

                }
            }
        },0,10000)

        return binding.root
    }

//    private lateinit var rootView: View
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        rootView = inflater.inflate(R.layout.fragment_game, container, false)
//
//        val navController = findNavController()
//        val leaveBtn: Button = rootView.findViewById(R.id.leave_btn_game)
//
//        leaveBtn.setOnClickListener {
//            navController.navigate(R.id.action_game_to_start)
//        }
//
//        return rootView
//    }
}