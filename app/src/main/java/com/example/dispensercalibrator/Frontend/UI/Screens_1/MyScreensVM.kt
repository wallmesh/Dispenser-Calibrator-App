package com.example.dispensercalibrator.Frontend.UI.Screens_1

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispensercalibrator.Backend.Room_2.CalibrationTest
import com.example.dispensercalibrator.Backend.Room_2.MainDAO
import com.example.dispensercalibrator.Backend.Whatsapp_3.ShareToWhatsapp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.collections.getValue
import io.ktor.util.collections.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//TTDS
// 1.

//  WE ARE FOLLOWING THE SINGLETON PATTERN HERE USING DEPENDENCY INJECTION. SO A SINGLE INSTANCE OF THE CalibrationTest() CLASS HERE IS BOTH
//  WRITTEN TO AND READ FROM EVERYWHERE THIS VIEWMODEL IS CALLED
@HiltViewModel
class MyScreensVM @Inject constructor (val dao: MainDAO, var mydata: CalibrationTest): ViewModel() {

    var fillingCounter by mutableStateOf("")

          //   Write to csv file
      /*    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
           suspend fun writeToCSV(service: Drive, accessToken: String) {
                    lateinit var dataholder: EachCardState
                    viewModelScope.launch(Dispatchers.IO) {
                                        dataholder = dao.retrieveCalibrationResult()
                                        val ktor = KtorMain().KtorInstance(body = dataholder, accessToken = accessToken, service = service)
                                        Log.d("chkOutput", "THE OUTPUT STREAM VALUE IS:${ktor}")
                    }
          }*/

          //  Share to Whatsapp
           fun ShareToWhats(context: Context) {
                     val scope = this
                    viewModelScope.launch(Dispatchers.IO) {
                              val whatsapp = ShareToWhatsapp(scope, context).trigger()
                              context.startActivity(whatsapp)
                              Log.d("chkOutput2", "DATA SUCCESSFULLY SHARED TO WHATSAPP")
                    }
          }

          //  STATE HOLDER FOR UI DATA
          var changeCylinderID: String by mutableStateOf("")
          var changeEmpty by mutableStateOf("")
          var changeFinal by mutableStateOf("")
          var changeDifference by mutableDoubleStateOf(0.0)
          var changeTolerance by mutableStateOf("")
          var changeDM by mutableDoubleStateOf(0.0)
          var changeLitresFilled by mutableStateOf("")
          var changeExpectedLitres by mutableDoubleStateOf(0.0)
          var changeSide by mutableStateOf("")
          var changeDispenserModel by mutableStateOf("")
          var changeLoadDensity by mutableStateOf("")
          var changeTemperature by mutableStateOf("")
          var changeDispenserSN by mutableStateOf("")
          var changeFull by mutableStateOf("")
          var changeStation by mutableStateOf("")
          var changeVisualResult by mutableStateOf("")


          //  SET THE VALUES FOR THE VARIABLES USED TO UPLOAD DATA TO THE ROOM DB
          fun setRoomData(){
                    mydata.cylinderId = changeCylinderID
                    mydata.Empty = changeEmpty
                    mydata.Final = changeFinal
                    mydata.Difference = changeDifference.toString()
                    mydata.Tolerance = changeTolerance
                    mydata.DM = changeDM.toString()
                    mydata.Litres = changeLitresFilled
                    mydata.ExpectedLitres = changeExpectedLitres.toString()
                    mydata.Side = changeSide
                    mydata.DispenserModel = changeDispenserModel
                    mydata.LoadDensity = changeLoadDensity
                    mydata.Temperature = changeTemperature
                    mydata.DispenserSN = changeDispenserSN
                    mydata.Full = changeFull
                    mydata.Station = changeStation
                    mydata.VisualResult = changeVisualResult
          }


          fun pushToRoomDB(){
                    val scope = CoroutineScope(Dispatchers.IO)
                    scope.launch {
                              dao.Insert_CalibrationDetails(mydata)
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
          fun change_Difference(){
                    val instance = DependenciesForOverviewScreen(this)
                    instance.calculateDifference()
                    instance.setDifferenceValue()
          }
          fun change_Tolerance(input: String){
                    changeTolerance = input
          }
          fun change_DM(){
                    val instance = DependenciesForOverviewScreen(this)
                    instance.calculateDM()
                    instance.setDM()
                    instance.setVisualResult()
          }
          fun change_Litres(input: String){
                    changeLitresFilled = input
          }
          fun change_Expected_Litres(){
                    val instance = DependenciesForOverviewScreen(this)
                    instance.calculateExpctdkg()
                    instance.setExpctdkg()
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