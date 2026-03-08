package com.example.dispensercalibrator.Frontend.UI.Screens_1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dispensercalibrator.Backend.Room_2.EachCardState
import com.example.dispensercalibrator.MainActivity
import com.example.dispensercalibrator.R
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal
import java.math.RoundingMode

// TTDs
// 1. Display the DM automatically -Done
// 2. The data requires internet to work therefore use a try a catch block there to catch any errors stemming form the lack of internet connectivity
// 3. Create a FAB button to clear all the fields an IconButtons to clear each field individually


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
                                        Surface(modifier = Modifier.fillMaxWidth()) {
                                                  HorizontalDivider()
                                                  Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center,
                                                            modifier = Modifier.height(70.dp).background(Color.Transparent)
                                                  ){
                                                            Button(
                                                                      modifier = Modifier.padding(start = 10.dp ),
                                                                      onClick = { onNavigateToDataEntryScreen()
                                                                      //  activity.auth(activity)
                                                            }) {
                                                                      Text("Go Back")
                                                            }

                                                            Spacer(modifier = Modifier.padding(start = 10.dp ))
                                                            Button({
                                                                      try {
                                                                                vm.ShareToWhats(context)
                                                                      } catch (ex: android.content.ActivityNotFoundException) {
                                                                                println("Whatsapp not installed")
                                                                                //  TODO()
                                                                      }
                                                            }) {
                                                                      Text("SHARE")
                                                            }
                                                  }
                                        }
                              }
                    ) { innerPadding ->

                              lateinit var rememberedItemsHolder: EachCardState

                              runBlocking {
                                        println("THE INITIALIZATION HAS BEGUN")
                                        rememberedItemsHolder =  vm.dao.retrieveCalibrationResult()
                              }

                              Column(modifier = Modifier.padding(innerPadding)) {
                                        EachOverviewCard(rememberedItemsHolder, vm)
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))
                              }
                    }
          }

          @OptIn(ExperimentalMaterial3Api::class)
          @Composable
          fun DataEntryScreen(vm: MyScreensVM, onNavigateToOverviewScreen:()->Unit){
                    val vscroll = rememberScrollState()
                    Scaffold (
                              /*floatingActionButton = {
                                        FloatingActionButton(
                                                  onClick = {

                                                  }
                                        ) { }
                              },*/
                              topBar = {
                                        TopAppBar(
                                                  title = {Text("Dispenser Calibrator", color = Color.Black, modifier = Modifier.padding(bottom = 8.dp))},
                                                  expandedHeight = 10.dp,
                                                   modifier = Modifier.padding(bottom = 15.dp),
                                                 // navigationIcon = { Image(painterResource(R.drawable.back_arrow),  contentDescription = "back")},
                                               //   colors = TopAppBarColors(containerColor = Color.Gray, Color.Blue, Color.Blue,Color.White,Color.Blue,Color.Blue ),
                                        )
                              },
                              bottomBar = {
                                        Surface(modifier = Modifier.fillMaxWidth()) {
                                                  HorizontalDivider()
                                                  Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center,
                                                            modifier = Modifier.height(70.dp).background(Color.Transparent)
                                                  ) {
                                                            Button(
                                                                      onClick = {
                                                                                // The following methods automatically calculate these calibration values
                                                                                vm.change_Expected_Litres()
                                                                                vm.change_Difference()
                                                                                vm.change_DM()

                                                                                // save data to Room DB
                                                                                vm.setRoomData()
                                                                                vm.pushToRoomDB()

                                                                                println("the value of VisualReport is ${vm.changeVisualResult}")
                                                                                // Then Navigate to OverviewScreen
                                                                                onNavigateToOverviewScreen()
                                                                      },
                                                                      modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp, bottom = 5.dp, top = 5.dp)
                                                            ) {
                                                                      Text("Go to Overview Screen")  // Pressing this button finest saves the data to Room db before navigating to the Overview Screen
                                                            }
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
                              val tempOptions = mutableListOf("Cool", "Warm")
                              val fullOptions = mutableListOf("Yes", "No")
                              val sideOptions = mutableListOf("1", "2")
                              val stationOptions = mutableListOf("SITE", "SWEDRU", "MG", "MILLILORD", "NKROANZA", "JUKWA", "OBAAPA", "MAMPONG")
                              val litresFilledOptions = mutableListOf("6.25", "12.5", "25")

                              val sideSpacing = 30.dp
                              Column(modifier = Modifier
                                        .verticalScroll(vscroll)
                                        .padding(innerPadding)
                                        .background(Color.Transparent)
                              ) {
                                        HorizontalDivider(modifier = Modifier.padding(bottom = 10.dp))
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Litres Filled(Lts)",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdown_1a(vm, litresFilledOptions, "LitresFilled").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Tolerance",  modifier = Modifier.padding(start = sideSpacing))
                                        Row {
                                                  OutlinedTextField(
                                                            value = vm.changeTolerance,
                                                            onValueChange = { vm.change_Tolerance(it)},
                                                            modifier = Modifier.padding(start = sideSpacing),
                                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                            placeholder = { }
                                                  )
                                                  Spacer(modifier = Modifier.padding(start = 10.dp))
                                                  IconButton(
                                                            onClick = {
                                                                      vm.changeTolerance = ""
                                                            }
                                                  ) {
                                                            Icon(painterResource(R.drawable.icons8_clear_symbol_96), "clear")
                                                  }
                                        }
                                        Spacer(modifier = Modifier.padding(start = 10.dp))

                                        Text("Cylinder ID",  modifier = Modifier.padding(start = sideSpacing))
                                        Row {
                                                  OutlinedTextField(
                                                            value = vm.changeCylinderID,
                                                            onValueChange = { vm.changeCylinderID(it)},
                                                            modifier = Modifier.padding(start = sideSpacing),
                                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                  )
                                                  Spacer(modifier = Modifier.padding(start = 10.dp))
                                                  IconButton(
                                                            onClick = {
                                                                      vm.changeCylinderID = ""
                                                            }
                                                  ) {
                                                            Icon(painterResource(R.drawable.icons8_clear_symbol_96), "clear")
                                                  }
                                        }
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Full",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdown_1a(vm, fullOptions, "Full").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Side",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdown_1a(vm, sideOptions, "Side").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Empty(kg)",  modifier = Modifier.padding(start = sideSpacing))
                                        Row {
                                                  OutlinedTextField(
                                                            value = vm.changeEmpty,
                                                            onValueChange = { vm.change_Empty(it)},
                                                            modifier = Modifier.padding(start = sideSpacing),
                                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                  )
                                                  Spacer(modifier = Modifier.padding(start = 10.dp))
                                                  IconButton(
                                                            onClick = {
                                                                      vm.changeEmpty = ""
                                                            }
                                                  ) {
                                                            Icon(painterResource(R.drawable.icons8_clear_symbol_96), "clear")
                                                  }
                                        }
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Final(kg)",  modifier = Modifier.padding(start = sideSpacing))
                                        Row {
                                                  OutlinedTextField(
                                                            value = vm.changeFinal,
                                                            onValueChange = { vm.change_Final(it)},
                                                            modifier = Modifier.padding(start = sideSpacing),
                                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                  )
                                                  Spacer(modifier = Modifier.padding(start = 10.dp))
                                                  IconButton(
                                                            onClick = {
                                                                      vm.changeFinal = ""
                                                            }
                                                  ) {
                                                            Icon(painterResource(R.drawable.icons8_clear_symbol_96), "clear")
                                                  }
                                        }
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        HorizontalDivider(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Temperature",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdown_1a(vm, tempOptions, "Temperature").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Load Density",  modifier = Modifier.padding(start = sideSpacing))
                                        Row {
                                                  OutlinedTextField(
                                                            value = vm.changeLoadDensity,
                                                            onValueChange = { vm.change_Load_Density(it)},
                                                            modifier = Modifier.padding(start = sideSpacing),
                                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                  )
                                                  Spacer(modifier = Modifier.padding(start = 10.dp))
                                                  IconButton(
                                                            onClick = {
                                                                      vm.changeLoadDensity = ""
                                                            }
                                                  ) {
                                                            Icon(painterResource(R.drawable.icons8_clear_symbol_96), "clear")
                                                  }
                                        }

                                        Text("Model",  modifier = Modifier.padding(start = sideSpacing))
                                        Row {
                                                  OutlinedTextField(
                                                            value = vm.changeDispenserModel,
                                                            modifier = Modifier.padding(start = sideSpacing),
                                                            onValueChange = { vm.change_Dispenser_Model(it)}
                                                  )
                                                  Spacer(modifier = Modifier.padding(start = 10.dp))
                                                  IconButton(
                                                            onClick = {
                                                                      vm.changeDispenserModel = ""
                                                            }
                                                  ) {
                                                            Icon(painterResource(R.drawable.icons8_clear_symbol_96), "clear")
                                                  }
                                        }
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Disp. SN",  modifier = Modifier.padding(start = sideSpacing))
                                        Row {
                                                  OutlinedTextField(
                                                            value = vm.changeDispenserSN,
                                                            modifier = Modifier.padding(start = sideSpacing),
                                                            onValueChange = { vm.change_Dispenser_SN(it)},
                                                  )
                                                  Spacer(modifier = Modifier.padding(start = 10.dp))
                                                  IconButton(
                                                            onClick = {
                                                                      vm.changeDispenserSN = ""
                                                            }
                                                  ) {
                                                            Icon(painterResource(R.drawable.icons8_clear_symbol_96), "clear")
                                                  }
                                        }
                                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                        Text("Station",  modifier = Modifier.padding(start = sideSpacing),)
                                        MyCustomGenericDropdown_1a(vm, stationOptions, "Station").MainMenu()
                                        Spacer(modifier = Modifier.padding(bottom = 360.dp))
                              }
                    }
          }



          @SuppressLint("ResourceType")
          @Composable
          fun EachOverviewCard(cal: EachCardState, vm: MyScreensVM){
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
                                        Text("Visual Result:")
                                        Spacer(modifier = Modifier.padding(5.dp))
                                        if (DependenciesForOverviewScreen(vm).calculateDM() in 1.2..1.35) {
                                                  Row {
                                                            Image(
                                                                      painter = painterResource(id = R.drawable.checkmark),
                                                                      contentDescription = "Description of the image"
                                                            )
                                                            Spacer(modifier = Modifier.padding(start = 5.dp))
                                                            Text("Must be between 1.2...1.35")
                                                  }
                                        }else{
                                                  Row {
                                                            Image(
                                                                      painter = painterResource(id = R.drawable.delete_icon),
                                                                      contentDescription = "Description of the image"
                                                            )
                                                            Spacer(modifier = Modifier.padding(start = 5.dp))
                                                            Text("Must be between 1.2...1.35")
                                                  }
                                        }
                              }
                    }
          }


class DependenciesForOverviewScreen(val vm: MyScreensVM){

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

          fun setVisualResult() {
                    if (calculateDM() in 1.2..1.35){
                              vm.changeVisualResult = "✔"
                    }else{
                              vm.changeVisualResult = "❌"
                    }
          }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun MyHost(
          controller: NavHostController,
          vm: MyScreensVM,
          activity: MainActivity,
          context: Context,
) {
          NavHost(controller, startDestination = "dataEntryScreen") {

                    composable("dataEntryScreen") {
                                        DataEntryScreen(vm, onNavigateToOverviewScreen = {
                                                  controller.navigate("overviewScreen")
                                        })
                    }

                    composable("overviewScreen") {
                              OverviewScreen(vm, context, onNavigateToDataEntryScreen = {
                                        controller.navigate("dataEntryScreen")
                              }, activity)
                    }
          }
}


