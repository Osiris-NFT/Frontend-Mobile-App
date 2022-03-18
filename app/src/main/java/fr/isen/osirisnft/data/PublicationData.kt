package fr.isen.osirisnft.data

import java.io.Serializable

class PublicationData(
    val _id: String,
    val publication_date: String,
    val publication_name: String,
    val user_name: String,
    val content_type: String,
    val media_url: String,
    val category: String,
    val description: String,
    val hashtags: ArrayList<String>,
    var likes_count: Int,
    val comments: ArrayList<CommentData>
    ): Serializable {}