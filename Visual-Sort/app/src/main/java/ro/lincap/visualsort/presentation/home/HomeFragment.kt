package ro.lincap.visualsort.presentation.home

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpanned
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.info_dialog.*
import ro.lincap.visualsort.R
import ro.lincap.visualsort.data.model.*
import ro.lincap.visualsort.databinding.FragmentHomeBinding
import ro.lincap.visualsort.util.Constants
import ro.lincap.visualsort.util.configureChartAppearance
import ro.lincap.visualsort.util.toComplexityFormat
import java.lang.IndexOutOfBoundsException

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
        chart.configureChartAppearance()
        configureObservers()

        changeAlgorithmButton.setOnClickListener {
            handleAlgorithmSwitch()
        }

        infoButton.setOnClickListener {
            showAlgorithmInfo()
        }

        compareButton.setOnClickListener {
            Navigation.findNavController(this.requireView()).navigate(R.id.action_homeFragment_to_comparisonFragment)
        }

        speedSlider.setLabelFormatter {
            speedIndicatorLabel(it)
        }

        sizeSlider.setLabelFormatter {
            "Size: $it"
        }
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
        val items = arrayOf(Constants.BUBBLESORT, Constants.SELECTIONSORT, Constants.QUICKSORT, Constants.MERGESORT, Constants.INSERTIONSORT, Constants.HEAPSORT, Constants.SHELLSORT, Constants.COMBSORT)
        context?.let {
            MaterialAlertDialogBuilder(it, R.style.MaterialAlertDialogTheme)
                .setTitle(R.string.choose_algorithm_string)
                .setItems(items) { _, which ->
                    when(which)
                    {
                        0 -> viewModel.sortingAlgorithm = BubbleSort()
                        1 -> viewModel.sortingAlgorithm = SelectionSort()
                        2 -> viewModel.sortingAlgorithm = QuickSort()
                        3 -> viewModel.sortingAlgorithm = MergeSort()
                        4 -> viewModel.sortingAlgorithm = InsertionSort()
                        5 -> viewModel.sortingAlgorithm = HeapSort()
                        6 -> viewModel.sortingAlgorithm = ShellSort()
                        7 -> viewModel.sortingAlgorithm = CombSort()
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
            try
            {
                colors[index.first.toInt()] = index.second
            }
            catch (e: IndexOutOfBoundsException)
            {
                Log.w(this::class.java.canonicalName, e.message.toString())
            }
        }
        dataSet.colors = colors

        // Disable the bar value labels
        dataSet.setDrawValues(false)

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
            it?.dialog_algorithmComplexityAverage?.text = algorithm.complexityAverage.toComplexityFormat()
            it?.dialog_algorithmComplexityBest?.text = algorithm.complexityBest.toComplexityFormat()
            it?.dialog_algorithmComplexityWorst?.text = algorithm.complexityWorst.toComplexityFormat()
            it?.dialog_algorithmComplexitySpace?.text = algorithm.complexitySpace.toComplexityFormat()
            it?.dismissButton?.setOnClickListener {
                algorithmInfoDialog?.dismiss()
            }
            it?.show()
        }
    }
}