package training.android

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) :
    SQLiteOpenHelper(context, "test.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT, age INTEGER, email TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
// Nothing
    }

    fun createUtilisateur(utilisateur: Utilisateur) {
        val values = ContentValues()
        values.put("name", utilisateur.name)
        values.put("age", utilisateur.age)
        values.put("email", utilisateur.email)

        writableDatabase.insert("users", null, values)
    }

    fun deleteUtilisateur(utilisateur: Utilisateur) {
        val values = arrayOf(
            utilisateur.name,
            utilisateur.age.toString(),
            utilisateur.email
        )

        writableDatabase.delete("users", "name =? and age=? and email =?", values)
    }

    fun getAllUsers(): MutableList<Utilisateur> {
        val users = mutableListOf<Utilisateur>()

        readableDatabase.rawQuery("SELECT * FROM users", null).use { cursor ->
            while (cursor.moveToNext()) {
                val user = Utilisateur(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("age")),
                    cursor.getString(cursor.getColumnIndex("email"))
                )

                users.add(user)
            }
        }


        return users
    }

    fun getUsersCount(): Int = DatabaseUtils.queryNumEntries(
        readableDatabase, "users", null
    ).toInt()
}