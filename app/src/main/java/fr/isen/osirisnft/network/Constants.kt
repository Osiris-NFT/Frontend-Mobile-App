package fr.isen.osirisnft.network

class Constants {
    companion object {
        const val PublicationServiceURL = "http://34.117.49.96"
        const val DebugURL = "http://34.110.178.223"

        // GET
        const val NewPublications = "/api/publications/new"
        const val DateParam = "?hours_time_delta=24"

        fun isLikedURL(publication_id: String, user: String): String {
            return "$PublicationServiceURL/api/is/$publication_id/liked_by/$user"
        }

        fun allLikesURL(user: String): String {
            return "$PublicationServiceURL/api/liked_publications_of/$user"
        }

        fun publicationIdURL(publication_id: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id"
        }

        // PATCH
        fun likeURL(publication_id: String, user: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/upvote_by/$user"
        }

        fun unlikeURL(publication_id: String, user: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/downvoted_by/$user"
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
            return "$DebugURL/upload/$publication_id"
        }
    }

}