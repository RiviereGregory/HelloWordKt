package training.android

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

enum class Direction(val description: String) {
    NORTH("Nord") {
        override fun action() = "marcher"
    },
    EAST("Est") {
        override fun action() = "courir"
    },
    SOUTH("Sud") {
        override fun action() = "sauter"
    },
    WEST("Ouest") {
        override fun action() = "se reposer"
    };

    abstract fun action(): String
}

fun logAction(windDirection: Direction) {
    val action = when (windDirection) {
        Direction.NORTH -> "marcher"
        Direction.EAST -> "courir"
        Direction.SOUTH -> "sauter"
        Direction.WEST -> "se reposer"
    }

    println("Vent du ${windDirection.description}, action = $action")
}

fun filterInts(numbers: Array<Int>, param: (Int) -> Boolean): Array<Int> {
    val filteredNumbers = mutableListOf<Int>()

    for (n in numbers) {
        if (param(n)) {
            filteredNumbers.add(n)
        }
    }

    return filteredNumbers.toTypedArray()
}

fun positiveInt(n: Int): Boolean = n > 0
fun evenInt(n: Int): Boolean = n % 2 == 0

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainActivity"
    var countries = arrayOf<String>(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Anguilla",
        "Antigua & Barbuda",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bermuda",
        "Bhutan",
        "Bolivia",
        "Bosnia &amp; Herzegovina",
        "Botswana",
        "Brazil",
        "British Virgin Islands",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Cape Verde",
        "Cayman Islands",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Congo",
        "Cook Islands",
        "Costa Rica",
        "Cote D Ivoire",
        "Croatia",
        "Cruise Ship",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Estonia",
        "Ethiopia",
        "Falkland Islands",
        "Faroe Islands",
        "Fiji",
        "Finland",
        "France",
        "French Polynesia",
        "French West Indies",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",
        "Greenland",
        "Grenada",
        "Guam",
        "Guatemala",
        "Guernsey",
        "Guinea",
        "Guinea Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Isle of Man",
        "Israel",
        "Italy",
        "Jamaica",
        "Japan",
        "Jersey",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kuwait",
        "Kyrgyz Republic",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macau",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Mauritania",
        "Mauritius",
        "Mexico",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Montserrat",
        "Morocco",
        "Mozambique",
        "Namibia",
        "Nepal",
        "Netherlands",
        "Netherlands Antilles",
        "New Caledonia",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Norway",
        "Oman",
        "Pakistan",
        "Palestine",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Poland",
        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Reunion",
        "Romania",
        "Russia",
        "Rwanda",
        "Saint Pierre & Miquelon",
        "Samoa",
        "San Marino",
        "Satellite",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "South Africa",
        "South Korea",
        "Spain",
        "Sri Lanka",
        "St Kitts & Nevis",
        "St Lucia",
        "St Vincent",
        "St. Lucia",
        "Sudan",
        "Suriname",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syria",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Timor L'Este",
        "Togo",
        "Tonga",
        "Trinidad &amp; Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Turks & Caicos",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "Uruguay",
        "Uzbekistan",
        "Venezuela",
        "Vietnam",
        "Virgin Islands (US)",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )

    val adapter = CountryAdapter(countries, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected = networkInfo?.isConnectedOrConnecting ?: false

        Log.i(TAG, "Téléphone connecté à internet ? $isConnected")


        val numbers = arrayOf(-99, -42, -6, -5, 0, 18, 42, 58, 99)
        val positiveNumbers = filterInts(numbers, ::positiveInt)

        println("Nombres positifs : ${Arrays.toString(positiveNumbers)}")

        val eventNumbers = filterInts(numbers, ::evenInt)

        println("Nombres pair : ${Arrays.toString(eventNumbers)}")

        val evenPositiveNumbers = filterInts(filterInts(numbers, ::positiveInt), ::evenInt)

        println("Nombres positifs et pair : ${Arrays.toString(evenPositiveNumbers)}")

        val windDirection = Direction.EAST
        println(windDirection)
        logAction(windDirection)
        println("Direction.action() -> ${windDirection.action()}")

        var age = execute(10, Operation.Add(1))
        println("Addition $age")

        age = execute(age, Operation.Subtract(5))
        println("Soutraction $age")


        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val button = findViewById<Button>(R.id.start_activity_green_button)
        button.setOnClickListener {
            println("Start green activity cllick")
            val intent = Intent(this, GreenActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            intent.addCategory("UserViewer")
            intent.putExtra("name", "Bob")
            intent.putExtra("age", 10)
            startActivity(intent)
        }

        val user = User("Bob", 10)

        findViewById<Button>(R.id.start_activity_user_detail).setOnClickListener {
            val intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }


        Log.v(TAG, "verbose message")
        Log.d(TAG, "debug message")
        Log.i(TAG, "Info message")
        Log.w(TAG, "Warning message")
        Log.e(TAG, "Error message")
        Log.println(Log.ASSERT, TAG, "Assert message")

        findViewById<Button>(R.id.delete_fragment).setOnClickListener {
            val fragment = ConfirmDeleteDialogFragment()
            fragment.listener = object : ConfirmDeleteDialogFragment.ConfirmeDeleteListener {
                override fun onDialogPositiveClick() {
                    Log.i("MainActivity", "onDialogPositive")
                    val fileListFragment = FileListDialogFragment()
                    fileListFragment.show(fragmentManager, "fileList")
                }

                override fun onDialogNegativeClick() {
                    Log.i("MainActivity", "onDialogNegative")
                }
            }

            fragment.show(fragmentManager, "confirmeDelete")
        }


        val recyclerView = findViewById<RecyclerView>(R.id.countries_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.update_countries_button).setOnClickListener { updateCountries() }

    }

    fun updateCountries() {
        val lastLetter = countries[0].last()
        for ((index, country) in countries.withIndex()) {
            if (lastLetter.isUpperCase()) {
                countries[index] = country.toLowerCase().capitalize()
            } else {
                countries[index] = country.toUpperCase()
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.main_menu, menu)
        Log.w(TAG, "onCreateOptionsMenu")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this, GreenActivity::class.java)
                intent.action = Intent.ACTION_VIEW
                intent.addCategory("UserViewer")
                intent.putExtra("name", "Bob")
                intent.putExtra("age", 10)
                startActivity(intent)
                return true
            }
            R.id.action_add -> {
                Toast.makeText(this, "Ajouter", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_delete -> {
                Toast.makeText(this, "Supprimer", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_save -> {
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            val index = view.tag as Int
            val country = countries[index]
            Toast.makeText(this, "Pays selectionné : $country", Toast.LENGTH_SHORT).show()
        }

    }
}
