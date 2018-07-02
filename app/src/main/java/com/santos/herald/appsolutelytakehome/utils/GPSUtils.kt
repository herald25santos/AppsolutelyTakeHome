package com.santos.herald.appsolutelytakehome.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.santos.herald.appsolutelytakehome.BuildConfig
import timber.log.Timber
import java.text.DateFormat
import java.util.*


class GPSUtils(var activity: Activity) {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mSettingsClient: SettingsClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationSettingsRequest: LocationSettingsRequest
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mCurrentLocation: Location
    private lateinit var onSuccessGetGPSLocation: OnSuccessGetGPSLocation

    // location updates interval - 10sec
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10 * 1000

    // fastest updates interval - 2 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 2000

    // location last updated time
    private var mLastUpdateTime: String? = null

    private var isRequestingLocationUpdates: Boolean = false

    private val REQUEST_CHECK_SETTINGS = 100


    fun initLocationServices() {
        Timber.d("initLocationServices")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mSettingsClient = LocationServices.getSettingsClient(activity)

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                Timber.d("onLocationResult")
                mCurrentLocation = locationResult!!.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                onSuccessGetGPSLocation.onGetGPSLocation(mCurrentLocation)
            }
        }

        isRequestingLocationUpdates = false

        mLocationRequest = LocationRequest()
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
    }

    fun setOnSuccessGetGPSLocation(onSuccessGetGPSLocation: OnSuccessGetGPSLocation) {
        this.onSuccessGetGPSLocation = onSuccessGetGPSLocation
    }

    @SuppressLint("MissingPermission")
            /**
             * Starting location updates
             * Check whether location settings are satisfied and then
             * location updates will be requested
             */
    fun startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(activity, {
                    Timber.d("addOnSuccessListener")
                    Timber.i("All location settings are satisfied.")
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                })
                .addOnFailureListener(activity, { e ->
                    Timber.d("addOnFailureListener")
                    val statusCode = (e as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Timber.i("Location settings are not satisfied. Attempting to upgrade " + "location settings ")
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                            } catch (sie: IntentSender.SendIntentException) {
                                Timber.i("PendingIntent unable to execute request.")
                            }

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            Timber.e(errorMessage)

                            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                    onSuccessGetGPSLocation.onFailedGetGPSLocation(e.message)
                })
    }

    fun startLocation() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        isRequestingLocationUpdates = true
                        startLocationUpdates()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        if (response.isPermanentlyDenied) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    fun stopLocationUpdates() {
        // Removing location updates
        isRequestingLocationUpdates = false
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(activity, {
                   // Toast.makeText(activity, "Location updates stopped!", Toast.LENGTH_SHORT).show()
                })
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    fun checkPermissions(): Boolean {
        val permissionState: Int = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
        // Check for the integer request code originally supplied to startResolutionForResult().
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    Timber.d("User agreed to make required location settings changes.")
                    isRequestingLocationUpdates = true
                    if (isRequestingLocationUpdates && checkPermissions()) {
                        startLocationUpdates()
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Timber.e("User chose not to make required location settings changes.")
                    isRequestingLocationUpdates = false
                }
            }// Nothing to do. startLocationupdates() gets called in onResume again.
        }
    }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean("is_requesting_updates", isRequestingLocationUpdates)
        outState?.putParcelable("last_known_location", mCurrentLocation)
        outState?.putString("last_updated_on", mLastUpdateTime)
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        mFusedLocationClient.lastLocation
                .addOnSuccessListener({
                    if (it != null) {
                        mCurrentLocation = it
                        onSuccessGetGPSLocation.onGetGPSLocation(mCurrentLocation)
                    }
                })
                .addOnFailureListener({
                    Timber.d("Error trying to get last GPS location")
                    it.printStackTrace()
                })
    }

    fun isRequestingLocationUpdates() = isRequestingLocationUpdates

    interface OnSuccessGetGPSLocation {
        fun onGetGPSLocation(location: Location)
        fun onFailedGetGPSLocation(error: String?)
    }


}