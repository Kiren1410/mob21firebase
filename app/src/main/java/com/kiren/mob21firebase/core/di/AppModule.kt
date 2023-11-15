package com.kiren.mob21firebase.core.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.kiren.mob21firebase.core.service.AuthService
import com.kiren.mob21firebase.core.service.StorageService
import com.kiren.mob21firebase.data.db.TodoDatabase
import com.kiren.mob21firebase.data.repo.TodosRepo
import com.kiren.mob21firebase.data.repo.TodosRepoFirestoreImpl
import com.kiren.mob21firebase.data.repo.UserRepo
import com.kiren.mob21firebase.data.repo.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuth(@ApplicationContext context: Context): AuthService {
        return AuthService()
    }

    @Provides
    @Singleton
    fun provideStorageService(): StorageService {
        return StorageService()
    }

//    @Provides
//    @Singleton
//    fun provideFirebaseRealtimeRef(): DatabaseReference {
//        return FirebaseDatabase.getInstance().getReference("todos")
//    }

    @Provides
    @Singleton
    fun provideFirebaseTodosCollection(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideTodosRepoFirestore(db: FirebaseFirestore): TodosRepo {
        return TodosRepoFirestoreImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepoFirestore(db: FirebaseFirestore): UserRepo {
        return UserRepoImpl(db.collection("users"))
    }


//    @Provides
//    @Singleton
//    fun provideRoomDb(@ApplicationContext context: Context): TodoDatabase {
//        return Room.databaseBuilder(
//            context,
//            TodoDatabase::class.java,
//            TodoDatabase.DB_NAME
//        ).fallbackToDestructiveMigration().build()
//    }

}











