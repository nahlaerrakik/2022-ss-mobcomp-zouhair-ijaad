package de.hsfl.team46.campusflag.repository

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import de.hsfl.team46.campusflag.model.Position


class LocationRepository : LocationListener {

    companion object {
        private const val TAG = "LocationListener"
    }

    private val MIN_TIME_BW_UPDATES = 1000
    private val MIN_DISTANCE_CHANGE_FOR_UPDATES = 0

    private val TOP_LEFT_COORDS = Position(54.778514, 9.442749)
    private val BOTTOM_RIGHT_COORDS = Position(54.769009, 9.464722)

    private var campusMapWidth: Double = 0.0
    private var campusMapHeight: Double = 0.0

    private var currentPosition = MutableLiveData<Position>()
    private var currentLocation = MutableLiveData<Position>()

    var displayMetrics: DisplayMetrics = DisplayMetrics()

    fun getCurrentLocation(): MutableLiveData<Position> = currentLocation
    fun getCurrentPosition(): MutableLiveData<Position> = currentPosition

    fun requestCurrentLocation(mContext: Context): Location? {
        var loc: Location? = null

        val locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager


        // getting GPS status
        val checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // getting network status
        val checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!checkGPS && !checkNetwork) {
            Toast.makeText(mContext, "No Service Provider Available", Toast.LENGTH_SHORT).show()
        }
        else {
            if (checkNetwork) {
                Toast.makeText(mContext, "Network", Toast.LENGTH_SHORT).show()

                try {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES.toLong(),
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
//                    Log.d("Network", "Network")

                    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (loc != null) {
//                        Log.d(TAG,"getCurrentLocation: " + loc.latitude.toString() + ", " + loc.longitude)
                        return loc
                    }
                }
                catch (e: SecurityException) {
                    Log.e(TAG, e.toString())
                }
            }
        }

        // if GPS Enabled get lat/long using GPS Services
        if (checkGPS) {
            Toast.makeText(mContext, "GPS", Toast.LENGTH_SHORT).show()

            if (loc == null) {
                try {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES.toLong(),
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
//                    Log.d("GPS Enabled", "GPS Enabled")

                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                } catch (e: SecurityException) {
                }
            }
        }

        if (loc != null) {
            currentLocation.value = Position(loc.latitude, loc.longitude)
            currentPosition.value = Position(
                campusMapHeight * getLatScaling(currentLocation.value!!.lat),
                campusMapWidth * getLongScaling(currentLocation.value!!.long)
            )
        }

        return loc
    }

    fun moveLocationMarker(x: Double, y: Double, marker: ImageView) {
        marker.translationX =
            (x - (10 * displayMetrics.density + 0.5f)).toFloat()
        marker.translationY =
            (y - (10 * displayMetrics.density + 0.5f)).toFloat()
    }

    override fun onLocationChanged(location: Location) {
        Log.d(TAG, "Location: ${location.latitude}, ${location.longitude}")
        currentLocation.value = Position(location.latitude, location.longitude)
        currentPosition.value = Position(
            campusMapHeight * getLatScaling(currentLocation.value!!.lat),
            campusMapWidth * getLongScaling(currentLocation.value!!.long)
        )
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onProviderDisabled(provider: String) {
    }

    private fun getLatScaling(lat:Double): Double {
        return (lat - TOP_LEFT_COORDS.lat) / (BOTTOM_RIGHT_COORDS.lat - TOP_LEFT_COORDS.lat)
    }

    private fun getLongScaling(long:Double): Double {
        return (long - TOP_LEFT_COORDS.long) / (BOTTOM_RIGHT_COORDS.long - TOP_LEFT_COORDS.long)
    }
}