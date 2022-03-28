package de.hsfl.team.campusflag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class StartFragment : Fragment() {

    //globale initialisierung
    private lateinit var rootView:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_start, container, false)

        // Navigation zu gewünschten Ziel bzw. Ansichten
        val navController = findNavController()

        // Host Button initialisieren
        var btnHostGame: Button = rootView.findViewById(R.id.host_btn_start)
        btnHostGame.setOnClickListener {
            navController.navigate(R.id.action_start_to_create)
        }
        // Join Button initialisieren
        var btnJoinGame: Button = rootView.findViewById(R.id.join_btn_start)
        btnJoinGame.setOnClickListener {
            navController.navigate(R.id.action_start_to_join)
        }
        return rootView
    }


}