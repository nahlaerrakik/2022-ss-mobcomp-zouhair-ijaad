package de.hsfl.team.campusflag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class LobbyFragment : Fragment() {

    private lateinit var rootView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_lobby, container, false)

        var startBtn : Button = rootView.findViewById(R.id.start_game_btn_lobby)
        var leaveBtn : Button = rootView.findViewById(R.id.leave_btn_lobby)

        val navController = findNavController()

        leaveBtn.setOnClickListener {
            navController.navigate(R.id.action_lobby_to_start)
        }

        startBtn.setOnClickListener {

            navController.navigate(R.id.action_lobby_to_game)
        }





        return rootView
    }

}

