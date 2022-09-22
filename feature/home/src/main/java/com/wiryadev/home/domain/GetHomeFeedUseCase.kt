package com.wiryadev.home.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.home.data.repository.HomeRepository
import com.wiryadev.home.mapper.SectionMapper
import com.wiryadev.home.presentation.viewparam.homeitem.HomeUiItem
import com.wiryadev.shared.data.model.mapper.MovieMapper
import com.wiryadev.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHomeFeedUseCase(
    private val repository: HomeRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Nothing, List<HomeUiItem>>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<List<HomeUiItem>>> {
        return flow {
            emit(ViewResource.Loading())

            repository.fetchHomeFeeds().collect { dataResource ->
                dataResource.suspendSubscribe(
                    doOnSuccess = { result ->
                        val data = mutableListOf<HomeUiItem>()
                        result.payload?.data?.let { response ->
                            response.header?.let { header ->
                                data.add(HomeUiItem.HeaderSectionItem(MovieMapper.toViewParam(header)))
                            }
                            response.sections?.forEach { section ->
                                data.add(HomeUiItem.MovieSectionItem(SectionMapper.toViewParam(section)))
                            }
                        }
                        emit(ViewResource.Success(data))
                    },
                    doOnError = { error ->
                        emit(ViewResource.Error(error.exception))
                    }
                )
            }
        }
    }

}