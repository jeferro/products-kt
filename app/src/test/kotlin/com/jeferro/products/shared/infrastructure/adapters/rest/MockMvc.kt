package com.jeferro.products.shared.infrastructure.adapters.rest

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch

fun ResultActions.asyncDispatch(mockMvc: MockMvc): ResultActions {
    val mvcResult = andReturn()

    val asyncRequest = asyncDispatch(mvcResult)
    return mockMvc.perform(asyncRequest)
}