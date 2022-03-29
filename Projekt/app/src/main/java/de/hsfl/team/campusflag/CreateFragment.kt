package de.hsfl.team.campusflag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class CreateFragment : Fragment() {

    private lateinit var rootView : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_create, container, false)

        //navigator ?
        val navController = findNavController()
        val createGameBtn : Button = rootView.findViewById(R.id.creategame_btn_create)
        val cancelBtn : Button = rootView.findViewById(R.id.cancel_btn_create)
        val setFlagAtPositionBtn : Button = rootView.findViewById(R.id.setflagposition_btn_create)

        createGameBtn.setOnClickListener {
            navController.navigate(R.id.action_create_to_lobby)
        }
        cancelBtn.setOnClickListener {
            navController.navigate(R.id.action_create_to_start)
        }
        setFlagAtPositionBtn.setOnClickListener {
            Toast.makeText(rootView.context, "Setting the Flag at position.. ", Toast.LENGTH_SHORT).show();
        }




        return rootView
    }




}