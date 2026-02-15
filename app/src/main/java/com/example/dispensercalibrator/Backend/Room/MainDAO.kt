package com.example.dispensercalibrator.Backend.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MainDAO {

                    //?    INSERT METHODS
                    @Insert
                    suspend fun Insert_CalibrationDetails(calibrationTestDetails2: CalibrationTest):Long

                    @Query("SELECT * FROM CalibrationTest")
                    suspend fun retriveAll():List<EachCardState>


}



