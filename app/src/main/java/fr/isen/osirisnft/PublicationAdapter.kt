package fr.isen.osirisnft

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.CellPublicationBinding

class PublicationAdapter(private val listPub: List<PublicationData>, private val pubClickListener: (PublicationData) -> Unit): RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder>() {
    class PublicationViewHolder(binding: CellPublicationBinding): RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.pubTitle
        val author: TextView = binding.pubAuthor
        val description: TextView = binding.pubDescription
        val likes: TextView = binding.pubLikes
        val date: TextView = binding.pubDate
        val image: ImageView = binding.pubImage
        val layout: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val binding = CellPublicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PublicationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val publication = listPub[position]

        holder.title.text = publication.title
        holder.author.text = publication.user
        holder.description.text = publication.description
        holder.date.text = publication.date
        holder.likes.text = publication.likes.toString()

        Picasso
            .get()
            .load(publication.image)
            .into(holder.image)

        holder.layout.setOnClickListener {
            pubClickListener.invoke(publication)
        }
    }

    override fun getItemCount(): Int {
        return listPub.count()
    }
}