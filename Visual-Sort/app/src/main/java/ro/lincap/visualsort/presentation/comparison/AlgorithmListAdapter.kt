package ro.lincap.visualsort.presentation.comparison

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ro.lincap.visualsort.R
import ro.lincap.visualsort.data.model.ISortingAlgorithm
import ro.lincap.visualsort.databinding.AlgorithmCardBinding

class AlgorithmListAdapter(private val sortingAlgorithms: List<ISortingAlgorithm>): RecyclerView.Adapter<AlgorithmListAdapter.AlgorithmViewHolder>()
{
    class AlgorithmViewHolder(var binding: AlgorithmCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlgorithmViewHolder =
        AlgorithmViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.algorithm_card,
                parent,
                false
            )
        )

    override fun getItemCount() = sortingAlgorithms.count()

    override fun onBindViewHolder(holder: AlgorithmViewHolder, position: Int)
    {
        val currentAlgorithm = sortingAlgorithms[position]
        holder.binding.algorithm = currentAlgorithm
    }
}