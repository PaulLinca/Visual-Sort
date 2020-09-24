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
        for(i in 1..100)
        {
            entries.add(BarEntry(i*1f,i*1f))
        }
        _entries.value = entries
    }

}