package com.example.dispensercalibrator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.dispensercalibrator.Backend.Room.tester
import com.example.dispensercalibrator.Frontend.UI.Screens.MyScreens
import com.example.dispensercalibrator.Frontend.UI.Screens.MyScreensVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
          val vm:MyScreensVM by viewModels()
          @Inject
          lateinit var cal : tester
          override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    enableEdgeToEdge()
                    val vm:MyScreensVM by viewModels()

                    setContent {
                                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                                  Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(innerPadding)) {

                                                         //   val controller = rememberNavController()
                                                         //   MyScreens().MyHost(controller, vm)

                                                            Button(
                                                                      onClick = {
                                                                            //    vm.mydata.cylinderId = "whatsapp"
                                                                                cal.something()
                                                                               // println("SHOWING VALUE OF CAL = ${cal}")
                                                                      },
                                                                      modifier = Modifier.height(50.dp).padding( start = 150.dp)
                                                            ) {
                                                                      Text("TESTER")
                                                            }
                                                  }
                                        }
                    }
          }
}
