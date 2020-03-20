package training.android

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class GreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_green)

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        progressBar.max = 100
        progressBar.progress = 25
        progressBar.secondaryProgress = 50

        findViewById<Button>(R.id.update_progress).setOnClickListener {
            progressBar.incrementProgressBy(5)
            progressBar.secondaryProgress += 3
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val action = intent.action
        val isUserViewer = intent.hasCategory("UserViewer")
        val extras: Bundle = intent.extras
        val name = extras.getString("name")
        val age = extras.getInt("age")
        Log.i("GreenActivity", "action : $action  name : $name  age: $age")
    }
}
