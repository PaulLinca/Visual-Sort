package ro.lincap.visualsort.util

import android.text.Html
import android.text.Spanned
import androidx.core.text.toSpanned

fun String.toComplexityFormat(): Spanned?
{
    val splitString = this.split("^")
    if(splitString.size == 1)
    {
        return "O(${splitString[0]})".toSpanned()
    }

    return Html.fromHtml("O(${splitString[0]}<sup>${splitString[1]}</sup>)")
}