package com.jeferro.products.domain.shared.services.logger

import com.jeferro.products.domain.shared.utils.logger.Logger

class FakeLogger : Logger {
    override fun debug(message: String) {
        // Do nothing
    }

    override fun info(message: String) {
        // Do nothing
    }

    override fun warn(message: String) {
        // Do nothing
    }

    override fun warn(message: String, cause: Exception?) {
        // Do nothing
    }

    override fun error(message: String) {
        // Do nothing
    }

    override fun error(message: String, cause: Exception?) {
        // Do nothing
    }
}