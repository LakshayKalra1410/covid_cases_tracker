package com.example.covid_cases_tracker

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.meracovidtracker.R
import org.json.JSONException
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var worldCasesTV:TextView
    lateinit var worldRecoveredTV:TextView
    lateinit var worldDeathTV:TextView
    lateinit var countryCasesTV:TextView
    lateinit var countryRecoveredTV:TextView
    lateinit var countryDeathTV:TextView
    lateinit var stateRV:RecyclerView
    lateinit var StateRvAdapter:StateRvAdapter
    lateinit var StateList:List<StateModel>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        worldCasesTV=findViewById(R.id.idTVWorldCases)
        worldRecoveredTV=findViewById(R.id.idTVWorldRecovered)
        worldDeathTV=findViewById(R.id.idTVWorldDeath)
        countryCasesTV=findViewById(R.id.idTVIndiaCases)
        countryRecoveredTV=findViewById(R.id.idTVIndiaRecovered)
        countryDeathTV=findViewById(R.id.idTVIndiaDeath)
        stateRV=findViewById(R.id.idRVStates)
        StateList=ArrayList<StateModel>()

        getWorldInfo()
        getStateInfo()

    }
    private fun getStateInfo(){
        val url="https://api.rootnet.in/covid19-in/stats/latest"
        val queue=Volley.newRequestQueue(this@MainActivity)
        val request=
            JsonObjectRequest(Request.Method.GET,url,null,{ response->
                try {
                    val dataObj=response.getJSONObject("data")
                    val summaryObj=dataObj.getJSONObject("summary")
                    val cases:Int=summaryObj.getInt("total")
                    val recovered:Int=summaryObj.getInt("discharged")
                    val deaths:Int=summaryObj.getInt("deaths")

                    countryCasesTV.text=cases.toString()
                    countryRecoveredTV.text=recovered.toString()
                    countryDeathTV.text=deaths.toString()

                    val regionalArray=dataObj.getJSONArray("regional")
                    for(i in 0 until regionalArray.length()){
                        val regionalObj=regionalArray.getJSONObject(i)
                        val stateName:String=regionalObj.getString("loc")
                        val cases:Int=regionalObj.getInt("totalConfirmed")
                        val recovered:Int=regionalObj.getInt("discharged")
                        val deaths:Int=regionalObj.getInt("deaths")

                        val stateModal=StateModel(stateName,recovered,deaths,cases)
                        StateList=StateList+stateModal
                    }
                    StateRvAdapter= StateRvAdapter(StateList)
                    stateRV.layoutManager=LinearLayoutManager(this)
                    stateRV.adapter=StateRvAdapter

                }catch (e:JSONException){
                    e.printStackTrace()
                }


            },{ error->
                {
                    Toast.makeText(this,"Fail to get Data",Toast.LENGTH_LONG).show()
                }

            })
        queue.add(request)

    }
    private fun getWorldInfo(){
        val url="https://corona.lmao.ninja/v3/covid-19/all"
        val queue=Volley.newRequestQueue(this@MainActivity)
        val request=
            JsonObjectRequest(Request.Method.GET,url,null,{ response->
                try {
                    val worldCases: Int = response.getInt("cases")
                    val worldRecovered: Int = response.getInt("recovered")
                    val worldDeath: Int = response.getInt("deaths")

                    worldRecoveredTV.text = worldRecovered.toString()
                    worldCasesTV.text = worldCases.toString()
                    worldDeathTV.text = worldDeath.toString()
                }
                catch (e:JSONException){
                    e.printStackTrace()
                }



            },{ error->
                {
                    Toast.makeText(this,"Fail to get Data",Toast.LENGTH_LONG).show()
                }

            })
        queue.add(request)
    }
}