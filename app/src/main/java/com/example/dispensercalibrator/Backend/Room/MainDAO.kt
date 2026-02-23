package com.example.dispensercalibrator.Backend.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MainDAO {

                    //?    INSERT METHODS
                    @Upsert
                    suspend fun Insert_CalibrationDetails(calibrationTestDetails2: CalibrationTest):Long

                    @Query("SELECT * FROM CalibrationTest")
                    suspend fun retrieveAll(): List<EachCardState>


}



