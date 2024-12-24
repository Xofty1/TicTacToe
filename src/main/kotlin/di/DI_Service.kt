package di

import datasource.repository.*
import org.koin.dsl.module

val gameModule = module {
    single { GameStorage() }
    single { GameRepository(get()) }
    single { UserStorage() }
    single { UserRepository(get()) }
    single { TicTacToeService(get(), get()) }
}