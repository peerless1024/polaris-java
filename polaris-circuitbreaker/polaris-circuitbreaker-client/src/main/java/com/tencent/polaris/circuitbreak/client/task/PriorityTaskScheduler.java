/*
 * Tencent is pleased to support the open source community by making polaris-java available.
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.polaris.circuitbreak.client.task;

import com.tencent.polaris.api.utils.ThreadPoolUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优先级队列调度器
 */
public class PriorityTaskScheduler {

    private final ExecutorService priorityJobScheduler;

    public PriorityTaskScheduler() {
        priorityJobScheduler = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>());
    }

    public void addCircuitBreakTask(InstancesCircuitBreakTask task) {
        priorityJobScheduler.execute(task);
    }

    public void destroy() {
        ThreadPoolUtils.waitAndStopThreadPools(new ExecutorService[]{priorityJobScheduler});
    }

}
