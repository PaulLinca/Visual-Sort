package ro.lincap.visualsort.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ro.lincap.visualsort.data.model.BubbleSort
import ro.lincap.visualsort.data.model.ISortingAlgorithm

class HomeViewModel : ViewModel()
{
    var sortingAlgorithm: ISortingAlgorithm = BubbleSort(MutableLiveData(arrayListOf()))
    val entries: LiveData<List<BarEntry>>
        get() = sortingAlgorithm.list

    init
    {
        initChartData()
    }

    private fun initChartData()
    {
        val entries = ArrayList<BarEntry>()
        for(chartValue in 0..99)
        {
            entries.add(BarEntry(chartValue.toFloat(),chartValue.toFloat()+ 1))
        }
        sortingAlgorithm.list.value = entries
    }

    fun shuffleChartData()
    {
        val shuffledEntries = sortingAlgorithm.list.value!!.shuffled()
        var counter = 0f
        for(entry in shuffledEntries)
        {
            entry.x = counter
            counter++
        }
        sortingAlgorithm.list.value = shuffledEntries
    }

    fun applySorting()
    {
        GlobalScope.launch {
            sortingAlgorithm.list.value?.let { sortingAlgorithm.sort() }
        }
    }
}