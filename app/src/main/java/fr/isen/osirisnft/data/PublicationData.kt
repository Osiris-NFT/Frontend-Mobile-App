package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PublicationData(
    @SerializedName("id") val _id: String,
    @SerializedName("date") val publication_date: String,
    @SerializedName("name") val publication_name: String,
    @SerializedName("author") val user_name: String,
    @SerializedName("type") val content_type: String,
    @SerializedName("url") val media_url: String,
    @SerializedName("category") val category: String,
    @SerializedName("description") val description: String,
    @SerializedName("hashtags") val hashtags: ArrayList<String>,
    @SerializedName("likes") var likes_count: Int,
    @SerializedName("comments") val comments: ArrayList<CommentData>
    ): Serializable {}