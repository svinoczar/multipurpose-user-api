package io.svinoczar.api.entity;

import org.springframework.data.annotation.Id;

public enum RewardReason {
    // REWARDS:
    ACTIVITY,
    VISIT,
    BUG_REPORT,
    DONATION,

    // FINES:
    INACTIVITY,
    BUG_ABUSE,
    MULTI_ACC,

}
