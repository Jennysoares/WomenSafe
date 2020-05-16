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