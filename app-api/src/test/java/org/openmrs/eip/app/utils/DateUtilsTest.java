package org.openmrs.eip.app.utils;

import java.time.LocalDateTime;

public class DateUtilsTest {

    public static LocalDateTime generateDateWithPrecision() {

        return LocalDateTime.now().withNano(0);
    }

}
