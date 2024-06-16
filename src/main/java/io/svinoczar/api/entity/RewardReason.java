package io.svinoczar.api.entity;

import org.springframework.data.annotation.Id;

public enum RewardReason {
    // REWARDS:
    TEST_REWARD,
    TIMEZONE_TEST_REWARD,
    ACTIVITY,
    VISIT,
    BUG_REPORT,
    BUG_REPORT_CONFIRMED,
    DONATION,

    TEST_FINE,
    // FINES:
    INACTIVITY,
    BUG_ABUSE,
    MULTI_ACC,
}
