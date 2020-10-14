package ro.lincap.visualsort.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ro.lincap.visualsort.data.model.ISortingAlgorithm
import kotlin.collections.ArrayList
import kotlin.random.Random

class HomeViewModel : ViewModel()
{
    lateinit var sortingAlgorithm: ISortingAlgorithm

    private val _entries = MutableLiveData<List<BarEntry>>()
    val entries: LiveData<List<BarEntry>>
        get() = _entries
    val size = MutableLiveData(50f)
    val speed = MutableLiveData(10f)

    init
    {
        populateChartData()
    }

    fun populateChartData()
    {
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
            _entries.value?.let { sort(it) }
        }
    }

    suspend fun sort(listToSort: List<BarEntry>)
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
                delay(1000 - speed.value!!.toLong())
            }
        }
    }
}