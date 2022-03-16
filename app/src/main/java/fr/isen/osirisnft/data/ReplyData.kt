package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReplyData(
    @SerializedName("_id") val id: String,
    @SerializedName("user") val user: String,
    @SerializedName("publication_date") val date: String,
    @SerializedName("content") val content: String,
    @SerializedName("likes_count") val likes: Int
): Serializable {}