package com.guillen.santiago.findmeapp.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class ReferencesManager {
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private CollectionReference usersCollection;
    private CollectionReference patientCollection;

    public ReferencesManager() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        usersCollection = firebaseFirestore.collection("Users");
        patientCollection = FirebaseFirestore.getInstance().collection("Patients");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public CollectionReference getUsersCollection() {
        return usersCollection;
    }

    public CollectionReference getPatientPosition(String patientId){
        return patientCollection.document(patientId).collection("Position");
    }

    public CollectionReference getPatientsCollection(String userId, boolean isCareTaker) {
        if(isCareTaker){
            return usersCollection.document(userId).collection("Patients");
        }else {
            return patientCollection;
        }
    }
}
