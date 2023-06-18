package com.turanapps.englishwordsapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.turanapps.englishwordsapp.Model.Word
import com.turanapps.englishwordsapp.R
import com.turanapps.englishwordsapp.databinding.ListWordsRecyclerRowBinding

class RecyclerAdapterListWords(val wordsList: List<Word>): RecyclerView.Adapter<RecyclerAdapterListWords.ListWordsViewHolder>() {

    private val colorList = listOf(
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7,
        R.color.color8,
        R.color.color7,
        R.color.color6,
        R.color.color5,
        R.color.color4,
        R.color.color3,
        R.color.color2
    )

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class ListWordsViewHolder(val binding: ListWordsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListWordsViewHolder {
        val binding = ListWordsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context.applicationContext), parent, false)
        return ListWordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListWordsViewHolder, position: Int) {

        val colorIndex = position % colorList.size
        val color = ContextCompat.getColor(holder.itemView.context, colorList[colorIndex])
        holder.itemView.setBackgroundColor(color)

        holder.binding.englishWordTextViewRecyclerRow.text = wordsList[position].english



        if (position == selectedPosition) {
            holder.binding.turkishWordTextViewRecyclerRow.visibility = View.VISIBLE
            holder.binding.turkishWordTextViewRecyclerRow.text = wordsList[position].turkish
        }
        else {
            holder.binding.turkishWordTextViewRecyclerRow.visibility = View.GONE
        }

        holder.binding.englishWordTextViewRecyclerRow.setOnClickListener {
            if(holder.binding.turkishWordTextViewRecyclerRow.visibility == View.VISIBLE){
                selectedPosition = RecyclerView.NO_POSITION
            }
            else{
                selectedPosition = holder.adapterPosition
            }
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return wordsList.size
    }

}
