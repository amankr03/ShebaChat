package com.example.shebachat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.runtime.currentRecomposeScope
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_RECEIVE= 1
    val ITEM_SENT=2

    //layout for viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType ==1) {
            // inflate receive
            val view:View= LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            return ReceiveViewHolder(view)
        }
        else{
            val view:View= LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    //return an integer depending upon the viewtype (sent or receive)
    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        //if the view id is with the sender id of the currentmessage then inflate
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]
        //if current view holder is send view holder
        if(holder.javaClass == SentViewHolder::class.java){
            // sent view holder


            val viewHolder = holder as SentViewHolder

            holder.sentMessage.text = currentMessage.message
        }
        else{
            //receive view holder
            val viewHolder = holder as ReceiveViewHolder

            holder.receiveMessage.text = currentMessage.message
        }
    }

    class SentViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
    }
}