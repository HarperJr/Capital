package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.database.entity.AccountEntityType
import com.harper.capital.database.entity.AccountTable
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.GoalTable
import com.harper.capital.database.entity.LoanEntity
import com.harper.capital.database.entity.LoanTable
import com.harper.capital.database.entity.embedded.AssetEntityEmbedded
import com.harper.capital.database.view.AssetBalanceTable
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert
    suspend fun insert(entity: AccountEntity): Long

    @Insert
    suspend fun insertLoan(entity: LoanEntity)

    @Insert
    suspend fun insertGoal(entity: GoalEntity)

    @Update
    suspend fun update(entity: AccountEntity)

    @Transaction
    @Query("SELECT * FROM ${AssetBalanceTable.tableName} WHERE ${AccountTable.id} = :accountId")
    suspend fun selectById(accountId: Long): AssetEntityEmbedded

    @Query("SELECT * FROM ${LoanTable.tableName} WHERE ${LoanTable.accountId} = :accountId")
    suspend fun selectLoanByAccountId(accountId: Long): LoanEntity

    @Query("SELECT * FROM ${GoalTable.tableName} WHERE ${GoalTable.accountId} = :accountId")
    suspend fun selectGoalByAccountId(accountId: Long): GoalEntity

    @Transaction
    @Query("SELECT * FROM ${AssetBalanceTable.tableName}")
    fun selectAll(): Flow<List<AssetEntityEmbedded>>

    @Transaction
    @Query("SELECT * FROM ${AssetBalanceTable.tableName} WHERE ${AccountTable.type} = :type")
    fun selectByType(type: AccountEntityType): Flow<List<AssetEntityEmbedded>>
}
