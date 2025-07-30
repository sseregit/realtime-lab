package io.github.sseregit.realtimelab.longpolling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class LongPollingController {

    private final Map<String, DeferredResult<String>> waitingUsers = new ConcurrentHashMap<>();

    @GetMapping("/longpolling")
    DeferredResult<String> longPolling(@RequestParam String userId) {
        log.info("클라이언트 [{}] 대기 시작", userId);
        DeferredResult<String> result = new DeferredResult<>(5000L, "no message");

        result.onCompletion(() -> {
            log.info("클라이언트 [{}] 응답 완료 또는 타임아웃", userId);
            waitingUsers.remove(userId);
        });

        waitingUsers.put(userId, result);
        return result;
    }
}
