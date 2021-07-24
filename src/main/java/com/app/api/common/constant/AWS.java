package com.app.api.common.constant;

import software.amazon.awssdk.regions.Region;

public class AWS {
    public static final Region REGION = Region.US_EAST_1;
    public static final String SPARE_PART = "auto-trader-spare";
    public static final String VEHICLE = "auto-trader-vehicle";
    public static final String SERVICE = "auto-trader-service-test";

    private AWS() {}
}
