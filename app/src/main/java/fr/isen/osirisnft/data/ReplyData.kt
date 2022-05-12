package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReplyData(
    @SerializedName("id") val _id: String,
    @SerializedName("user") val user: String,
    @SerializedName("date") val publication_date: String,
    @SerializedName("target") val target_user: String,
    @SerializedName("content") val content: String,
    @SerializedName("likes") val likes_count: Int
): Serializable {}