package de.hsfl.team.campusflag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        var createGameBtn : Button = rootView.findViewById(R.id.creategame_btn_create)
        var cancelBtn : Button = rootView.findViewById(R.id.cancel_btn_create)

        createGameBtn.setOnClickListener {
            navController.navigate(R.id.action_create_to_lobby)
        }
        cancelBtn.setOnClickListener {
            navController.navigate(R.id.action_create_to_start)
        }


        return rootView
    }




}