package training.android

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserDetailActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    val TAG = "UserDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://httpbin.org/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val service: HttpBinServiceString = retrofit.create(HttpBinServiceString::class.java)

        val call: Call<String> = service.getUserAgent()
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i(TAG, "User agent response: ${response?.body()}")
            }

        })

        //JSON
        val retrofitJson = Retrofit.Builder()
            .baseUrl("http://httpbin.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serviceJson = retrofitJson.create(HttpBinServiceJson::class.java)
        val callJson = serviceJson.getUserInfo()
        callJson.enqueue(object : Callback<GetData> {
            override fun onFailure(call: Call<GetData>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<GetData>, response: Response<GetData>) {
                val getData = response?.body()
                Log.i(TAG, "Received url : ${getData?.url}")
            }

        })

        swipeRefreshLayout = findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener {
            schedulRefreshStop()
        }

        findViewById<Button>(R.id.refresh).setOnClickListener {
            swipeRefreshLayout.isRefreshing = true
            schedulRefreshStop()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>("user")
        val nameTextView = findViewById<TextView>(R.id.name)
        val ageTextView = findViewById<TextView>(R.id.age)

        nameTextView.setText("Nom: ${user.name}")
        ageTextView.setText("Age: ${user.age}")


    }

    private fun schedulRefreshStop() {
        var handler = Handler()
        handler.postDelayed({ swipeRefreshLayout.isRefreshing = false }, 2000)
    }
}
