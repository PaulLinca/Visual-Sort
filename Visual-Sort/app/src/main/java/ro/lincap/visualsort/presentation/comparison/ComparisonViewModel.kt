package ro.lincap.visualsort.presentation.comparison

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.lincap.visualsort.data.model.*

class ComparisonViewModel : ViewModel()
{
    val algorithms = MutableLiveData<List<ISortingAlgorithm>>()

    init
    {
        algorithms.value = arrayListOf(
            BubbleSort(),
            InsertionSort(),
            QuickSort(),
            MergeSort(),
            InsertionSort(),
            HeapSort(),
            ShellSort(),
            CombSort()
        )
    }
}