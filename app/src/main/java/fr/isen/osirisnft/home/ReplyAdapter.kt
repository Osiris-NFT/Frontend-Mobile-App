package fr.isen.osirisnft.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.osirisnft.data.ReplyData
import fr.isen.osirisnft.databinding.CellReplyBinding

class ReplyAdapter(private val listReply: ArrayList<ReplyData>): RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder>() {
    class ReplyViewHolder(binding: CellReplyBinding): RecyclerView.ViewHolder(binding.root) {
        val user: TextView = binding.replyUser
        val target: TextView = binding.replyTarget
        val content: TextView = binding.replyContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val binding = CellReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        val reply = listReply[position]

        holder.user.text = reply.user
        holder.target.text = reply.target_user
        holder.content.text = reply.content
    }

    override fun getItemCount(): Int {
        return listReply.count()
    }
}