package ro.lincap.visualsort.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.info_dialog.*
import ro.lincap.visualsort.R
import ro.lincap.visualsort.data.model.BubbleSort
import ro.lincap.visualsort.data.model.SelectionSort
import ro.lincap.visualsort.databinding.FragmentHomeBinding
import ro.lincap.visualsort.util.Constants

class HomeFragment : Fragment()
{
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val barChartColors: IntArray = IntArray(101) { Constants.BLUE}

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

        infoButton.setOnClickListener {
            showAlgorithmInfo()
        }

        speedSlider.setLabelFormatter {
            speedIndicatorLabel(it)
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

    /*
    Handles what happens when the "Change algorithm" button is pressed
    It shows a popup dialog with all the algorithms available for the user to choose from
     */
    private fun handleAlgorithmSwitch()
    {
        val items = arrayOf("Bubble Sort", "Selection Sort")
        context?.let {
            MaterialAlertDialogBuilder(it, R.style.MaterialAlertDialogTheme)
                .setTitle(R.string.choose_algorithm_string)
                .setItems(items) { _, which ->
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

    private fun speedIndicatorLabel(speed: Float): String
    {
        return when (speed)
        {
            in 0f..149f -> {
                "Speed: Slow"
            }
            in 150f..349f -> {
                "Speed: Normal"
            }
            else -> {
                "Speed: Fast"
            }
        }
    }

    private fun showAlgorithmInfo()
    {
        val algorithm = viewModel.sortingAlgorithm
        val algorithmInfoDialog = context?.let {
            MaterialDialog(it)
                .noAutoDismiss()
                .cornerRadius(10f)
                .customView(R.layout.info_dialog)
        }
        algorithmInfoDialog.also {
            it?.dialog_algorithmName?.text = algorithm.properName
            it?.dialog_algorithmDescription?.text = algorithm.description
            it?.dialog_algorithmComplexityAverage?.text = algorithm.complexityAverage
            it?.dialog_algorithmComplexityBest?.text = algorithm.complexityBest
            it?.dialog_algorithmComplexityWorst?.text = algorithm.complexityWorst
            it?.dialog_algorithmComplexitySpace?.text = algorithm.complexitySpace
            it?.dismissButton?.setOnClickListener {
                algorithmInfoDialog?.dismiss()
            }
            it?.show()
        }
    }
}