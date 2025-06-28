package io.dodn.springboot.core.domain

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicBoolean

@Service
class AsyncService {

    private val logger = LoggerFactory.getLogger(javaClass)
    val isTaskCompleted = AtomicBoolean(false)

    @Async
    fun longRunningTask() {
        logger.info("비동기 작업을 시작합니다.")

        Thread.sleep(3000)
        isTaskCompleted.set(true)

        logger.info("비동기 작업이 성공적으로 완료되었습니다.")
    }
}