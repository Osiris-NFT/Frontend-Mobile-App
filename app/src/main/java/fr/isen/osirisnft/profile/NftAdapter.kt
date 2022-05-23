package fr.isen.osirisnft.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.data.NFTData
import fr.isen.osirisnft.databinding.CellImageBinding
import fr.isen.osirisnft.network.Constants

class NftAdapter(private val listNft: ArrayList<NFTData>, private val nftClickListener: (NFTData) -> Unit): RecyclerView.Adapter<NftAdapter.NftViewHolder>() {
    class NftViewHolder(binding: CellImageBinding): RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.profilePubImage
        val layout: CardView = binding.profileCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftViewHolder {
        val binding = CellImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NftViewHolder, position: Int) {
        val nft = listNft[position]

        Picasso
            .get()
            .load(Constants.imageIdURL(nft._id))
            .into(holder.image)

        Log.d("testlog", Constants.imageIdURL(nft._id))

        holder.layout.setOnClickListener {
            nftClickListener.invoke(nft)
        }
    }

    override fun getItemCount(): Int {
        return listNft.count()
    }
}