package com.example.theyumexplorer.extension

import com.example.theyumexplorer.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser?.toTheYumUser(): User = User(
    this?.uid,
    this?.displayName,
    this?.email,
    this?.phoneNumber,
    this?.photoUrl?.toString()
)