package com.example.dispensercalibrator.Backend.Whatsapp_3

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import com.example.dispensercalibrator.Backend.Room_2.EachCardState
import com.example.dispensercalibrator.Frontend.UI.Screens_1.MyScreensVM
import com.example.dispensercalibrator.R
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.Date
import kotlin.collections.ArrayList

class ShareToWhatsapp(val vm: MyScreensVM, val context: Context) {
          lateinit var items: ArrayList<EachCardState>

        /*  fun drawableToByteArray(): Uri {
                    val bitmap =  context.resources.getDrawable(R.drawable.icons8_delete_48, null).toBitmap()
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "mylogotest", null)
                    return Uri.parse(path.toString())
          }*/

        suspend fun trigger(): Intent {
                  val scope = CoroutineScope(Dispatchers.IO)
                  val kick = scope.async {
                            val results = vm.dao.retrieveCalibrationResult()        // We are not persisting multiple instances of Calibration data. Each instance Overwrites the previous in the Room DB, that's why it does not say retrieveCalibrationResult(s).
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {

                                      // An exception is made for the "Result" property where its value is pulled directly form the VM variable rather than form Room DB like all others beacuse Room sometimes delays to persit it.
                                                setType("text/plain")
                                                putExtra(
                                                          Intent.EXTRA_TEXT,
                                                          """
                                                                      |Date: ${Date()}
                                                                      |CylinderId:  ${results.cylinderId}
                                                                      |Empty:  ${results.Empty}
                                                                      |Final:  ${results.Final}
                                                                      |Difference:  ${results.Difference}
                                                                      |
                                                                      |DM:  ${results.DM}
                                                                      |
                                                                      |Litres:  ${results.Litres}
                                                                      |ExpectedLitres:  ${results.ExpectedLitres}
                                                                      |Full:  ${results.Full}
                                                                      |Tolerance:  ${results.Tolerance}
                                                                      |Side:  ${results.Side}
                                                                      |DispenserModel:  ${results.DispenserModel}
                                                                      |LoadDensity:  ${results.LoadDensity}
                                                                      |Temperature:  ${results.Temperature}
                                                                      |DispenserSN:  ${results.DispenserSN}
                                                                      |Station:  ${results.Station}
                                                                      |Result:  ${vm.changeVisualResult}   
                                                            """.trimMargin()
                                                )
                                                // setType("image/*")
                                                // putExtra(Intent.EXTRA_STREAM, drawableToByteArray())
                                                // addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                setPackage("com.whatsapp.w4b") // Directs intent to WhatsApp
                                                setFlags(FLAG_ACTIVITY_NEW_TASK)

                            }
                            sendIntent
                  }.await()
                  return kick
        }
}



/*val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                       setType("image/*")
                                       putExtra(Intent.EXTRA_STREAM, drawableToByteArray())
                                       addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                       setPackage("com.whatsapp.w4b") // Directs intent to WhatsApp
                                       setFlags(FLAG_ACTIVITY_NEW_TASK)
                             }*/