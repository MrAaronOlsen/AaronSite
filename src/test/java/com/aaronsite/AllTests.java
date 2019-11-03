package com.aaronsite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages( {
    "com.aaronsite.database.operations"
} )

public class AllTests {
}
