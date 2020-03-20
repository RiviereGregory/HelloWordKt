package training.android

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.app.DialogFragment


class ConfirmDeleteDialogFragment : DialogFragment() {

    interface  ConfirmeDeleteListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    val TAG = ConfirmDeleteDialogFragment::class.java.simpleName

    var listener: ConfirmeDeleteListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Supprimer tout le contenu du téléphone ?")
            .setPositiveButton("Oh oui !", object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, id: Int) {
                    Log.i(TAG,"Youpi ! on va tout casser")
                    listener?.onDialogPositiveClick()
                }
            })
            .setNegativeButton("Euh .. Non", DialogInterface.OnClickListener{ dialog, id ->
                Log.i(TAG, "Ouf...")
                listener?.onDialogNegativeClick()
            })

        return builder.create()
    }


}