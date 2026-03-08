package com.example.dispensercalibrator.Frontend.UI.Screens_1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.dispensercalibrator.R
import javax.inject.Inject

// TTDs
//1.        The number of DropdownMenuItems() that are created should be dependent on the number menu options there are. It should correspond so it does not show only two options every time even if there are more. -- done

class MyCustomGenericDropdown_1a @Inject constructor(val vm: MyScreensVM, val suppliedListOfMenuOptions: List<String>, var nameOfCalibrationDetail:String) {

          object HasBeenClickedSingleton{
                    var hasBeenClicked by mutableStateOf(false)
          }
          @Composable
          fun MainMenu(){
                    var expanded by remember { mutableStateOf(false) }


                    @Composable
                    fun buttonBody(){
                              Row(modifier = Modifier.Companion.fillMaxSize(), verticalAlignment = Alignment.Companion.CenterVertically) {
                                        if (HasBeenClickedSingleton.hasBeenClicked) {
                                                  when (nameOfCalibrationDetail) {    // For the Overview SCREEN
                                                            "CylinderID" -> Text(vm.changeCylinderID, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Empty" -> Text(vm.changeEmpty, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Final" -> Text(vm.changeFinal, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Difference" -> Text(vm.changeDifference.toString(), modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Tolerance" -> Text(vm.changeTolerance, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "DM" -> Text(vm.changeDM.toString(), modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "LitresFilled" -> Text(vm.changeLitresFilled, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "ExpectedLiters" -> Text(vm.changeExpectedLitres.toString(), modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Side" -> Text(vm.changeSide, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "DispenserModel" -> Text(vm.changeDispenserModel, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "LoadDensity" -> Text(vm.changeLoadDensity, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Temperature" -> Text(vm.changeTemperature, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "DispenserSN" -> Text(vm.changeDispenserSN, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Full" -> Text(vm.changeFull, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                            "Station" -> Text(vm.changeStation, modifier = Modifier.Companion.width(120.dp), color = Color.Companion.Red)
                                                  }
                                        } else {
                                                  Text(nameOfCalibrationDetail, modifier = Modifier.Companion.width(120.dp), textDecoration = TextDecoration.Companion.Underline)
                                        }
                                        Spacer(modifier = Modifier.Companion.padding(end = 50.dp))
                                        Image(painterResource(R.drawable.down_lean_arrowhead), contentDescription = "down")
                              }
                    }
                    val sideSpacing = 30.dp
                    Box(modifier = Modifier) {
                              OutlinedButton(
                                        onClick = {
                                                  expanded = !expanded
                                        },
                                        shape = RoundedCornerShape(10),
                                        modifier = Modifier.Companion.width(280.dp).height(55.dp).padding(start = sideSpacing),
                                        content = { buttonBody() }
                              )
                              DropdownMenu(expanded, { expanded = false }, offset = DpOffset(29.dp,0.dp), modifier = Modifier.width(250.dp)) {
                                        suppliedListOfMenuOptions.forEach {
                                                  DropdownMenuItem(
                                                            modifier = Modifier,
                                                            text = { Text(it) },
                                                            onClick = {
                                                                      HasBeenClickedSingleton.hasBeenClicked = true
                                                                      println("THE VALUE OF HAS BEEN CLICKED IS ${HasBeenClickedSingleton.hasBeenClicked}")
                                                                      fun optionSelector() {
                                                                                when (nameOfCalibrationDetail) {    // For the Overview SCREEN
                                                                                          "CylinderID" -> vm.changeCylinderID = it
                                                                                          "Empty" -> vm.changeEmpty = it
                                                                                          "Final" -> vm.changeFinal = it
                                                                                          "Difference" -> vm.changeDifference = it.toDouble()
                                                                                          "Tolerance" -> vm.changeTolerance = it
                                                                                          "DM" -> vm.changeDM = it.toDouble()
                                                                                          "LitresFilled" -> vm.changeLitresFilled = it
                                                                                          "ExpectedLiters" -> vm.changeExpectedLitres = it.toDouble()
                                                                                          "Side" -> vm.changeSide = it
                                                                                          "DispenserModel" -> vm.changeDispenserModel = it
                                                                                          "LoadDensity" -> vm.changeLoadDensity = it
                                                                                          "Temperature" -> vm.changeTemperature = it
                                                                                          "DispenserSN" -> vm.changeDispenserSN = it
                                                                                          "Full" -> vm.changeFull = it
                                                                                          "Station" -> vm.changeStation = it
                                                                                }
                                                                      }
                                                                      optionSelector()
                                                                      println("THE VALUE IS $it")
                                                                      //   println("THE parameter VALUE HAS CHANGED TO ${holder.clickedOption}")
                                                                      println("THE TEMP VALUE IN THE VM IS ${vm.changeSide}")
                                                                      expanded = false
                                                            }
                                                  )
                                                  HorizontalDivider()
                                        }
                              }
                    }
          }
}