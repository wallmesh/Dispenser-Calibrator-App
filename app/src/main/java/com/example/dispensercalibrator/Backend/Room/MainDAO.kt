package com.example.dispensercalibrator.Backend.Room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MainDAO {


                    //?    INSERT METHODS
                    @Insert
                    suspend fun Insert_CalibrationDetails(calibrationTestDetails: CalibrationTest):Long


                   /* @Query("SELECT  date FROM CombinedDetails")
                    suspend fun getDate(): MutableList<String>*/


}



