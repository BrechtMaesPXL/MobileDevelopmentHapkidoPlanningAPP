package com.example.hapkidoplanningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.User


class userRViewAdapter (
    private val users: List<User>,
    private val onItemClicked: (User) -> Unit
    ): RecyclerView.Adapter<userRViewAdapter.ViewHolderClass>() {



    inner class ViewHolderClass(itemView: View ): RecyclerView.ViewHolder(itemView) {
        val rvName: TextView = itemView.findViewById(R.id.nameField)
        val rvBelt: TextView = itemView.findViewById(R.id.beltField)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(users[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_r_view, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return  users.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = users[position]
        holder.rvBelt.text = currentItem.belt.toString()
        holder.rvName.text = currentItem.name
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameField)

        fun bind(user: User) {
            nameTextView.text = user.name
        }
    }
}
