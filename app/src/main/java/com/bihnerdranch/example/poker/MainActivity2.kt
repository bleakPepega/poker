package com.bihnerdranch.example.poker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity2 : AppCompatActivity() {
    lateinit var mLeftImageView: ImageView
    lateinit var mRightImageView: ImageView
    lateinit var mMidImageView: ImageView
    lateinit var checkButton: Button
    lateinit var foldButton: Button
    lateinit var raiseButton: Button
    lateinit var fortyImageView:ImageView
    lateinit var fivtyImageView: ImageView
    lateinit var firstMapInHandView: ImageView
    lateinit var secondMapInHandImageView: ImageView
    lateinit var enterOfChipText: TextView
    lateinit var barForRaise: SeekBar
    var checkHost: Int = 0
    var checkForFiveMap = true
    var arrayDeck = mutableListOf<Int>()
    val mapForMap = mutableMapOf<Int, String>()
    val arrForTable = mutableListOf<String>()
    val arrForHand = mutableListOf<String>()




    var chips: Int = 1000
    var checkForFirstPress = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val count = intent.extras?.getString("key")
        checkHost = count.toString().toInt()
        mLeftImageView =  findViewById(R.id.imageview_left);
        mMidImageView = findViewById(R.id.imageview_mid)
        mRightImageView = findViewById(R.id.imageview_right)
        fortyImageView = findViewById(R.id.fourthMap)
        checkButton = findViewById(R.id.check)
        fivtyImageView = findViewById(R.id.fivtyMap)
        secondMapInHandImageView = findViewById(R.id.secondMApInHand)
        firstMapInHandView = findViewById(R.id.handMap)
        raiseButton = findViewById(R.id.raise)
        enterOfChipText = findViewById(R.id.enterChip)
        barForRaise = findViewById(R.id.berForRaise)
        barForRaise.visibility = View.GONE
        foldButton = findViewById(R.id.fold)
        lifecycleScope.launch {random()}

    }
    @SuppressLint("ResourceType")
    suspend fun random() {
        if (checkHost != 1) {
        for (i in 2 .. 14) {
            arrayDeck.add(resources.getIdentifier("peaks_$i", "drawable", packageName))
            mapForMap[resources.getIdentifier("peaks_$i", "drawable", packageName)] = "peaks $i"

        }
        for (i in 2 .. 14) {
            arrayDeck.add(resources.getIdentifier("buby_$i", "drawable", packageName))
            mapForMap[resources.getIdentifier("buby_$i", "drawable", packageName)] = "buby $i"

        }
        for (i in 2 .. 14) {
            arrayDeck.add(resources.getIdentifier("heart_$i", "drawable", packageName))
            mapForMap[resources.getIdentifier("heart_$i", "drawable", packageName)] = "heart $i"

        }
        for (i in 2 .. 14) {
            arrayDeck.add(resources.getIdentifier("tref_$i", "drawable", packageName))
            mapForMap[resources.getIdentifier("tref_$i", "drawable", packageName)] = "tref $i"

        }
        mapForMap[arrayDeck[0]]?.let { Log.d("testMap", it) }
        arrayDeck = arrayDeck.shuffled().toMutableList()
            distributionOfCard()

//        Thread {
//            Log.d("pupa", lifecycleScope.launch {   server(1).toString()}.toString())
//        }.start()

    }
        else {
            deckForSecondPlayer()
            delay(1000L)
            distributionOfCard()


        }
    }
    fun showTheFirstThreeStreets() {
        mRightImageView.setImageResource(arrayDeck[0])
        mLeftImageView.setImageResource(arrayDeck[1])
        mMidImageView.setImageResource(arrayDeck[2])
    }
    fun distributionOfCard() {
        Log.d("testil", "$arrayDeck")
        var flagForCheckOfRaiseOrCheck = true
        if (checkHost == 1) {
            firstMapInHandView.setImageResource(arrayDeck[8])
            secondMapInHandImageView.setImageResource(arrayDeck[7])
        }
        else {
            firstMapInHandView.setImageResource(arrayDeck[6])
            secondMapInHandImageView.setImageResource(arrayDeck[5])
        }
        checkButton.setOnClickListener {
            checkForFirstPress += 1
            if (flagForCheckOfRaiseOrCheck) {
                showTheFirstThreeStreets()
                flagForCheckOfRaiseOrCheck = false
            }
            if (checkForFirstPress > 1) {
                if (checkForFiveMap) {
                    Toast.makeText(this@MainActivity2, "Raise", Toast.LENGTH_SHORT).show()
                    fortyImageView.setImageResource(arrayDeck[3])
                    checkForFiveMap = false
                } else fivtyImageView.setImageResource(arrayDeck[4])
            }
        }
        raiseButton.setOnClickListener {
            if (flagForCheckOfRaiseOrCheck) {
                showTheFirstThreeStreets()
                flagForCheckOfRaiseOrCheck = false
            }
            tips(2)
        }
        foldButton.setOnClickListener {

            for (i in 0..4) {
//                mapForMap[arrayDeck[i]]?.let { it1 -> Log.d("testMap", it1) }
//                mapForMap[arrayDeck[i]]?.split("_")?.get(1)?.let { it1 -> arr.add(it1) }
                mapForMap[arrayDeck[i]]?.let { it1 -> arrForTable.add(it1) }
            }
            for (i in 5..8) {
                mapForMap[arrayDeck[i]]?.let { it1 -> arrForHand.add(it1) }

            }
            Log.d("testMap", arrForHand.toString())
//            Toast.makeText(this@MainActivity2,
//                MeaningOfMap(arrForTable, arrForHand).determineThwWinningCombination(), Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {checkTheWinner()}


        }
    }
    fun tips(quanity: Int) {
        barForRaise.visibility = View.VISIBLE
        barForRaise.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@MainActivity2,
                    "Progress is: " + seek.progress + "%",
                    Toast.LENGTH_SHORT).show()
                chips -= seek.progress
                enterOfChipText.text = chips.toString()

            }
        })

        }
    suspend fun deckForSecondPlayer() {
        var deckSecpndPlayer = ""
        val listForDeck = mutableListOf<String>()
        deckSecpndPlayer = serverHost()
        deckSecpndPlayer = deckSecpndPlayer.replace("[", "")
        deckSecpndPlayer = deckSecpndPlayer.replace("]", "")
        for (i in 1 until deckSecpndPlayer.length) {
            if (deckSecpndPlayer[i] == ' ' && deckSecpndPlayer[i - 1] != ',') {
                deckSecpndPlayer =
                    StringBuilder(deckSecpndPlayer).also { it.setCharAt(i, '_') }.toString()
            }
        }
        val deckSecpndPlayerToLIst = deckSecpndPlayer.split(", ")
        Log.d("testil", deckSecpndPlayer)
        for (i in 0..4) {
            listForDeck += deckSecpndPlayerToLIst[i]
        }
        for (i in 5..8) {
            listForDeck += deckSecpndPlayerToLIst[i]
        }

        listForDeck.forEach { arrayDeck += resources.getIdentifier(it,"drawable", packageName) }
    }
    suspend fun checkTheWinner() {
        var deckSecpndPlayer = ""
        val listForDeck = mutableListOf<String>()
        val listForDeck1 = mutableListOf<String>()

        if (checkHost == 1) {
            deckSecpndPlayer = serverHost()
            deckSecpndPlayer = deckSecpndPlayer.replace("[", "")
            deckSecpndPlayer = deckSecpndPlayer.replace("]", "")

            val deckSecpndPlayerToLIst = deckSecpndPlayer.split(", ")
            for (i in 0..4) {
                listForDeck += deckSecpndPlayerToLIst[i]
            }
            for (i in 6..8) {
                listForDeck1 += deckSecpndPlayerToLIst[i]
            }
        }
        else {

            server((arrForTable + arrForHand) as MutableList<String>)
            if (MeaningOfMap(arrForTable,listForDeck1).determineThwWinningCombination() < MeaningOfMap(arrForTable, arrForHand).determineThwWinningCombination())
                Toast.makeText(this@MainActivity2, "second Player Win", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this@MainActivity2, "first Player Win", Toast.LENGTH_SHORT).show()
        }
    }
}