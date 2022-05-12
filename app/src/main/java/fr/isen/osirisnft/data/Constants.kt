package fr.isen.osirisnft.data

class Constants {
    companion object {
        const val PublicationServiceURL = "http://34.117.49.96"
        const val DebugURL = "http://34.110.178.223"

        // GET
        const val NewPublications = "/api/publications/new"
        const val DateParam = "?hours_time_delta=24"

        // PATCH
        fun likeURL(publication_id: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/upvote"
        }

        fun unlikeURL(publication_id: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/downvote"
        }

        // POST
        fun addCommentURL(publication_id: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/comments/post"
        }

        const val PostPublication = "/api/publications/post"

        fun postImageURL(publication_id: String):String {
            return "$PublicationServiceURL/api/publications/$publication_id/upload"
        }

        fun debugImageURL(publication_id: String): String {
            return "/upload/$publication_id"
        }
    }

}