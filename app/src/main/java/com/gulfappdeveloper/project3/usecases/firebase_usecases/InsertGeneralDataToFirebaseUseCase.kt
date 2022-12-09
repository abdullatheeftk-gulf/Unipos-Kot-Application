package com.gulfappdeveloper.project3.usecases.firebase_usecases

import com.gulfappdeveloper.project3.domain.firebase.FirebaseGeneralData
import com.gulfappdeveloper.project3.repositories.FirebaseRepository

class InsertGeneralDataToFirebaseUseCase(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(collectionName: String, firebaseGeneralData: FirebaseGeneralData) {
        firebaseRepository.insertGeneralData(
            collectionName = collectionName,
            firebaseGeneralData = firebaseGeneralData
        )
    }
}