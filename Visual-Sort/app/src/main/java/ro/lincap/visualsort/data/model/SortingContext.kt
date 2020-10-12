package ro.lincap.visualsort.data.model

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry

data class SortingContext(
    var list: MutableLiveData<List<BarEntry>>
)