package pl.grzybiarze.gatherer.helper_classes

data class Post(
    val numberOfLikes: Int,
    val numberOfComments: Int,
    val date: String,
    val localization: String,
    val postOwner: String,
    val postOwnerPhoto: String,
    val mushroomType: String
    //val postPhoto: whatKindOfDatatype?
)