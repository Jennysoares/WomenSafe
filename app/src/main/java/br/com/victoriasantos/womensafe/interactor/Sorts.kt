package br.com.victoriasantos.womensafe.interactor

import android.content.Context
import br.com.victoriasantos.womensafe.domain.Plate

class QuickSort(context: Context){
    fun quicksort(items:List<Plate>):List<Plate>{
        if (items.count() < 2){
            return items
        }
        val pivot = items[items.count()/2].placa

        val equal = items.filter { it.placa!!.compareTo(pivot!!)  == 0 }
//    println("pivot value is : "+equal)

        val less = items.filter { it.placa!!.compareTo(pivot!!) < 0 }
//    println("Lesser values than pivot : "+less)

        val greater = items.filter { it.placa!!.compareTo(pivot!!) > 0 }
//    println("Greater values than pivot : "+greater)

        return quicksort(less) + equal + quicksort(greater)
    }


}

class MergeSort(context: Context){

    fun mergeSort(list: List<Plate>): List<Plate> {
        if (list.size <= 1) {
            return list
        }

        val middle = list.size / 2
        var left = list.subList(0,middle);
        var right = list.subList(middle,list.size);

        return merge(mergeSort(left), mergeSort(right))
    }

    fun merge(left: List<Plate>, right: List<Plate>): List<Plate>  {
        var indexLeft = 0
        var indexRight = 0
        var newList : MutableList<Plate> = mutableListOf()

        while (indexLeft < left.count() && indexRight < right.count()) {
            if (left[indexLeft].placa!!.compareTo(right[indexRight].placa!!) <= 0) {
                newList.add(left[indexLeft])
                indexLeft++
            } else {
                newList.add(right[indexRight])
                indexRight++
            }
        }

        while (indexLeft < left.size) {
            newList.add(left[indexLeft])
            indexLeft++
        }

        while (indexRight < right.size) {
            newList.add(right[indexRight])
            indexRight++
        }
        return newList;
    }
}
