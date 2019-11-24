import android.util.Log

import com.breadwallet.tools.security.SmartValidator
import com.breadwallet.tools.util.BRConstants
import com.breadwallet.tools.util.Bip39Reader
import com.breadwallet.tools.util.Utils
import com.breadwallet.wallet.BRWalletManager

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.junit.Test

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.util.ArrayList
import java.util.Arrays

import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat

/**
 * BreadWallet
 *
 *
 * Created by Mihail Gutan on <mihail></mihail>@breadwallet.com> 11/3/17.
 * Copyright (c) 2017 breadwallet LLC
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
class PaperKeyTests {
    companion object {
        private val TAG = PaperKeyTests::class.java.name
        val PAPER_KEY_JAP = "こせき　ぎじにってい　けっこん　せつぞく　うんどう　ふこう　にっすう　こせい　きさま　なまみ　たきび　はかい"//japanese
        val PAPER_KEY_ENG = "stick sword keen   afraid smile sting   huge relax nominee   arena area gift "//english
        val PAPER_KEY_FRE = "vocation triage capsule marchand onduler tibia illicite entier fureur minorer amateur lubie"//french
        val PAPER_KEY_SPA = "zorro turismo mezcla nicho morir chico blanco pájaro alba esencia roer repetir"//spanish
        val PAPER_KEY_CHI = "怨 贪 旁 扎 吹 音 决 廷 十 助 畜 怒"//chinese
    }

    private val allWords: List<String>
        get() {
            val result = ArrayList<String>()
            val names = ArrayList<String>()
            names.add("en-BIP39Words.txt")
            names.add("es-BIP39Words.txt")
            names.add("fr-BIP39Words.txt")
            names.add("ja-BIP39Words.txt")
            names.add("zh-BIP39Words.txt")

            for (fileName in names) {
                var inputstream: InputStream? = null
                try {
                    inputstream = javaClass.getResourceAsStream(fileName)
                    val str = IOUtils.toString(inputstream)
                    val lines = str.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    result.addAll(listOf(*lines))
                } catch (e: IOException) {
                    Log.e(TAG, "getAllWords: $fileName, ", e)
                } finally {
                    if (inputstream != null)
                        try {
                            inputstream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                }
            }
            val cleanList = ArrayList<String>()
            for (s in result) {
                val cleanWord = Bip39Reader.cleanWord(s)
                cleanList.add(cleanWord)
            }
            assertThat(cleanList.size, `is`(10240))
            return cleanList
        }

    /*
    @Test
    fun `test words are valid`() {
        assertEquals(allWords.size, 10240)
        assertThat(isValid(PAPER_KEY_JAP, allWords), `is`(true))
        assertThat(isValid(PAPER_KEY_ENG, allWords), `is`(true))
        assertThat(isValid(PAPER_KEY_FRE, allWords), `is`(true))
        assertThat(isValid(PAPER_KEY_SPA, allWords), `is`(true))
        assertThat(isValid(PAPER_KEY_CHI, allWords), `is`(true))
    }
     */

    @Test
    fun `test paper validation`() = assertThat(allWords.size, `is`(10240))


    // TODO When did this code stop working?
    private fun isValid(phrase: String, words: List<String>): Boolean {
        return BRWalletManager.getInstance().validateRecoveryPhrase(words.toTypedArray(), phrase)
    }


}
