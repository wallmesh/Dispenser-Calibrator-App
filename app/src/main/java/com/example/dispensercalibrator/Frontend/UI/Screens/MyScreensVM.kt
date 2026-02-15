package com.example.dispensercalibrator.Frontend.UI.Screens

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispensercalibrator.Backend.Ktor.KtorMain
import com.example.dispensercalibrator.Backend.Room.CalibrationTest
import com.example.dispensercalibrator.Backend.Room.EachCardState
import com.example.dispensercalibrator.Backend.Room.MainDAO
import com.google.api.services.drive.Drive
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//TTDS
// 1.

//  WE ARE FOLLOWING THE SINGLETON PATTERN HERE USING DEPENDENCY INJECTION. SO A SINGLE INSTANCE OF THE CalibrationTest() CLASS HERE IS BOTH
//  WRITTEN TO AND READ FROM EVERYWHERE THIS VIEWMODEL IS CALLED
@HiltViewModel
class MyScreensVM @Inject constructor (val dao: MainDAO, var mydata: CalibrationTest, val mycontext: Context): ViewModel() {

          @RequiresApi(Build.VERSION_CODES.TIRAMISU)
           suspend fun writeToCSV(service: Drive, accessToken: String) {
                    lateinit var dataholder: List<EachCardState>
                    val me = viewModelScope.launch(Dispatchers.IO) {
                                        dataholder = dao.retriveAll()
                                        val ktor = KtorMain().KtorInstance(body = dataholder, accessToken = accessToken, service = service)
                                        Log.d("chkOutput", "THE OUTPUT STREAM VALUE IS:${ktor}")
                    }
          }


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
                              val me = dao.Insert_CalibrationDetails(mydata)
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