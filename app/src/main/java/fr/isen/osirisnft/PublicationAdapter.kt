package fr.isen.osirisnft

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.data.Constants
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.CellPublicationBinding
import org.json.JSONObject
import kotlin.coroutines.coroutineContext

class PublicationAdapter(private val listPub: ArrayList<PublicationData>, private val pubClickListener: (PublicationData) -> Unit): RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder>() {
    class PublicationViewHolder(binding: CellPublicationBinding): RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.pubTitle
        val author: TextView = binding.pubAuthor
        val description: TextView = binding.pubDescription
        val likes: TextView = binding.pubLikes
        val date: TextView = binding.pubDate
        val image: ImageView = binding.pubImage
        val likeButton: CheckBox = binding.pubLikeButton
        val layout: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val binding = CellPublicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PublicationViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val publication = listPub[position]

        holder.title.text = publication.publication_name
        holder.author.text = publication.user_name
        holder.description.text = publication.description
        holder.date.text = publication.publication_date
        holder.likes.text = publication.likes_count.toString()


        Picasso
            .get()
            .load(Constants.PublicationServiceURL + publication.media_url)
            .into(holder.image)


        val context = holder.likeButton.context
        holder.likeButton.setOnClickListener {
            if (holder.likeButton.isChecked) {
                publication.likes_count += 1
                holder.likes.text = publication.likes_count.toString()

                likesCountRequest(Constants.likeURL(publication._id), context)
            } else {
                publication.likes_count -= 1
                holder.likes.text = publication.likes_count.toString()

                likesCountRequest(Constants.unlikeURL(publication._id), context)
            }
        }

        holder.layout.setOnClickListener {
            pubClickListener.invoke(publication)
        }
    }

    override fun getItemCount(): Int {
        return listPub.count()
    }

    private fun likesCountRequest(url: String, context: Context) { // requête json
        val queue = Volley.newRequestQueue(context)
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.PATCH,
            url,
            parameters,
            {
                Log.d("testlog", it.toString(2))
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }
}