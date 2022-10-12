package com.bihnerdranch.example.poker

import android.util.Log
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*

suspend fun server(combination: MutableList<String>) {
    GlobalScope.launch {
        Log.d("pupa", "aoiwejhfoajwe")
        val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
            .connect(InetSocketAddress("185.242.107.62", 5678))
        val input = socket.openReadChannel()
        val output = socket.openWriteChannel(autoFlush = true)

        output.writeFully("$combination\n".toByteArray())
        Log.d("pupa", "${input.readUTF8Line()}")
        withContext(Dispatchers.IO) {
            socket.close()
        }
    }
}
@OptIn(DelicateCoroutinesApi::class)
suspend fun serverHost(): String {
    var test = ""
    GlobalScope.launch {
        val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
            .connect(InetSocketAddress("185.242.107.62", 5678))
        val input = socket.openReadChannel()
        val output = socket.openWriteChannel(autoFlush = true)
        output.writeFully("a\n".toByteArray())
        val readyInput = input.readUTF8Line()
        test = readyInput.toString()

        Log.d("testTime", "$test in cor")
        withContext(Dispatchers.IO) {
            socket.close()
        }
    }
    delay(500L)
    Log.d("testTime", "$test well")
    return test
}


//suspend fun test() : String = runBlocking {
//    return@runBlocking async { serverHost() }
//}.await()
//var pepega = "qwe"
//fun qqq(test: String): String {
//    Log.d("pupa", test)
//    pepega = test
//    return test
//}
//suspend fun v(): String {
//    return qqq(test())
//}