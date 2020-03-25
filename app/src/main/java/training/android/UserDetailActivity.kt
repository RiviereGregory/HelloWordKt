package training.android

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    val TAG = "UserDetailActivity"

    var users = arrayListOf<Utilisateur>()
    val adapter = UserAdapter(users, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        val database = Database(this)

        if (database.getUsersCount() == 0) {
            //initialisation de la base de donnée
            database.createUtilisateur(Utilisateur("Bob", 10, "bob@bob.com"))
            database.createUtilisateur(Utilisateur("Bobette", 4, "bobette@bob.com"))
            database.createUtilisateur(Utilisateur("Mike", 14, "mike@bob.com"))
            database.createUtilisateur(Utilisateur("Jane", 17, "jane@bob.com"))
        }

        database.getAllUsers().toCollection(users)

        for (user in users) {
            Log.i(TAG, "utilisateur base de données: $user")
        }


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


        val recyclerView = findViewById<RecyclerView>(R.id.users_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.update_users_button).setOnClickListener { updateUsers() }
    }

    fun updateUsers() {
        val database = Database(this)
        users.clear()
        if (database.getUsersCount() > 4) {
            //Pour vider la base de données
            if (database.getUsersCount() != 0) {
                for (user in database.getAllUsers()) {
                    database.deleteUtilisateur(user)
                }
            }
            //initialisation de la base de donnée
            database.createUtilisateur(Utilisateur("Bob", 10, "bob@bob.com"))
            database.createUtilisateur(Utilisateur("Bobette", 4, "bobette@bob.com"))
            database.createUtilisateur(Utilisateur("Mike", 14, "mike@bob.com"))
            database.createUtilisateur(Utilisateur("Jane", 17, "jane@bob.com"))
            database.getAllUsers().toCollection(users)
        } else {
            //Pour vider la base de données
            if (database.getUsersCount() != 0) {
                for (user in database.getAllUsers()) {
                    database.deleteUtilisateur(user)
                }
            }
            database.createUtilisateur(Utilisateur("Bobi", 10, "bobi@bob.com"))
            database.createUtilisateur(Utilisateur("Bobet", 4, "bobet@bob.com"))
            database.createUtilisateur(Utilisateur("Nike", 14, "nike@bob.com"))
            database.createUtilisateur(Utilisateur("Joe", 27, "joe@bob.com"))
            database.createUtilisateur(Utilisateur("Peier", 30, "peier@bob.com"))
            database.createUtilisateur(Utilisateur("Paul", 44, "paul@bob.com"))
            database.createUtilisateur(Utilisateur("George", 74, "jack@bob.com"))
            database.createUtilisateur(Utilisateur("Jack", 17, "jane@bob.com"))
            database.createUtilisateur(Utilisateur("Mars", 19, "mars@bob.com"))
            database.createUtilisateur(Utilisateur("Vincent", 25, "vincent@bob.com"))
            database.createUtilisateur(Utilisateur("Jill", 34, "jill@bob.com"))
            database.createUtilisateur(Utilisateur("Marc", 57, "marc@bob.com"))
            database.getAllUsers().toCollection(users)
        }

        adapter.notifyDataSetChanged()
    }

    private fun schedulRefreshStop() {
        var handler = Handler()
        handler.postDelayed({ swipeRefreshLayout.isRefreshing = false }, 2000)
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            val index = view.tag as Int
            val user = users[index]
            Toast.makeText(this, "$user", Toast.LENGTH_SHORT).show()
        }
    }
}
