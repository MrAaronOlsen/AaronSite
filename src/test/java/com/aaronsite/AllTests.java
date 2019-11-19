package com.aaronsite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages( {
    "com.aaronsite.database.metadata",
    "com.aaronsite.database.operations",
    "com.aaronsite.database.statements"
} )

public class AllTests {
}
