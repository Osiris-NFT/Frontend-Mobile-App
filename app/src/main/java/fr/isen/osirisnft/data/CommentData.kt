package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CommentData(
    val _id: String,
    val user: String,
    val publication_date: String,
    val content: String,
    val likes_count: Int,
    val replies: ArrayList<ReplyData>
): Serializable {}