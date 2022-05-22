package code.hyunwa.githubuserprofile.util

import android.app.Activity
import android.app.Dialog

class Helpers {

    fun showProgressDialog(activity: Activity) : Dialog{
        val dialog = Dialog(activity)
//        var dialogLayout = activity.layoutInflater.inflate(R.layout.dialog_loader, null)
//        dialog?.let {
//            it.setContentView(dialogLayout)
//            it.setCancelable(false)
//            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        }
        return dialog
    }
}