package pl.grzybiarze.gatherer.model

import com.google.firebase.firestore.DocumentReference

data class User(
    var firstName: String? = null,
    val lastName: String? = null,
    val posts: List<DocumentReference>? = null,
    val profilePicture: DocumentReference? = null,
    val username: String? = null,
    var email: String? = null
)
