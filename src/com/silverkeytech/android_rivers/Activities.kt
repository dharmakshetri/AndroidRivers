/*
Android Rivers is an app to read and discover news using RiverJs, RSS and OPML format.
Copyright (C) 2012 Dody Gunawinata (dodyg@silverkeytech.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
*/


package com.silverkeytech.android_rivers

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import java.net.SocketException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import org.apache.http.conn.ConnectTimeoutException
import android.widget.TextView
import android.graphics.Typeface
import go.goyalla.dict.arabicDictionary.file.ArabicReshape
import android.util.Log
import android.util.TypedValue
import com.silverkeytech.android_rivers.riverjs.RiverItemMeta
import java.util.Date
import java.util.Calendar
import com.github.kevinsawicki.http.HttpRequest
import android.os.Build


public fun Activity.getStandardDialogBackgroundColor(): Int {
    val theme = this.getVisualPref().getTheme()
    if (theme == R.style.Theme_Sherlock_Light_DarkActionBar)
        return android.graphics.Color.WHITE
    else if (theme == R.style.Theme_Sherlock)
        return android.graphics.Color.BLACK
    else
        return android.graphics.Color.WHITE
}

public fun Activity.findView<T: View>(id: Int): T {
    return (this.findViewById(id) as T)
}

public fun Activity.isModernTablet() : Boolean{
    val beyondHoneycomb = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
    return this.getResources()!!.getBoolean(R.bool.is_tablet) || beyondHoneycomb
}

public fun Activity.restart() {
    var intent = this.getIntent()
    this.finish()
    this.startActivity(intent)
}

public fun Activity.toastee(text: String, duration: Duration = Duration.QUICK, grav: Int = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL) {
    var t = Toast.makeText(this, text, duration.toInt())
    t!!.setGravity(grav, 0, 0);
    t!!.show()
}

public data class ConnectivityErrorMessage(val timeoutException: String, val socketException: String, val otherException: String)

public fun Activity.handleConnectivityError(e: Exception?, message: ConnectivityErrorMessage) {
    if (e is ConnectTimeoutException)
        this.toastee(message.timeoutException, Duration.AVERAGE)
    else if (e is UnknownHostException || e is SocketException)
        this.toastee(message.socketException, Duration.AVERAGE)
    else
        this.toastee(message.otherException, Duration.AVERAGE)

}

public enum class Duration {
    QUICK
    AVERAGE
    LONG
    public fun toInt(): Int = when(this) {
        QUICK -> 3000
        AVERAGE -> 10000
        LONG -> 30000
    }
}
