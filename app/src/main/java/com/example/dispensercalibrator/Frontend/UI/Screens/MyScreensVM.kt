package com.example.dispensercalibrator.Frontend.UI.Screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dispensercalibrator.Backend.Room.CalibrationTest
import com.example.dispensercalibrator.Backend.Room.MainDAO
import com.example.dispensercalibrator.Backend.Room.tester
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


//  WE ARE FOLLOWING THE SINGLETON PATTERN HERE USING DEPENDENCY INJECTION. SO A SINGLE INSTANCE OF THE CalibrationTest() CLASS HERE IS BOTH
//  WRITTEN TO AND READ FROM EVERYWHERE THIS VIEWMODEL IS CALLED
@HiltViewModel
class MyScreensVM @Inject constructor (val example: tester): ViewModel() {

          val mydata = CalibrationTest()

          //  STATE HOLDER FOR UI DATA
          var changeCylinderID: String by mutableStateOf("")
          var changeEmpty by mutableStateOf("")
          var changeFinal by mutableStateOf("")
          var changeDifference by mutableStateOf("")
          var changeTolerance by mutableStateOf("")
          var changeDM by mutableStateOf("")
          var changeLitres by mutableStateOf("")
          var changeExpectedLitres by mutableStateOf("")
          var changeSide by mutableStateOf("")
          var changeDispenserModel by mutableStateOf("")
          var changeLoadDensity by mutableStateOf("")
          var changeTemperature by mutableStateOf("")
          var changeDispenserSN by mutableStateOf("")
          var changeFull by mutableStateOf("")
          var changeStation by mutableStateOf("")


          //  SET THE VALUES FOR THE VARIABLES USED TO UPLOAD DATA TO THE ROOM DB
          fun setRoomData(){
                    mydata.cylinderId = changeCylinderID
                    mydata.Empty = changeEmpty
                    mydata.Final = changeFinal
                    mydata.Difference = changeDifference
                    mydata.Tolerance = changeTolerance
                    mydata.DM = changeDM
                    mydata.Litres = changeLitres
                    mydata.ExpectedLitres = changeExpectedLitres
                    mydata.Side = changeSide
                    mydata.DispenserModel = changeDispenserModel
                    mydata.LoadDensity = changeLoadDensity
                    mydata.Temperature = changeTemperature
                    mydata.DispenserSN = changeDispenserSN
                    mydata.Full = changeFull
                    mydata.Station = changeStation
          }


          fun pushToRoomDB(){
                    val scope = CoroutineScope(Dispatchers.IO)
                    scope.launch {
                          //    val me = dao.Insert_CalibrationDetails(mydata)
                    }
          }


          fun changeCylinderID(input: String){
                    changeCylinderID = input
                    println("CURRENT STATE IS: $changeCylinderID")
                    println("CURRENT INSTANCE IS: ${mydata.cylinderId} ")
          }
          fun change_Empty(input: String){
                    changeEmpty = input
          }
          fun change_Final(input: String){
                    changeFinal = input
          }
          fun change_Difference(input: String){
                    changeDifference = input
          }
          fun change_Tolerance(input: String){
                    changeTolerance = input
          }
          fun change_DM(input: String){
                    changeDM = input
          }
          fun change_Litres(input: String){
                    changeLitres = input
          }
          fun change_Expected_Litres(input: String){
                    changeExpectedLitres = input
          }
          fun change_Side(input: String){
                    changeSide = input
          }
          fun change_Dispenser_Model(input: String){
                    changeDispenserModel = input
          }
          fun change_Load_Density(input: String){
                    changeLoadDensity = input
          }
          fun change_Temperature(input: String){
                    changeTemperature = input
          }
          fun change_Dispenser_SN(input: String){
                    changeDispenserSN = input
          }
          fun change_Full(input: String){
                    changeFull = input
          }
          fun change_Station(input: String){
                    changeStation = input
          }
}