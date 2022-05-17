package fr.isen.osirisnft.data

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class ParseData {
    fun parsePublications(json: JSONObject, objectName: String): ArrayList<PublicationData> { // parse des données en une liste de publications
        val publications: JSONArray = json[objectName] as JSONArray
        val arraylistofpub = ArrayList<PublicationData>()

        for (i in 0 until publications.length()) {
            Log.d("PARSE PUBLICATION: PUBLICATION", publications.get(i).toString())
            val arraylistofcom = ArrayList<CommentData>()
            val publication = publications.get(i) as JSONObject
            val comments = publication["comments"] as JSONArray

            for (y in 0 until comments.length()){
                Log.d("PARSE PUBLICATION: COMMENT", comments.get(y).toString())
                val arraylistofrep = ArrayList<ReplyData>()
                val comment = comments.get(y) as JSONObject
                val replies = comment["replies"] as JSONArray

                for (z in 0 until replies.length()){
                    val reply = replies.get(z) as JSONObject
                    //Log.d("REPLY", reply.toString())
                    val replydata =  ReplyData(
                        reply["_id"] as String,
                        reply["user"] as String,
                        reply["publication_date"] as String,
                        reply["target_user"] as String,
                        reply["content"] as String,
                        reply["likes_count"] as Int)
                    Log.d("PARSE PUBLICATION: REPLY AS REPLYDATA", replydata.toString())
                    arraylistofrep.add(replydata)
                }
                val commentdata = CommentData(
                    comment["_id"] as String,
                    comment["user"] as String,
                    comment["publication_date"] as String,
                    comment["content"] as String,
                    comment["likes_count"] as Int,
                    arraylistofrep,
                )
                arraylistofcom.add(commentdata)
            }
            val arraylistofhash = ArrayList<String>()
            val hashtags = publication["hashtags"] as JSONArray

            for (w in 0 until hashtags.length()){
                val hashtag = hashtags.get(w) as String
                arraylistofhash.add(hashtag)
            }
            val publicationdata = PublicationData(
                publication["_id"] as String,
                publication["publication_date"] as String,
                publication["publication_name"] as String,
                publication["user_name"] as String,
                publication["content_type"] as String,
                publication["media_url"] as String,
                publication["category"] as String,
                publication["description"] as String,
                arraylistofhash,
                publication["likes_count"] as Int,
                arraylistofcom
            )
            arraylistofpub.add(publicationdata)
        }
        Log.d("PARSE PUBLICATION: PUBLICATIONS AS ARRAYLIST", arraylistofpub.toString())

        return arraylistofpub
    }
}