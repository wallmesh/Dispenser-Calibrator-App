package com.example.dispensercalibrator.Frontend.UI.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.util.TableInfo
import com.example.dispensercalibrator.Backend.MyCustomGenericDropdownMenu
import com.example.dispensercalibrator.Backend.Room.EachCardState
import com.example.dispensercalibrator.MainActivity
import com.example.dispensercalibrator.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

// TTDs
// 1. Display the data automatically


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
          @Composable
          fun OverviewScreen(vm: MyScreensVM, context: Context, onNavigateToDataEntryScreen: ()->Unit, activity: MainActivity) {
                    Scaffold(
                              topBar = {
                                        TopAppBar(
                                                  title = {"Data Screen"},
                                                  navigationIcon = { Image(painterResource(R.drawable.back_arrow),  contentDescription = "back")}
                                        )
                              },
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
                                                            EachCard(it, vm, context)
                                                            Spacer(modifier = Modifier.padding(bottom = 10.dp))
                                                  }
                                        }
                              }
                    }
          }

          @OptIn(ExperimentalMaterial3Api::class)
          @Composable
          fun DataEntryScreen(vm: MyScreensVM, onNavigateToOverviewScreen:()->Unit){
                    val vscroll = rememberScrollState()
                    Scaffold (
                              modifier = Modifier.background(Color.White),
                              containerColor = MaterialTheme.colorScheme.background,
                            //  contentColor = Color.White,
                              topBar = {
                                        TopAppBar(
                                                  title = {Text("Dispenser Calibrator", color = Color.White)},
                                                  expandedHeight = 10.dp,
                                                   modifier = Modifier.padding(bottom = 20.dp),
                                                 // navigationIcon = { Image(painterResource(R.drawable.back_arrow),  contentDescription = "back")},
                                                  colors = TopAppBarColors(containerColor = Color.DarkGray, Color.Blue, Color.Blue,Color.White,Color.Blue,Color.Blue ),
                                        )
                              },
                              bottomBar = {
                                       /* Button(
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
                                                  modifier = Modifier.padding(start = 150.dp, top = 50.dp)
                                        ) {
                                                  Text("Go to Overview Screen")  // Pressing this button finest saves the data to Room db before navigating to the Overview Screen
                                        }*/
                                        BottomAppBar(
                                                //  windowInsets = WindowInsets.systemBars,
                                               //   modifier = Modifier.height(50.dp),
                                               //   contentPadding = PaddingValues(50.dp),
                                                  actions = {
                                                            Row (verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Start, modifier = Modifier.height(40.dp)) {
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
                                                                              //  modifier = Modifier.padding(start = 150.dp, top = 50.dp)
                                                                      ) {
                                                                                Text("Go to Overview Screen")  // Pressing this button finest saves the data to Room db before navigating to the Overview Screen
                                                                      }
                                                            }
                                                  }
                                        )
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
                              val tempOptions = mutableListOf("Cool", "Warm")
                              val fullOptions = mutableListOf("Yes", "No")
                              val sideOptions = mutableListOf("1", "2")
                              val stationOptions = mutableListOf("SITE", "SWEDRU", "MG", "MILLILORD", "NKROANZA", "JUKWA", "OBAAPA", "MAMPONG")
                              val litresFilledOptions = mutableListOf("6.25", "12.5", "25")

                              val sideSpacing = 30.dp
                              Column(modifier = Modifier
                                        .verticalScroll(vscroll)
                                        .padding(innerPadding)) {
                                        Text("Load Density",  modifier = Modifier.padding(start = sideSpacing),)
                                        OutlinedTextField(
                                                  value = vm.changeLoadDensity,
                                                  onValueChange = { vm.change_Load_Density(it)},
                                                  modifier = Modifier.padding(start = sideSpacing),
                                                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Litres Filled",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdownMenu(vm, litresFilledOptions, "Litres Filled").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Tolerance",  modifier = Modifier.padding(start = sideSpacing),)
                                        OutlinedTextField(
                                                  value = vm.changeTolerance,
                                                  onValueChange = { vm.change_Tolerance(it)},
                                                  modifier = Modifier.padding(start = sideSpacing),
                                                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                  placeholder = { }
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Cylinder ID",  modifier = Modifier.padding(start = sideSpacing),)
                                        OutlinedTextField(
                                                  value = vm.changeCylinderID,
                                                  onValueChange = { vm.changeCylinderID(it)},
                                                  modifier = Modifier.padding(start = sideSpacing),
                                                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Empty",  modifier = Modifier.padding(start = sideSpacing),)
                                        OutlinedTextField(
                                                  value = vm.changeEmpty,
                                                  onValueChange = { vm.change_Empty(it)},
                                                  modifier = Modifier.padding(start = sideSpacing),
                                                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Final",  modifier = Modifier.padding(start = sideSpacing),)
                                        OutlinedTextField(
                                                  value = vm.changeFinal,
                                                  onValueChange = { vm.change_Final(it)},
                                                  modifier = Modifier.padding(start = sideSpacing),
                                                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Full",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdownMenu(vm, fullOptions, "Full").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Side",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdownMenu(vm, sideOptions, "Side").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Temperature",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdownMenu(vm, tempOptions, "Temperature").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Model",  modifier = Modifier.padding(start = sideSpacing),)
                                        OutlinedTextField(
                                                  value = vm.changeDispenserModel,
                                                  modifier = Modifier.padding(start = sideSpacing),
                                                  onValueChange = { vm.change_Dispenser_Model(it)}
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Disp. SN",  modifier = Modifier.padding(start = sideSpacing),)
                                        OutlinedTextField(
                                                  value = vm.changeDispenserSN,
                                                  modifier = Modifier.padding(start = sideSpacing),
                                                  onValueChange = { vm.change_Dispenser_SN(it)},
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Station",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdownMenu(vm, stationOptions, "Station").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 360.dp))
                              }
                    }
          }



          @SuppressLint("ResourceType")
          @Composable
          fun EachCard(cal: EachCardState, vm: MyScreensVM, context: Context){
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
                                        Text("Empty(kg):")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Text(  cal.Empty )
                              }
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("Final(kg):")
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
                              Row(modifier = Modifier.padding(start = 5.dp)) {
                                        Text("State:")
                                        Spacer(modifier = Modifier.padding(10.dp))
                                        if (screenActions(vm).calculateDM() in 1.2..1.35) {
                                                  Image(
                                                            painter = painterResource(id = R.drawable.checkmark),
                                                            contentDescription = "Description of the image"
                                                  )
                                        }else{
                                                  Image(
                                                            painter = painterResource(id = R.drawable.delete_icon),
                                                            contentDescription = "Description of the image"
                                                  )
                                        }
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
                   val compute = if ( vm.changeLoadDensity.isEmpty() ||  vm.changeLitresFilled.isEmpty()){
                           //   TODO(implement a TOAST here alerting the user that the field is empty)
                             return 0.0
                    }else{
                              val LoadDensityAsDouble:Double =  vm.changeLoadDensity.toDouble()
                              val LitresAsDouble: Double = vm.changeLitresFilled.toDouble()
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


