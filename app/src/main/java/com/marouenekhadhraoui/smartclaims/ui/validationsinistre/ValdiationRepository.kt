package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.newtork.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValdiationRepository @Inject constructor(var logger: Logger, private val apiHelper: ApiHelper) {


    suspend fun newDossier(token: String, type: String, lat: String, lang: String) = apiHelper.newDossier(token, type, lat, lang)


    fun uploadFiles(list1: ArrayList<Uri>, list2: ArrayList<Uri>, id: String): Boolean {
        var done: Boolean = false
        logger.log(done.toString())
        val storage = Firebase.storage
        val storageRef = storage.reference
// Create a reference to "mountains.jpg"
// Create a reference to 'images/mountains.jpg'
        val scan1ref = storageRef.child("$id/images/scan1.jpg")
        val scan2ref = storageRef.child("$id/images/scan2.jpg")
        val vid1ref = storageRef.child("$id/videos/vid1.mp4")
        val vid2ref = storageRef.child("$id/videos/vid2.mp4")
        val degat1ref = storageRef.child("$id/images/degat1.jpg")
        val degat2ref = storageRef.child("$id/images/degat2.jpg")
        val degat3ref = storageRef.child("$id/images/degat3.jpg")
        val degat4ref = storageRef.child("$id/images/degat4.jpg")


        scan1ref.putFile(list1[0])
        scan2ref.putFile(list1[1])
        vid1ref.putFile(list1[2])
        vid2ref.putFile(list1[3])
        logger.log(list2.size.toString())
        degat1ref.putFile(list2[0])
        degat2ref.putFile(list2[1])
        degat3ref.putFile(list2[2])
        degat4ref.putFile(list2[3])
        return done

    }

}