package com.example.tripon

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",")
    }
}


@Entity(tableName = "guiders")
data class Guider(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val location: String,
    val contact: String
)

@Entity(tableName = "travellers")
data class Traveller(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val location: String,
    val image: List<String>
)

@Entity(tableName = "coupons")
data class Coupon(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val code: String,
    val description: String,
    val percentage: Int
)

@Dao
interface GuiderDao {
    @Insert
    fun insertGuider(guider: Guider)

    @Query("SELECT * FROM guiders")
    fun getAllGuiders(): Flow<List<Guider>>
}

@Dao
interface TravellerDao {
    @Insert
    fun insert(traveller: Traveller)

    @Query("SELECT * FROM travellers")
    fun getAllTravellers(): Flow<List<Traveller>>
}

@Dao
interface CouponDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(coupon: Coupon)

    @Query("SELECT * FROM coupons ORDER BY id DESC")
    fun getAllCoupons(): Flow<List<Coupon>>
}


@Database(entities = [Guider::class, Traveller::class, Coupon::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun travellerDao(): TravellerDao
    abstract fun guiderDao(): GuiderDao
    abstract fun couponDao(): CouponDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trip_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


