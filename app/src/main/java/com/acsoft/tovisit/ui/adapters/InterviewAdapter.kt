package com.acsoft.tovisit.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acsoft.tovisit.R
import com.acsoft.tovisit.core.BaseViewHolder
import com.acsoft.tovisit.data.model.InterviewItemEntity
import com.acsoft.tovisit.databinding.InterviewItemBinding

class InterviewAdapter(private val itemClickListener: OnInterviewClickListener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var interviewList = listOf<InterviewItemEntity>()

    fun setInterviewList(interviewList: List<InterviewItemEntity>) {
        this.interviewList = interviewList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding =
                InterviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = InterviewViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onInterviewClick(interviewList[position])
        }

        return holder

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is InterviewViewHolder -> {
                holder.bind(interviewList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return interviewList.size
    }

    private inner class InterviewViewHolder(val binding : InterviewItemBinding, val context: Context) :
            BaseViewHolder<InterviewItemEntity>(binding.root) {
        override fun bind(item: InterviewItemEntity) {

            binding.tvVisited.text = if (item.visited) context.getString(R.string.visited)
            else context.getString(R.string.pending)
            binding.tvStreetName.text = item.streetName
            binding.tvSuburb.text = item.suburb
        }
    }

    interface OnInterviewClickListener {
        fun onInterviewClick(interview: InterviewItemEntity)
    }

}
