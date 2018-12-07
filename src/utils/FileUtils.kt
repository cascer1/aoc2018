package utils

import java.io.BufferedReader
import java.io.InputStreamReader


object FileUtils {

    public fun loadFileAsBufferedReader(fileName: String): BufferedReader {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource(fileName)!!.openStream()

        return BufferedReader(InputStreamReader(file))
    }

    public fun loadFileAsString(fileName: String): String {
        loadFileAsBufferedReader(fileName).use { br ->
            return br.readLine()
        }
    }

}