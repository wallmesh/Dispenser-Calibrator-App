package com.example.dispensercalibrator.Frontend.UI.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MyScreens(){

          @Composable
          fun mainScreen(vm: MyScreensVM){
                    val vscroll = rememberScrollState()
                    Scaffold (
                              bottomBar = {
                                        BottomAppBar(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                                                  Button(
                                                            onClick = {
                                                                      vm.setRoomData()
                                                                      vm.pushToRoomDB()
                                                  },
                                                            modifier = Modifier.height(50.dp))
                                                  {
                                                            Text("Insert data")
                                                  }

                                                  Button(
                                                            onClick = {
                                                                      vm.mydata.cylinderId = "whatsapp"
                                                                      println("SECOND INSTANCE = ${vm.mydata.cylinderId}")
                                                            },
                                                            modifier = Modifier.height(50.dp).padding( start = 150.dp)
                                                  ) {
                                                            Text("TESTER")
                                                  }
                                        }
                              }
                    ){ innerPadding ->
                              Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(vscroll)
                              ) {

                                        Row(modifier = Modifier.padding()) {
                                                  Text("Cylinder ID")
                                                  TextField(
                                                            value = vm.changeCylinderID,
                                                            onValueChange = { vm.changeCylinderID(it)}
                                                  )
                                        }

                                        Row(modifier = Modifier.padding()) {
                                                  Text("Empty")
                                                  TextField(
                                                            value = vm.changeEmpty,
                                                            onValueChange = { vm.change_Empty(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Final")
                                                  TextField(
                                                            value = vm.changeFinal,
                                                            onValueChange = { vm.change_Final(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Difference")
                                                  TextField(
                                                            value = vm.changeDifference,
                                                            onValueChange = { vm.change_Difference(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Tolerance")
                                                  TextField(
                                                            value = vm.changeTolerance,
                                                            onValueChange = { vm.change_Tolerance(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("DM")
                                                  TextField(
                                                            value = vm.changeDM,
                                                            onValueChange = { vm.change_DM(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Litres")
                                                  TextField(
                                                            value = vm.changeLitres,
                                                            onValueChange = { vm.change_Litres(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Expected Litres(no DM)")
                                                  TextField(
                                                            value = vm.changeExpectedLitres,
                                                            onValueChange = { vm.change_Expected_Litres(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Side")
                                                  TextField(
                                                            value = vm.changeSide,
                                                            onValueChange = { vm.change_Side(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Disp. Model")
                                                  TextField(
                                                            value = vm.changeDispenserModel,
                                                            onValueChange = { vm.change_Dispenser_Model(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Load Density")
                                                  TextField(
                                                            value = vm.changeLoadDensity,
                                                            onValueChange = { vm.change_Load_Density(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Temperature")
                                                  TextField(
                                                            value = vm.changeTemperature,
                                                            onValueChange = { vm.change_Temperature(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Dispenser SN")
                                                  TextField(
                                                            value = vm.changeDispenserSN,
                                                            onValueChange = { vm.change_Dispenser_SN(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Full")
                                                  TextField(
                                                            value = vm.changeFull,
                                                            onValueChange = { vm.change_Full(it)}
                                                  )
                                        }
                                        Row() {
                                                  Text("Station")
                                                  TextField(
                                                            value = vm.changeStation,
                                                            onValueChange = { vm.change_Station(it)}
                                                  )
                                        }
                              }
                    }
          }


          @Composable
          fun MyHost(
                    controller: NavHostController,
                    vm: MyScreensVM
          ){
                    NavHost(controller,  startDestination = "mainScreen"){

                              composable("mainScreen"){
                                     //   val vm = hiltViewModel<MyScreensVM>()
                                        mainScreen(vm)
                              }
                    }
          }
}