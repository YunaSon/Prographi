package org.prography.lemorning.src.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemCardBinding
import org.prography.lemorning.src.BindingViewHolder
import org.prography.lemorning.src.models.Card

class CardRecyclerAdapter(var cards: List<Card> = arrayListOf()): RecyclerView.Adapter<CardRecyclerAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : BindingViewHolder<ItemCardBinding>(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)

        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.binding.card = card

        if (position % 2 == 0) {
            holder.binding.layoutCardItem.setBackgroundColor(Color.parseColor("#ffffff"))
            holder.binding.tvCardItemBank.setTextColor(Color.parseColor("#FBC900"))
            holder.binding.tvCardItemNumber.setTextColor(Color.parseColor("#FBC900"))
            holder.binding.tvCardItemRemove.setTextColor(Color.parseColor("#FBC900"))
        }
    }
}