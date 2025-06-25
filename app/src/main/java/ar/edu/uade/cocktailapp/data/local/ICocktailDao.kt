package ar.edu.uade.cocktailapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ICocktailDao {

    @Query("SELECT*FROM cocktail")
    suspend fun getAll(): List<CocktailLocal>

    @Query("SELECT * FROM cocktail WHERE idDrink = :id LIMIT 1")
    suspend fun findById(id: Int): CocktailLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg cocktail: CocktailLocal)

    @Delete
    suspend fun delete(cocktail: CocktailLocal)




}