package org.techtown.movie

import com.google.firebase.database.Exclude

data class MovieComment(
    var objectId: String,
    var author: String,
    var date: String,
    var rating: Long,
    var contents: String,
    var recommendCount: Long,
    var timestamp: Long = 0
) {

    @Exclude
    fun toMap(): HashMap<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["objectId"] = objectId
        result["author"] = author
        result["date"] = date
        result["rating"] = rating
        result["contents"] = contents
        result["recommendCount"] = recommendCount
        result["timestamp"] = timestamp

        return result
    }

}