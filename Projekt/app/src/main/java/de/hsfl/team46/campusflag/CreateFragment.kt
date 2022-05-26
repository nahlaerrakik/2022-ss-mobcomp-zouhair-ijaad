package de.hsfl.team46.campusflag

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import de.hsfl.team46.campusflag.databinding.FragmentCreateBinding
import de.hsfl.team46.campusflag.enums.Colors
import de.hsfl.team46.campusflag.model.Host
import de.hsfl.team46.campusflag.viewmodels.ViewModel


class CreateFragment : Fragment() {

    private val mainViewModel: ViewModel by activityViewModels()

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
                if (isGranted) {
                    Log.d("Permission", "Granted")
                } else {
                    Log.d("Permission", "Denied")
                }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel


        binding.creategameBtnCreate.setOnClickListener {
            mainViewModel.createGame {
                mainViewModel.setGameId(it)
                findNavController().navigate(R.id.action_create_lobbyy)
            }
        }

        binding.setflagpositionBtnCreate.setOnClickListener {
            mainViewModel.setCurrentHostFlagPositions(
                mainViewModel.currentLocation.value!!
            )
            Toast.makeText(binding.root.context, "Setting the Flag at position.. ", Toast.LENGTH_SHORT).show()
        }

        displayCurrentLocation()

        binding.positionMarker.setColorFilter(Colors.RED.rgb)

        return binding.root
    }

    private fun displayCurrentLocation(){
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                Log.d("Using Permission", "Granted")

                val loc: Location? = mainViewModel.getLocationRepository().requestCurrentLocation(binding.root.context)
                if (loc != null) {
                    mainViewModel.currentPosition.observe(viewLifecycleOwner) {
                        mainViewModel.getLocationRepository().moveLocationMarker(it.long, it.lat, binding.positionMarker)
                    }
                }
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }
}