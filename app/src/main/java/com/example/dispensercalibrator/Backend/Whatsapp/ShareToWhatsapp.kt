package com.example.dispensercalibrator.Backend.Whatsapp

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.example.dispensercalibrator.Backend.Room.EachCardState
import com.example.dispensercalibrator.Frontend.UI.Screens.MyScreensVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class ShareToWhatsapp(val vm: MyScreensVM) {
          lateinit var items: ArrayList<EachCardState>
          suspend fun trigger(): Intent {
                    val scope = CoroutineScope(Dispatchers.IO)
                    val kick = scope.async {
                              val results = vm.dao.retrieveAll()
                              val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                        results.forEach {
                                                  setType("text/plain")
                                                  putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            """ 
                                                                      |date: ${it.date} 
                                                                      |cylinderId: ${it.cylinderId}
                                                                      |Empty: ${it.Empty}
                                                                      |Final: ${it.Final}
                                                                      |Difference: ${it.Difference}
                                                                      |Tolerance: ${it.Tolerance}
                                                                      |DM: ${it.DM}
                                                                      |Litres: ${it.Litres}
                                                                      |ExpectedLitres: ${it.ExpectedLitres}
                                                                      |Side: ${it.Side}
                                                                      |DispenserModel: ${it.DispenserModel}
                                                                      |LoadDensity: ${it.LoadDensity}
                                                                      |Temperature: ${it.Temperature}
                                                                      |DispenserSN: ${it.DispenserSN}
                                                                      |Full: ${it.Full}
                                                                      |Station: ${it.Station}
                                                                      |State: 
                                                            """.trimMargin()
                                                  )
                                                  setPackage("com.whatsapp.w4b") // Directs intent to WhatsApp
                                                  setFlags(FLAG_ACTIVITY_NEW_TASK)
                                        }
                              }
                              sendIntent
                    }.await()
                    return kick
          }
}