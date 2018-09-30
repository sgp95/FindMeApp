package com.guillen.santiago.findmeapp.data.firebase.Services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.guillen.santiago.findmeapp.data.firebase.ReferencesManager;
import com.guillen.santiago.findmeapp.data.model.User;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class UserService {
    ReferencesManager manager;

    public UserService() {
        this.manager = new ReferencesManager();
    }

    public Maybe<Task<AuthResult>> loginUser(final String email, final String password){
        return Maybe.create(new MaybeOnSubscribe<Task<AuthResult>>() {
            @Override
            public void subscribe(final MaybeEmitter<Task<AuthResult>> emitter) throws Exception {
                manager.getFirebaseAuth()
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            emitter.onSuccess(task);
                            emitter.onComplete();
                        }else {
                            if (!emitter.isDisposed()) {
                                Exception e = new Exception("user not found");
                                emitter.onError(e);
                            }else{
                                Log.e("FindMeApp","loginWithUserAndPassword Observer disposed");
                            }
                        }
                    }
                });
            }
        });
    }

//    public Completable RegisterUser(final String email, final String password){
//        return Completable.create(new CompletableOnSubscribe() {
//            @Override
//            public void subscribe(final CompletableEmitter emitter) throws Exception {
//                manager.getFirebaseAuth()
//                        .createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            emitter.onComplete();
//                        }else {
//                            emitter.onError(task.getException());
//                        }
//                    }
//                });
//            }
//        });
//    }

    public Single<Task<AuthResult>> RegisterUser(final String email, final String password){
        return Single.create(new SingleOnSubscribe<Task<AuthResult>>() {
            @Override
            public void subscribe(final SingleEmitter<Task<AuthResult>> emitter) throws Exception {
                manager.getFirebaseAuth()
                        .createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            emitter.onSuccess(task);
                        }else {
                            emitter.onError(task.getException());
                        }
                    }
                });
            }
        });
    }

    public Single<String> saveUserData(final User user, final String userId){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                manager.getUsersCollection()
                        .document(userId)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emitter.onSuccess(userId);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        });
            }
        });
    }


    public Maybe<User> getUser(final  String userId){
        return Maybe.create(new MaybeOnSubscribe<User>() {
            @Override
            public void subscribe(final MaybeEmitter<User> emitter) throws Exception {
                manager.getUsersCollection()
                        .document(userId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        emitter.onSuccess(documentSnapshot.toObject(User.class));
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                });
            }
        });
    }
}
