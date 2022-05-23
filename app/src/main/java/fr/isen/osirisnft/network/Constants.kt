package fr.isen.osirisnft.network

class Constants {
    companion object {
        const val PublicationServiceURL = "http://34.117.49.96"
        const val DebugURL = "http://34.110.178.223"

        /********** GET **********/
        const val NewPublications = "/api/publications/new"
        const val DateParam = "?hours_time_delta=24"
        const val TokenByNft = "/api/nfts/token"

        fun isLikedURL(publication_id: String, user: String): String {
            return "$PublicationServiceURL/api/is/$publication_id/liked_by/$user"
        }

        fun allLikesURL(user: String): String {
            return "$PublicationServiceURL/api/liked_publications_of/$user"
        }

        fun pubByUserURL(user: String): String {
            return "$PublicationServiceURL/api/$user/publications"
        }

        fun imageIdURL(id: String): String {
            return "$PublicationServiceURL/api/images/$id"
        }

        fun nftByWalletURL(wallet: String): String {
            return "$PublicationServiceURL/api/nfts/$wallet"
        }

        fun nftByIdURL(id: String): String {
            return "$PublicationServiceURL/api/nfts/metadata_of/$id"
        }

        /********** PATCH **********/
        fun likeURL(publication_id: String, user: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/upvote_by/$user"
        }

        fun unlikeURL(publication_id: String, user: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/downvoted_by/$user"
        }

        fun updateNftURL(id: String, wallet: String): String {
            return "$PublicationServiceURL/api/nfts/$id/update_wallet/$wallet"
        }

        /********** POST **********/
        const val PostPublication = "/api/publications/post"
        const val TransferNft = "/api/nfts/transfer"

        fun addCommentURL(publication_id: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/comments/post"
        }

        fun postImageNFTURL(wallet: String): String {
            return "$DebugURL/upload-nft/$wallet"
        }

        /********** DELETE **********/
        fun deletePubByIdURL(publication_id: String): String {
            return "$PublicationServiceURL/api/publications/$publication_id/delete"
        }
    }
}