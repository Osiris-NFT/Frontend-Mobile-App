package fr.isen.osirisnft

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.data.Constants
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.CellImageBinding

class ImageAdapter(private val listImage: ArrayList<PublicationData>, private val imageClickListener: (PublicationData) -> Unit): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(binding: CellImageBinding): RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.profilePubImage
        val layout: CardView = binding.profileCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ImageViewHolder {
        val binding = CellImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ImageViewHolder, position: Int) {
        val image = listImage[position]

        Picasso
            .get()
            .load(Constants.PublicationServiceURL + image.media_url)
            .into(holder.image)

        holder.layout.setOnClickListener {
            imageClickListener.invoke(image)
        }
    }

    override fun getItemCount(): Int {
        return listImage.count()
    }
}