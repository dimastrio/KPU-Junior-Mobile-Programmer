package id.dimas.kpu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.dimas.kpu.databinding.ItemVotersBinding
import id.dimas.kpu.model.Voters

class VoterAdapter(private val onClick: (Voters) -> Unit) :
    RecyclerView.Adapter<VoterAdapter.VoterViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Voters>() {
        override fun areItemsTheSame(oldItem: Voters, newItem: Voters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Voters, newItem: Voters): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(voters: List<Voters>) = differ.submitList(voters)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoterViewHolder {
        val binding = ItemVotersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VoterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: VoterViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    inner class VoterViewHolder(private val binding: ItemVotersBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(data: Voters) {
            with(binding) {
                tvNik.text = "NIK : ${data.nik}"
                tvName.text = "Nama : ${data.nama}"
                itemView.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }
}