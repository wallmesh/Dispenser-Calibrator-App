package com.example.dispensercalibrator.Frontend.UI.Screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dispensercalibrator.Backend.Room.EachCardState
import com.example.dispensercalibrator.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.nextUp

// TTDs
// 1. Display the data automatically


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
          @Composable
          fun OverviewScreen(vm: MyScreensVM, context: Context, onNavigateToDataEntryScreen: ()->Unit, activity: MainActivity) {
                    Scaffold(
                              bottomBar = {
                                        BottomAppBar {
                                                  Button({
                                                            onNavigateToDataEntryScreen()
                                                          //  activity.auth(activity)
                                                  }) {
                                                            Text("Go to data screen")
                                                  }

                                                  Button({
                                                            try {
                                                                     vm.ShareToWhats(context)
                                                            } catch (ex: android.content.ActivityNotFoundException) {
                                                                      println("Whatsapp not installed")
                                                            }
                                                  }) {
                                                            Text("SHARE")
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
                                                  val number =  vm.dao.retrieveAll().count()
                                                  println("THE NUMBER OF ITEMS IS: $number")
                                                  numberOfItems.intValue = number

                                                  val allItemsFromDB = vm.dao.retrieveAll()
                                                  rememberedItemsHolder = allItemsFromDB.toMutableList()
                                                  println("THE NUMBER OF  REMEMBERED ITEMS IS: ${rememberedItemsHolder.count()}")
                                        }
                              }

                              LazyColumn(modifier = Modifier.padding(innerPadding)) {
                                        item(numberOfItems.intValue){
                                                  rememberedItemsHolder.forEach { it ->
                                                            EachCard(it, vm)
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
                                                 /* Button(
                                                            onClick = {
                                                                      vm.setRoomData()
                                                                      vm.pushToRoomDB()
                                                  },
                                                            modifier = Modifier.height(50.dp))
                                                  {
                                                            Text("Insert data")
                                                  }*/

                                                  Button(
                                                            onClick = {
                                                                      // save data to Room DB
                                                                      vm.setRoomData()
                                                                      vm.pushToRoomDB()

                                                                      vm.change_Expected_Litres()
                                                                      vm.change_Difference()
                                                                      vm.change_DM()

                                                                      // Then Navigate to OverviewScreen
                                                                      onNavigateToOverviewScreen()
                                                            },
                                                            modifier = Modifier.height(50.dp).padding( start = 150.dp)
                                                  ) {
                                                            Text("Go to Overview Screen")  // Pressing this button finest saves the data to Room db before navigating to the Overview Screen
                                                  }
                                        }
                              }
                    ){ innerPadding ->

                                      /*  Row(
                                                  verticalAlignment = Alignment.Top,
                                                  horizontalArrangement = Arrangement.Start,
                                                  modifier = Modifier.fillMaxWidth().padding(start = 5.dp)
                                        )
                                        {
                                                  Column(modifier = Modifier.verticalScroll(vscroll)) {
                                                            val bottomSpacing = 49.dp
                                                            Spacer(modifier = Modifier.padding(bottom = 22.dp))
                                                            Text("Load Density")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Filled Litres")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Tolerance")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Cylinder ID")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Empty")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Final")
                                                            Spacer(modifier = Modifier.padding(bottom = 42.dp))
                                                            Text("Diff(kg)")
                                                            Spacer(modifier = Modifier.padding(bottom = 8.dp))
                                                            Text("Exptd(kg)")
                                                            Spacer(modifier = Modifier.padding(bottom = 8.dp))
                                                            Text("DM")
                                                            Spacer(modifier = Modifier.padding(bottom = 25.dp))
                                                            Text("Full")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Side")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Model")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Temp")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Disp. SN")
                                                            Spacer(modifier = Modifier.padding(bottom = bottomSpacing))
                                                            Text("Station")
                                                            Spacer(modifier = Modifier.padding(bottom = 240.dp))                                                  }
                                        }*/
                              val bottomSpacing = 30.dp
                                        Row (verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth().padding(start =bottomSpacing)){
                                                  Column(modifier = Modifier.verticalScroll(vscroll)) {
                                                            Text("Load Density")
                                                            OutlinedTextField(
                                                                      value = vm.changeLoadDensity,
                                                                      onValueChange = { vm.change_Load_Density(it)},
                                                                      modifier = Modifier.padding(),
                                                                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                                     // label = {Text("Load Density")}
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Filled Litres")
                                                            OutlinedTextField(
                                                                      value = vm.changeLitres,
                                                                      onValueChange = { vm.change_Litres(it)},
                                                                      modifier = Modifier.padding(),
                                                                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                                     // label = {Text("Load Density")}
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Tolerance")
                                                            OutlinedTextField(
                                                                      value = vm.changeTolerance,
                                                                      onValueChange = { vm.change_Tolerance(it)},
                                                                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                                      placeholder = { }
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Cylinder ID")
                                                            OutlinedTextField(
                                                                      value = vm.changeCylinderID,
                                                                      onValueChange = { vm.changeCylinderID(it)},
                                                                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Empty")
                                                            OutlinedTextField(
                                                                      value = vm.changeEmpty,
                                                                      onValueChange = { vm.change_Empty(it)},
                                                                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Final")
                                                            OutlinedTextField(
                                                                      value = vm.changeFinal,
                                                                      onValueChange = { vm.change_Final(it)},
                                                                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("${vm.changeDifference}")
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("${vm.changeExpectedLitres}")
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("${vm.changeDM}")
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Full")
                                                            OutlinedTextField(
                                                                      value = vm.changeFull,
                                                                      onValueChange = { vm.change_Full(it)},
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Side")
                                                            OutlinedTextField(
                                                                      value = vm.changeSide,
                                                                      onValueChange = { vm.change_Side(it)},
                                                                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Model")
                                                            OutlinedTextField(
                                                                      value = vm.changeDispenserModel,
                                                                      onValueChange = { vm.change_Dispenser_Model(it)}
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Temp")
                                                            OutlinedTextField(
                                                                      value = vm.changeTemperature,
                                                                      onValueChange = { vm.change_Temperature(it)}
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Disp. SN")
                                                            OutlinedTextField(
                                                                      value = vm.changeDispenserSN,
                                                                      onValueChange = { vm.change_Dispenser_SN(it)},
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                            Text("Station")
                                                            OutlinedTextField(
                                                                      value = vm.changeStation,
                                                                      onValueChange = { vm.change_Station(it)},
                                                                      keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                                                            )
                                                            Spacer(modifier = Modifier.padding(bottom = 260.dp))
                                                  }
                              }
                    }
          }

          @Composable
          fun EachCard(cal: EachCardState, vm: MyScreensVM){
                    Card(modifier = Modifier.padding(start = 5.dp)) {
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Load Density:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.LoadDensity, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Filled Litres:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Litres, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Tolerance:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Tolerance, )
                              }
                              Spacer(modifier = Modifier.padding(bottom = 10.dp))
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Cylinder ID:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.cylinderId, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Empty:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Empty )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Final:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Final, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Diff(kg):")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  vm.changeDifference.toString() )
                              }
                              //calculated
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Exptd(kg):")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text( vm.changeExpectedLitres.toString(), )
                              }
                              //calculated
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("DM(kg):")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  vm.changeDM.toString() )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Full:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Full, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Side:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(cal.Side)
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Temp:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Temperature, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Disp. Model:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.DispenserModel, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Disp. SN:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.DispenserSN, )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Station:")
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
                    activity: MainActivity,
                    context: Context
          ) {
                    NavHost(controller, startDestination = "dataEntryScreen") {

                              composable("overviewScreen") {
                                        OverviewScreen(vm, context, onNavigateToDataEntryScreen = {
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


class screenActions(val vm: MyScreensVM){


          fun Double.roundToTwoDecimalPlaces(): BigDecimal {
                    return BigDecimal(this.toString()).setScale(2, RoundingMode.HALF_UP)
          }

          fun calculateDifference(): Double {
                    val compute = if (vm.changeFinal.isEmpty() || vm.changeEmpty.isEmpty()){
                             // TODO(implement a TOAST here alerting the user that the field is empty)
                              return 0.0
                    }else{
                              val finalAsDouble = vm.changeFinal.toDouble()
                              val emptyAsDouble = vm.changeEmpty.toDouble()
                              val unroundedResult =  finalAsDouble - emptyAsDouble
                              val finalResult = unroundedResult.roundToTwoDecimalPlaces().toDouble()
                              finalResult
                    }
                    return compute
          }

          fun setDifferenceValue(){
                    vm.changeDifference = calculateDifference()
          }

          fun calculateExpctdkg(): Double {
                   val compute = if ( vm.changeLoadDensity.isEmpty() ||  vm.changeLitres.isEmpty()){
                           //   TODO(implement a TOAST here alerting the user that the field is empty)
                             return 0.0
                    }else{
                              val LoadDensityAsDouble:Double =  vm.changeLoadDensity.toDouble()
                              val LitresAsDouble: Double = vm.changeLitres.toDouble()
                              val unroundedResult = LoadDensityAsDouble * LitresAsDouble
                             val finalResult = unroundedResult.roundToTwoDecimalPlaces().toDouble()
                             finalResult
                    }
                    return compute
          }

          fun setExpctdkg(){
                    vm.changeExpectedLitres = calculateExpctdkg()
          }

          fun calculateDM(): Double {
                    val compute = if (vm.changeExpectedLitres.isNaN() || vm.changeDifference.isNaN()){
                              0.0
                              TODO()
                    }else{
                              val differenceAsDouble:Double =  vm.changeDifference.toDouble()
                              val expectedLitersAsDouble: Double = vm.changeExpectedLitres.toDouble()
                              val unroundedResult = expectedLitersAsDouble.minus(differenceAsDouble)
                              val finalResult = unroundedResult.roundToTwoDecimalPlaces().toDouble()
                              finalResult
                    }
                    return compute
          }

          fun setDM(){
                    vm.changeDM = calculateDM()
          }
}


