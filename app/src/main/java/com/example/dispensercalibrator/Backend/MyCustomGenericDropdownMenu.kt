package com.example.dispensercalibrator.Backend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.dispensercalibrator.Frontend.UI.Screens.MyScreensVM
import com.example.dispensercalibrator.R
import javax.inject.Inject

// TTDs
//1.        The number of DropdownMenuItems() that are created should be dependent on the number menu options there are. It should correspond so it does not show only two options every time even if there are more. -- done

class MyCustomGenericDropdownMenu @Inject constructor(val vm: MyScreensVM, val suppliedListOfMenuOptions: List<String>, var nameOfCalibrationDetail:String) {

          var clickedOption by mutableStateOf("Select option")
          @Composable
          fun MainMenu(){
                    var expanded by remember{mutableStateOf(false)}
                    var clickDetector by remember{mutableStateOf(false)}

                    @Composable
                    fun buttonBody(){
                              Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
                                        if (clickDetector){
                                                  Text(clickedOption, modifier = Modifier.width(120.dp), color = Color.Red)
                                        }else{
                                                  Text(nameOfCalibrationDetail, modifier = Modifier.width(120.dp), textDecoration = TextDecoration.Underline )
                                        }
                                        Spacer(modifier = Modifier.padding(end = 80.dp))
                                        Image(painterResource(R.drawable.down_lean_arrowhead), contentDescription = "down")
                              }
                    }
                    val sideSpacing = 30.dp
                    Box(modifier =Modifier.fillMaxSize()){
                              OutlinedButton(
                                        onClick = {
                                                  expanded =!expanded
                                        },
                                        shape = RoundedCornerShape(10),
                                        modifier = Modifier.width(280.dp).height(55.dp).padding(start = sideSpacing),
                                        content = { buttonBody() }
                              )
                              DropdownMenu(expanded, {expanded = false}, modifier = Modifier.width(280.dp)) {
                                        suppliedListOfMenuOptions.forEach {
                                                  DropdownMenuItem(
                                                            text = { Text(it) },
                                                            onClick = {
                                                                      clickDetector = true
                                                                      clickedOption = it   // For the Data Entry Screen
                                                                      fun optionSelector(){
                                                                                when(nameOfCalibrationDetail ){    // For the Overview SCREEN
                                                                                          "cylinderID" -> vm.changeCylinderID = it
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
                                                                      println("THE parameter VALUE HAS CHANGED TO $clickedOption")
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