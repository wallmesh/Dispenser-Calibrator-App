package com.example.dispensercalibrator.Frontend.UI.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dispensercalibrator.Backend.Room.EachCardState
import com.example.dispensercalibrator.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



          @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
          @Composable
          fun OverviewScreen(vm: MyScreensVM, onNavigateToDataEntryScreen: ()->Unit, activity: MainActivity) {
                    Scaffold(
                              bottomBar = {
                                        BottomAppBar {
                                                  Button({
                                                            onNavigateToDataEntryScreen()
                                                            activity.auth(activity)
                                                  }) {
                                                            Text("Go to data screen")
                                                  }
                                        }
                              }
                    ) { innerPadding ->
                              /*   LaunchedEffect(Unit) {
                                           val scope =  CoroutineScope(Dispatchers.IO).launch {
                                                     vm.dao.retriveAll().forEach { it ->
                                                               vm.cardState.Station = it.Station
                                                               vm.cardState.DM = it.DM
                                                               vm.cardState.Side = it.Side
                                                               vm.cardState.Full = it.Full
                                                               vm.cardState.Temperature = it.Temperature
                                                               vm.cardState.DispenserSN = it.DispenserSN
                                                               vm.cardState.LoadDensity = it.LoadDensity
                                                               vm.cardState.DispenserModel = it.DispenserModel
                                                               vm.cardState.ExpectedLitres = it.ExpectedLitres
                                                               vm.cardState.Litres = it.Litres
                                                               vm.cardState.Tolerance = it.Tolerance
                                                               vm.cardState.Difference = it.Difference
                                                               vm.cardState.Final = it.Final
                                                               vm.cardState.Empty = it.Empty
                                                               vm.cardState.cylinderId = it.cylinderId
                                                               vm.cardState.id = it.id
                                                               vm.cardState.date = it.date
                                                     }
                                           }
                                 }*/

                              val numberOfItems = remember{ mutableIntStateOf(0) }
                              var rememberedItemsHolder = remember { mutableListOf<EachCardState>() }
                              LaunchedEffect(Unit) {

                                        CoroutineScope(Dispatchers.IO).launch {
                                                  val number =  vm.dao.retriveAll().count()
                                                  println("THE NUMBER OF ITEMS IS: $number")
                                                  numberOfItems.intValue = number

                                                  val allItemsFromDB = vm.dao.retriveAll()
                                                  rememberedItemsHolder = allItemsFromDB.toMutableList()
                                                  println("THE NUMBER OF  REMEMBERED ITEMS IS: ${rememberedItemsHolder.count()}")
                                        }
                              }

                              LazyColumn(modifier = Modifier.padding(innerPadding)) {
                                        item(numberOfItems.intValue){
                                                  rememberedItemsHolder.forEach { it ->
                                                            EachCard(it)
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                  }
                                        }
                              }
                    }
          }


          @Composable
          fun DataEntryScreen(vm: MyScreensVM, onNavigateToOverviewScreen:()->Unit){
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
                                                                      onNavigateToOverviewScreen()
                                                            },
                                                            modifier = Modifier.height(50.dp).padding( start = 150.dp)
                                                  ) {
                                                            Text("Go to Overview Screen")
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
          fun EachCard(cal: EachCardState){
                    val vscroll2 = rememberScrollState()
                    Card(modifier = Modifier.padding(start = 5.dp)) {
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Cylinder ID")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.cylinderId, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Empty")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Empty )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Final")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Final, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Difference")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Difference, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Tolerance")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Tolerance, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("DM")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.DM, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Litres")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Litres, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Expected Litres(no DM)")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.ExpectedLitres, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Side")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(cal.Side)
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Disp. Model")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.DispenserModel, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Load Density")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.LoadDensity, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Temperature")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Temperature, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Dispenser SN")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.DispenserSN, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Full")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Full, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Station")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Station, )
                              }
                    }
          }


          @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
          @Composable
          fun MyHost(
                    controller: NavHostController,
                    vm: MyScreensVM,
                    activity: MainActivity
          ) {
                    NavHost(controller, startDestination = "overviewScreen") {

                              composable("overviewScreen") {
                                        OverviewScreen(vm, onNavigateToDataEntryScreen = {
                                                  controller.navigate("dataEntryScreen")
                                        }, activity)
                              }

                              composable("dataEntryScreen") {
                                        //   val vm = hiltViewModel<MyScreensVM>()
                                        DataEntryScreen(vm, onNavigateToOverviewScreen = {
                                                  controller.navigate("overviewScreen")
                                        })
                              }
                    }
          }



