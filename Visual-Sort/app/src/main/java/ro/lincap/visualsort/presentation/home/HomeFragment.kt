package ro.lincap.visualsort.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.fragment_home.*
import ro.lincap.visualsort.databinding.FragmentHomeBinding

class HomeFragment : Fragment()
{
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            homeViewModel = viewModel
            lifecycleOwner = this@HomeFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        configureChartAppearance()

        viewModel.entries.observe(viewLifecycleOwner, Observer { entries ->
            refreshChart(entries)
        })

        refreshButton.setOnClickListener {
            viewModel.shuffleChartData()
        }
    }

    /**
     * Defines how the chart should look and behave
     */
    private fun configureChartAppearance()
    {
        // disable zoom
        chart.setScaleEnabled(false)

        // disable labels
        chart.axisLeft.setDrawLabels(false)
        chart.axisRight.setDrawLabels(false)
        chart.legend.isEnabled = false
        chart.description.isEnabled = false

        // disable grid lines
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.xAxis.isEnabled = false
    }

    /**
     * Updates the chart's data set and redraws it with the new values
     * @param entries bars the chart should display
     */
    private fun refreshChart(entries: List<BarEntry>?)
    {
        val dataSet = BarDataSet(entries, "Values to be ordered")
        val lineData = BarData(dataSet)
        chart.data = lineData
        chart.notifyDataSetChanged();
        chart.invalidate()
    }
}