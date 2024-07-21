package io.svinoczar.api.entity;

import org.springframework.data.annotation.Id;

public enum RewardReason {
    // REWARDS:
    TEST_REWARD,
    TIMEZONE_TEST_REWARD,
    ACTIVITY,
    VISIT,
    QUEST,
    TASK,
    BUG_REPORT,
    BUG_REPORT_CONFIRMED,
    DONATION,
    OTHER,

    // FINES:
    TEST_FINE,
    INACTIVITY,
    BUG_ABUSE,
    MULTI_ACC,
    OTHER_FINE

    //NOTE: TOTAL COUNT = 15
}
