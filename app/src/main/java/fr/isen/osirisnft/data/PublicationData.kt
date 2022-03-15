package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PublicationData(
    @SerializedName("publication_name") val title: String,
    @SerializedName("publication_date") val date: String,
    @SerializedName("user_name") val username: String,
    @SerializedName("media_url") val image: String,
    @SerializedName("description") val description: String,
    @SerializedName("hashtags") val hashtags: String,
    @SerializedName("likes_count") val likes: Int
    ): Serializable {
    fun getThumbnailURL(): String? {
        return if (image.isNotEmpty()) {
            image
        } else {
            null
        }
    }
}