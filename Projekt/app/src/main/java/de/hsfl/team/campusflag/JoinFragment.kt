package de.hsfl.team.campusflag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


class JoinFragment : Fragment() {

    private lateinit var rootView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_join, container, false)

        var navController = findNavController()

        var joinGameBtn : Button = rootView.findViewById(R.id.join_game_btn_join)
        var cancelBtn : Button = rootView.findViewById(R.id.cancel_btn_join)

        joinGameBtn.setOnClickListener {
            navController.navigate(R.id.action_join_to_lobby)
        }

        cancelBtn.setOnClickListener {
            navController.navigate(R.id.action_join_to_start)
        }

        return rootView
    }


}