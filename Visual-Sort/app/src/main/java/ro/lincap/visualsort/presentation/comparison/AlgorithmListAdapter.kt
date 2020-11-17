package ro.lincap.visualsort.presentation.comparison

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.algorithm_card.view.*
import ro.lincap.visualsort.R
import ro.lincap.visualsort.data.model.ISortingAlgorithm
import ro.lincap.visualsort.databinding.AlgorithmCardBinding
import ro.lincap.visualsort.util.configureChartAppearance
import java.lang.IndexOutOfBoundsException
import kotlin.random.Random

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

        val chart = holder.itemView.card_barChart
        chart.configureChartAppearance()
        initChart(chart)
    }

    private fun initChart(barChart: BarChart)
    {
        val entries = ArrayList<BarEntry>()
        for(chartPosition in 0..51)
        {
            val randomValue = Random.nextInt(1, 51).toFloat()
            entries.add(BarEntry(chartPosition.toFloat(), randomValue))
        }

        // Create the data set
        val dataSet = BarDataSet(entries, "Values to be ordered")

        // Disable the bar value labels
        dataSet.setDrawValues(false)

        // Set the new chart data
        barChart.data = BarData(dataSet)

        // Redraw the chart
        barChart.notifyDataSetChanged()
        barChart.invalidate()
    }
}