package com.gulfappdeveloper.project3.data.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.gulfappdeveloper.project3.domain.firebase.FirebaseError
import com.gulfappdeveloper.project3.domain.services.FirebaseService

private const val TAG = "FirebaseServiceImpl"
class FirebaseServiceImpl(
    private val fdb:FirebaseFirestore
):FirebaseService {
    override suspend fun insertErrorDataToFireStore(
        collectionName: String,
        documentName: String,
        error: FirebaseError
    ) {
        try {
            fdb.collection(collectionName)
                .document(documentName)
                .set(error)
                .addOnSuccessListener {
                    Log.d(TAG, "insertErrorDataToFireStore: ")
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    Log.e(TAG, "insertErrorDataToFireStore: ${it.message}",)
                }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override suspend fun insertGeneralData(collectionName: String) {

    }
}