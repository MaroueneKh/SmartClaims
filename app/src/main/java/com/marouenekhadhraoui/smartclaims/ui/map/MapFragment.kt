package com.marouenekhadhraoui.smartclaims.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentMapBinding
import com.marouenekhadhraoui.smartclaims.ui.declaration.DeclarationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var binding: FragmentMapBinding
    private lateinit var navDirections: NavDirections
    private lateinit var location: Location

    private val viewModel: MapViewModel by viewModels()
    private val viewModelDeclaration: DeclarationViewModel by activityViewModels()

    @Inject
    lateinit var logger: Logger
    private val LOCATION_PERMISSION_REQUEST = 101


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //instasiate getfused location with context
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.lifecycleOwner = this

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
        setupNavigation()


        viewModelDeclaration.stateButton1.postValue("checked")

    }

    fun bindViewModel() {
        binding.viewModel = viewModel
    }

    fun setNavDirections() {

        val bundle = bundleOf("lat" to location.latitude, "long" to location.longitude)

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_mapFragment_to_scanConstatFragment
            }
        }
    }

    fun setupNavigation() {

        viewModel.pressBtnSuivantEvent.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let {
                        viewModel.locationDone.observe(
                                viewLifecycleOwner,
                                androidx.lifecycle.Observer {
                                    it?.let {
                                        if (it) {
                                            setNavDirections()
                                            Navigation.findNavController(requireView()).navigate(navDirections)


                                        }
                                    }

                                })
                    }
                })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onMapReady(googleMap: GoogleMap) {
        logger.log("hellooooo")
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            //requests are not permitted
            requestPermission()
        } else {
            if (checkGps()) {
                //gps is enabled in device
                startLocationUpdates()
            } else {
                //gps is not enabled
                showAlertLocation()
            }

        }

    }

    // Start location updates
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f //170 m = 0.1 mile
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //according to your app
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                if (locationResult.locations.isNotEmpty()) {
                    viewModel.locationDone.postValue(true)

                    location = locationResult.lastLocation
                    setMarker(locationResult.lastLocation)
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null /* Looper */
        )
    }

    // Stop location updates


    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)

    }


    fun setMarker(location: Location) {
        mMap.addMarker(
                MarkerOptions()
                        .position(LatLng(location.latitude, location.longitude))
                        .title("Votre emplacement").draggable(true)
        )
        val latlng = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18.0F))


    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun checkGps(): Boolean {
        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isLocationEnabled

    }

    private fun showAlertLocation() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Veuillez activer la localisation")
        dialog.setPositiveButton("Settings") { _, _ ->
            val myIntent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(myIntent)
        }
        dialog.setNegativeButton("Cancel") { _, _ ->

        }
        dialog.setCancelable(false)
        dialog.show()
    }


}