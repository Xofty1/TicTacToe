package di

import datasource.repository.GameRepository
import datasource.repository.GameStorage
import datasource.repository.RepositoryService
import org.koin.dsl.module

val gameModule = module {
    single { GameStorage() }
    single { GameRepository(get()) }
    single { RepositoryService(get()) }
}