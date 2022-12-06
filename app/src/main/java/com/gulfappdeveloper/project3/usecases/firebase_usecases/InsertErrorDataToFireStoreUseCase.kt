package com.gulfappdeveloper.project3.usecases.firebase_usecases

import com.gulfappdeveloper.project3.domain.firebase.FirebaseError
import com.gulfappdeveloper.project3.repositories.FirebaseRepository

class InsertErrorDataToFireStoreUseCase(
    private val firebaseRepository: FirebaseRepository
) {

   suspend operator fun invoke(
       collectionName:String,
       documentName:String,
       errorData:FirebaseError
   ){
       firebaseRepository.insertErrorDataToFireStore(
           collectionName = collectionName,
           documentName = documentName,
           error = errorData
       )
   }
}