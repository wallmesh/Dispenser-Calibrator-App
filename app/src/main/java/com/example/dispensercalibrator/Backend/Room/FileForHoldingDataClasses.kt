
package com.example.dispensercalibrator.Backend.Room


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class tester @Inject constructor (var name: String){

}


/*@Singleton
@Entity
data class CalibrationTest @Inject constructor  (
                    @PrimaryKey(autoGenerate = true) @ColumnInfo ("ID") var id: Int = 0,
                    @ColumnInfo("date") var date: String = "",
                    @ColumnInfo("cylinder_id") var cylinderId: String = "",
                    @ColumnInfo("Empty") var Empty: String = "",
                    @ColumnInfo("Final") var Final: String = "" ,
                    @ColumnInfo("Difference") var Difference: String = "",
                    @ColumnInfo("Tolerance") var Tolerance: String = "",
                    @ColumnInfo("DM") var DM: String = "",
                    @ColumnInfo("Litres") var Litres: String = "",
                    @ColumnInfo("ExpectedLitres") var ExpectedLitres: String = "",
                    @ColumnInfo("Side") var Side: String = "",
                    @ColumnInfo("DispenserModel") var DispenserModel: String = "",
                    @ColumnInfo("LoadDensity") var LoadDensity: String = "",
                    @ColumnInfo("Temperature") var Temperature: String ="",
                    @ColumnInfo("DispenserSN") var DispenserSN: String ="",
                    @ColumnInfo("Full") var Full: String = "",
                    @ColumnInfo("Station") var Station: String = "",
                    )*/


@Singleton
@Entity
data class CalibrationTest @Inject constructor  (
          @PrimaryKey(autoGenerate = false) @ColumnInfo ("ID") var id: Int,
          @ColumnInfo("date") var date: String ,
          @ColumnInfo("cylinder_id") var cylinderId: String,
          @ColumnInfo("Empty") var Empty: String,
          @ColumnInfo("Final") var Final: String ,
          @ColumnInfo("Difference") var Difference: String,
          @ColumnInfo("Tolerance") var Tolerance: String ,
          @ColumnInfo("DM") var DM: String ,
          @ColumnInfo("Litres") var Litres: String ,
          @ColumnInfo("ExpectedLitres") var ExpectedLitres: String ,
          @ColumnInfo("Side") var Side: String ,
          @ColumnInfo("DispenserModel") var DispenserModel: String,
          @ColumnInfo("LoadDensity") var LoadDensity: String ,
          @ColumnInfo("Temperature") var Temperature: String,
          @ColumnInfo("DispenserSN") var DispenserSN: String,
          @ColumnInfo("Full") var Full: String,
          @ColumnInfo("Station") var Station: String,
)

// @Singleton
data class EachCardState @Inject constructor (
          @PrimaryKey(autoGenerate = true) @ColumnInfo ("ID") var id: Int,
          @ColumnInfo("date") var date: String ,
          @ColumnInfo("cylinder_id") var cylinderId: String,
          @ColumnInfo("Empty") var Empty: String,
          @ColumnInfo("Final") var Final: String ,
          @ColumnInfo("Difference") var Difference: String,
          @ColumnInfo("Tolerance") var Tolerance: String ,
          @ColumnInfo("DM") var DM: String ,
          @ColumnInfo("Litres") var Litres: String ,
          @ColumnInfo("ExpectedLitres") var ExpectedLitres: String ,
          @ColumnInfo("Side") var Side: String ,
          @ColumnInfo("DispenserModel") var DispenserModel: String,
          @ColumnInfo("LoadDensity") var LoadDensity: String ,
          @ColumnInfo("Temperature") var Temperature: String,
          @ColumnInfo("DispenserSN") var DispenserSN: String,
          @ColumnInfo("Full") var Full: String,
          @ColumnInfo("Station") var Station: String,
)







/*

@Entity
data class CylinderDetailsOnly (
                    @ColumnInfo("SN") @PrimaryKey(autoGenerate = true) var SN: Int = 0,
                    @ColumnInfo("Barcode") var Barcode: String?,
                    @ColumnInfo("Size")  var Size: String?,
                    @ColumnInfo("Cond") var Cond: String?,
                    @ColumnInfo("SelectedDropDownOption") var SelectedDropdownOption: String?,
)

@Entity
data class LastRowid(
                    @PrimaryKey(autoGenerate = false) var id: Int = 0,
                    @ColumnInfo("rowID") var RowID: Long = 0
    )


@Entity
data class CombinedDetails(
                    @PrimaryKey(autoGenerate = true) var id2: Int = 0,
                    @Embedded var dispatchDetails: DispatchDetailsOnly,
                    @Embedded var cylinderDetails: CylinderDetailsOnly
)
{
                  */
/*  var newtewstsclasslist = mutableListOf(EmbeddedCylinderDetails())
                    val somelist = mutableListOf(CylinderDetailsOnly()).forEach {
                                        newtewstsclasslist.add(EmbeddedCylinderDetails(dispatchDetails,  it))
                    }*//*

}



*/
