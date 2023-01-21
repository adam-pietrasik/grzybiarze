package pl.grzybiarze.gatherer.helper_classes

import android.net.Uri

data class Post(
    var numberOfLikes: Int = 0,
    var numberOfComments: Int = 0,
    var date: String = "",
    var localization: String = "",
    var postOwner: String = "",
    var postOwnerPhoto: String = "",
    var mushroomType: String = "",
    var postText: String = "",
    var postPictureUri: String? = null
)