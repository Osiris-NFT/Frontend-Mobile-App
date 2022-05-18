package fr.isen.osirisnft.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.osirisnft.data.CommentData
import fr.isen.osirisnft.databinding.CellCommentBinding
import fr.isen.osirisnft.home.DetailsActivity.Companion.context as DetailsActivity

class CommentAdapter(private val listComment: ArrayList<CommentData>): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    class CommentViewHolder(binding: CellCommentBinding): RecyclerView.ViewHolder(binding.root) {
        val user: TextView = binding.commentUser
        val content: TextView = binding.commentContent
        val replies: RecyclerView = binding.listOfReply
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CellCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = listComment[position]

        holder.user.text = comment.user
        holder.content.text = comment.content

        holder.replies.layoutManager = LinearLayoutManager(DetailsActivity)
        holder.replies.adapter = ReplyAdapter(comment.replies)
    }

    override fun getItemCount(): Int {
        return listComment.count()
    }

    fun addComment(newComment: CommentData) {
        listComment.add(newComment)
    }
}