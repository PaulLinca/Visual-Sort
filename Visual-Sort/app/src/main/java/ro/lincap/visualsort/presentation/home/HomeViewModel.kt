package ro.lincap.visualsort.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry

class HomeViewModel : ViewModel()
{
    private val _entries = MutableLiveData<List<BarEntry>>()
    val entries: LiveData<List<BarEntry>>
        get() = _entries

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
}