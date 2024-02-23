package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Note
import com.example.myapplication.util.ImageConverter

class NoteAdapter(var notes: MutableList<Note>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
        val ivImage: ImageView = view.findViewById(R.id.itemPreviewImage)
        val cardView: CardView = view.findViewById(R.id.card_view_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.notes_card,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bitmap = notes.get(position).image?.let {
            ImageConverter.convertStringToBase64(it)
        }
        holder.tvTitle.text = notes.get(position).title
        holder.tvMessage.text = notes.get(position).message
        bitmap?.let {
            holder.ivImage.setImageBitmap(bitmap)
        } ?: holder.ivImage.setImageResource(R.drawable.gallery)
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position)
            }
        }
        if (notes.get(position).urgency) {
            holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardView.context,
                    R.color.note_b
                )
            )
        } else {
            holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardView.context,
                    R.color.note_a
                )
            )
        }
    }

    override fun getItemId(position: Int): Long {
        return notes[position].id.toLong()
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}
