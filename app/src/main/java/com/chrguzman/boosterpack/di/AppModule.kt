package com.chrguzman.boosterpack.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.chrguzman.boosterpack.networking.MtgApi
import com.chrguzman.boosterpack.repo.CardRepository
import com.chrguzman.boosterpack.repo.CardRepositoryInterface
import com.chrguzman.boosterpack.roomdb.CardDao
import com.chrguzman.boosterpack.roomdb.CardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        CardDatabase::class.java,
        "CardDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: CardDatabase) = database.cardDao()

    @Singleton
    @Provides
    fun injectRetrofitApi(): MtgApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.magicthegathering.io/")
            .build()
            .create(MtgApi::class.java)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context)

    @Singleton
    @Provides
    fun injectNormalRepo(dao: CardDao, api: MtgApi) =
        CardRepository(dao, api) as CardRepositoryInterface
}