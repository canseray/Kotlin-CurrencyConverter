package com.example.kotlin_currencyconverter

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun getRates(view: View){
        val download = Download()

        try {

            val url = "http://data.fixer.io/api/latest?access_key=YOURKEY&format=1"
            download.execute(url)

        } catch (e: Exception){
            e.printStackTrace()
        }
    }




    //down işlemini arkaplanda yaptıgın icin async
    inner class Download : AsyncTask<String,Void,String>(){



        override fun doInBackground(vararg params: String?): String {               //vararg birden fazla index-url alabilir,dizi
            var result = ""
            var url: URL
            var httpURLConnection : HttpURLConnection

            try{
                url = URL(params[0])                                                 //tek url verdigin icin [0] index
                httpURLConnection = url.openConnection() as HttpURLConnection        //baglantı ac

                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)                 //verileri oku

                var data = inputStreamReader.read()

                while (data > 0 ){                                                //verileri karaktere çevir sonuca ekle
                    val character = data.toChar()
                    result += character
                    data = inputStreamReader.read()
                }

            } catch (e: Exception){
                e.printStackTrace()
            }
            return result
        }



        //yukardaki islemlerle connection kurduk jsonla alıcaz
        override fun onPostExecute(result: String?) {
            //println(result) logda gör

            try{
                val jsonObject = JSONObject(result)
                val base = jsonObject.getString("base")
                val rates = jsonObject.getString("rates")

                val jsonObject1 = JSONObject(rates)
                val tr = jsonObject.getString("TRY")
                val cad = jsonObject.getString("CAD")
                val gbp = jsonObject.getString("GBP")
                val usd = jsonObject.getString("USD")
                val chf = jsonObject.getString("CHF")

                tryText.text = "TRY" + tr
                cadText.text = "CAD" + cad
                gbpText.text = "GBP" + gbp
                usdText.text = "USD" + usd
                chfText.text = "CHF" + chf








            }catch (e: Exception){
                e.printStackTrace()
            }

            super.onPostExecute(result)
        }
    }
}
