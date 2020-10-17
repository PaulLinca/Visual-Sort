package ro.lincap.visualsort.presentation.home

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ro.lincap.visualsort.data.model.BubbleSort
import ro.lincap.visualsort.data.model.ISortingAlgorithm
import kotlin.random.Random

class HomeViewModel : ViewModel()
{
    var sortingAlgorithm: ISortingAlgorithm = BubbleSort()

    private val _entries = MutableLiveData<List<BarEntry>>()
    val entries: LiveData<List<BarEntry>>
        get() = _entries
    val size = MutableLiveData(50f)
    val speed = MutableLiveData(10f)
    val entriesToHighlight = MutableLiveData(listOf<Pair<Float, Int>>())
    val isBusy = MutableLiveData(false)
    val currentAlgorithm = MutableLiveData("BubbleSort")

    init
    {
        populateChartData()
    }

    fun populateChartData()
    {
        entriesToHighlight.value = listOf()

        val entries = ArrayList<BarEntry>()
        val currentSize = size.value!!.toInt()
        for(chartPosition in 0..currentSize)
        {
            val randomValue = Random.nextInt(1, currentSize).toFloat()
            entries.add(BarEntry(chartPosition.toFloat(), randomValue))
        }
        _entries.value = entries
    }

    fun applySorting()
    {
        GlobalScope.launch(Dispatchers.Main) {
            isBusy.value = true
            sortingAlgorithm.sort(_entries, speed, entriesToHighlight)
            isBusy.value = false
        }
    }
}