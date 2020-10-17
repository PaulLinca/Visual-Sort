package ro.lincap.visualsort.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.LabelFormatter
import kotlinx.android.synthetic.main.fragment_home.*
import ro.lincap.visualsort.R
import ro.lincap.visualsort.data.model.BubbleSort
import ro.lincap.visualsort.data.model.SelectionSort
import ro.lincap.visualsort.databinding.FragmentHomeBinding
import ro.lincap.visualsort.util.Constants

class HomeFragment : Fragment()
{
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val barChartColors: IntArray = IntArray(100) { Constants.BLUE}

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

        setupUI()
    }

    private fun setupUI()
    {
        configureChartAppearance()
        configureObservers()

        changeAlgorithmButton.setOnClickListener {
            handleAlgorithmSwitch()
        }

        speedSlider.setLabelFormatter {
            "Speed: $it"
        }

        sizeSlider.setLabelFormatter {
            "Size: $it"
        }
    }

    /**
     * Defines how the chart should look and behave
     */
    private fun configureChartAppearance()
    {
        // disable zoom
        chart.setScaleEnabled(false)

        // Disable touch
        chart.setTouchEnabled(false)

        // disable labels
        chart.axisLeft.setDrawLabels(false)
        chart.axisRight.setDrawLabels(false)
        chart.legend.isEnabled = false
        chart.description.isEnabled = false

        // disable grid lines
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.xAxis.isEnabled = false

        // remove side padding
        chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        // remove bottom padding
        chart.axisLeft.axisMinimum = 0f
    }

    /**
     * Configures the viewModel values that should be observed and sets what should happen when notified
     */
    private fun configureObservers()
    {
        viewModel.entries.observe(viewLifecycleOwner, Observer { entries ->
            refreshChart(entries)
        })

        viewModel.entriesToHighlight.observe(viewLifecycleOwner, Observer {
            refreshChart(viewModel.entries.value)
        })

        viewModel.size.observe(viewLifecycleOwner, Observer {
            viewModel.populateChartData()
        })
    }

    /**
     * Configures the adapter for the algorithm spinner
     * Defines the user interaction
     */
//    private fun initAlgorithmSpinner()
//    {
//        ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.sorting_algorithms,
//            android.R.layout.simple_spinner_item)
//            .also { arrayAdapter ->
//                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                algorithmSpinner.adapter = arrayAdapter
//            }
//
//        algorithmSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?)
//            {
//                viewModel.sortingAlgorithm = BubbleSort()
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
//            {
//                val algorithm = parent?.getItemAtPosition(position)
//                when(algorithm)
//                {
//                    getString(R.string.bubble_sort) -> viewModel.sortingAlgorithm = BubbleSort()
//                    getString(R.string.selection_sort) -> viewModel.sortingAlgorithm = SelectionSort()
//                }
//
//                (activity as AppCompatActivity).supportActionBar?.title = algorithm as String
//            }
//        }
//    }

    private fun handleAlgorithmSwitch()
    {
        val items = arrayOf("Bubble Sort", "Selection Sort")
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Choose an algorithm")
                .setItems(items) {dialog, which ->
                    when(which)
                    {
                        0 -> viewModel.sortingAlgorithm = BubbleSort()
                        1 -> viewModel.sortingAlgorithm = SelectionSort()
                    }
                    viewModel.currentAlgorithm.value = items[which]
                }
                .setCancelable(false)
                .show()
        }

    }

    /**
     * Updates the chart's data set and redraws it with the new values
     * @param entries bars the chart should display
     */
    private fun refreshChart(entries: List<BarEntry>?)
    {
        // Create the data set
        val dataSet = BarDataSet(entries, "Values to be ordered")

        // Set how the bars should be colored
        val colors = barChartColors.take(viewModel.size.value!!.toInt() + 1).toMutableList()
        for(index in viewModel.entriesToHighlight.value!!)
        {
            colors[index.first.toInt()] = index.second
        }
        dataSet.colors = colors

        // Disable the bar value labels
        dataSet.setDrawValues(false);

        // Set the new chart data
        chart.data = BarData(dataSet)

        // Redraw the chart
        chart.notifyDataSetChanged()
        chart.invalidate()
    }
}