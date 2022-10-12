package com.bihnerdranch.example.poker

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import kotlin.math.max

class MeaningOfMap(private val arrayMap: List<String>, private val mapOnTable: List<String>) {
    init {
        val array = mutableListOf<Any>()
        arrayMap.forEach { array += it }
        Log.d("qwertyu", array.toString())
        println(array)
    }

    fun checkOlderMap(): MutableList<Any> {
        val arr = mutableListOf<Any>()
        val arr2 = mutableListOf<Any>()
        val q: MutableList<Any> = mutableListOf()
        arrayMap.forEach { arr += it.split(" ")[1] }
        mapOnTable.forEach { arr2 += it.split(" ")[1] }
        for (i in arr) {
            for (j in arr2) {
                if (i == j) q += i
            }
        }

        Log.d("pepega", "$q ayaya")
        return q

    }
    fun checkOnFlash(): Int {
        var counter = 0
        val test = arrayMap + mapOnTable
        test.forEach { if (it  == "peaks") counter ++
            Log.d("key", it)
        }
        val q = test.groupingBy { it.split(" ")[0] }.eachCount().filter { it.value ==  it.value}
        Log.d("key", q.toString())
//        if (mapOnTable.forEach { s -> s.split() == q.values.maxOf { it } })
        return q.values.maxOf { it }
    }
    fun checkOnStreet(): Int {
        var counter = 0
        val test = arrayMap + mapOnTable
        var a = mutableListOf<Int>()
        val b = mutableListOf<Int>()
        val c = mutableListOf<Int>()
        mapOnTable.forEach { b += it.split(" ")[1].toInt() }
        test.forEach { a += it.split(" ")[1].toInt() }
        a = a.sorted().toMutableList()
        for (i in 0 until a.size - 1) {
            Log.d("keyq", "${a[i].toString()} ,${a[i + 1].toString()}")
            if (a[i +1] - a[i] == 1) {
                counter++
                c += a[i]
            }
        }
        Log.d("keyq", "${c.toString()} $counter")
        c.forEach{ if (it in b) return counter  }
        Log.d("keyq", "${c.toString()} $counter")
        return 0
    }
    fun checkOnSet(): Int {
        var counter = 0
        val test = arrayMap + mapOnTable
        val arrayWithoutMapInHand = arrayMap
        test.forEach { if (it  == "peaks") counter ++
        Log.d("key", it)
        }
        val q = test.groupingBy { it.split(" ")[1] }.eachCount().filter { it.value ==  it.value}
        if (arrayWithoutMapInHand.groupingBy { it.split(" ")[1] }.eachCount().filter { it.value == it.value }.values.maxOf { it } == q.values.maxOf { it }) return 0
        else
            return q.values.maxOf { it }

    }
    @SuppressLint("SuspiciousIndentation")
    fun checkOnCare(): Int {
        var counter = 0
        val test = arrayMap + mapOnTable
        val arrayWithoutMapInHand = arrayMap
        test.forEach { if (it  == "peaks") counter ++
            Log.d("key", it)
        }
        val q = test.groupingBy { it.split(" ")[1] }.eachCount().filter { it.value ==  it.value}
        Log.d("keyq", q.values.maxOf { it }.toString())
        counter =  (q.values.maxOf { it })
        if (arrayWithoutMapInHand.groupingBy { it.split(" ")[1] }.eachCount().filter { it.value == it.value }.values.maxOf { it } == q.values.maxOf { it }) return 0
        else
        return q.values.maxOf { it }
    }
    fun checkOnFullHouse() {
        TODO()
    }
    fun determineThwWinningCombination(): Int {
        val winOlderMap = checkOlderMap()
        val winStreet = checkOnStreet()
        val winCare = checkOnCare()
        val winSet = checkOnSet()
        val winFlash = checkOnFlash()
        when {
            winStreet >= 4 -> return 1
            winCare >= 4 -> return 2
            winSet == 3 -> return 3
            winFlash >= 5 -> return 4
            winOlderMap.isNotEmpty() -> return 5

        }
        return 6
    }
}
