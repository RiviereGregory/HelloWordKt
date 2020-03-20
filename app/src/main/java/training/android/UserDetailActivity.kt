package training.android

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class UserDetailActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

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
