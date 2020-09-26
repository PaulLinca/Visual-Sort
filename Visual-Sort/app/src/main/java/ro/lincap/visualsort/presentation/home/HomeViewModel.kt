package ro.lincap.visualsort.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ro.lincap.visualsort.data.model.ISortingAlgorithm
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel()
{
    private val _entries = MutableLiveData<List<BarEntry>>()
    val entries: LiveData<List<BarEntry>>
        get() = _entries

    lateinit var sortingAlgorithm: ISortingAlgorithm

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
        _entries.value = entries
        var s = BarEntry(1f,2f)
    }

    fun shuffleChartData()
    {
        val shuffledEntries = _entries.value!!.shuffled()
        var counter = 0f
        for(entry in shuffledEntries)
        {
            entry.x = counter
            counter++
        }
        _entries.value = shuffledEntries
    }

    fun applySorting()
    {
        GlobalScope.launch {
            _entries.value?.let { sort(it) }
        }
    }

    fun sort(listToSort: List<BarEntry>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        for(currentPass in 0 until (listToSort.size - 1))
        {
            for(currentPosition in 0 until (listToSort.size - currentPass - 1))
            {
                if (listToSort[currentPosition].y > listToSort[currentPosition + 1].y)
                {
                    val temp = listToSort[currentPosition].y
                    listToSort[currentPosition].y = listToSort[currentPosition + 1].y
                    listToSort[currentPosition + 1].y = temp
                }
                _entries.postValue(listToSort)
                Thread.sleep(10)
            }
        }
    }
}