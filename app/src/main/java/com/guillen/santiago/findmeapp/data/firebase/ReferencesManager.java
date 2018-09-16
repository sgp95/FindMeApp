package com.guillen.santiago.findmeapp.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class ReferencesManager {
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private CollectionReference usersCollection;

    public ReferencesManager() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        usersCollection = firebaseFirestore.collection("Users");
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

    public void setUsersCollection(CollectionReference usersCollection) {
        this.usersCollection = usersCollection;
    }

    public CollectionReference getFamilyCollection(String userId) {
        return usersCollection.document(userId).collection("Family");
    }
}
