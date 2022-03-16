package fr.isen.osirisnft.data

class PublicationClass(
    val id: String,
    val date: String,
    val title: String,
    val user: String,
    val contentType: String,
    val image: String,
    val category: String,
    val description: String,
    val hashtags: ArrayList<String>,
    val likes: Int,
    val comments: ArrayList<CommentData>
) {

    fun PublicationClass(){

    }
}