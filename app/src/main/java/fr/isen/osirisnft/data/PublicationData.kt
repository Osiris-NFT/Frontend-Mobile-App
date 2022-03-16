package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.StringBuilder

class PublicationData(
    @SerializedName("_id") val id: String,
    @SerializedName("publication_date") val date: String,
    @SerializedName("publication_name") val title: String,
    @SerializedName("user_name") val user: String,
    @SerializedName("content-type") val contentType: String,
    @SerializedName("media_url") val image: String,
    @SerializedName("category") val category: String,
    @SerializedName("description") val description: String,
    @SerializedName("hashtags") val hashtags: ArrayList<String>,
    @SerializedName("likes_count") val likes: Int,
    @SerializedName("comments") val comments: ArrayList<CommentData>
    ): Serializable {}