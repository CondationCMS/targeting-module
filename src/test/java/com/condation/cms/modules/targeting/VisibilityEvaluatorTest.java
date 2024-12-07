package com.condation.cms.modules.targeting;

/*-
 * #%L
 * targeting-module
 * %%
 * Copyright (C) 2024 CondationCMS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class VisibilityEvaluatorTest {

	private LocalDate date () {
		return LocalDate.now();
	}
	private LocalTime time () {
		return LocalTime.now();
	}
	
    @Test
    public void testVisibleWithinDateRange() {
		
		
        Map<String, Object> params = new HashMap<>();
        params.put("from", date().minusDays(1).format(DateTimeFormatter.ISO_DATE));
        params.put("to", date().plusDays(1).format(DateTimeFormatter.ISO_DATE));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should be visible within the date range")
                .isTrue();
    }

    @Test
    public void testNotVisibleBeforeDate() {
        Map<String, Object> params = new HashMap<>();
        params.put("from", date().plusDays(1).format(DateTimeFormatter.ISO_DATE));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should not be visible before the 'from' date")
                .isFalse();
    }

    @Test
    public void testNotVisibleAfterDate() {
        Map<String, Object> params = new HashMap<>();
        params.put("to", date().minusDays(1).format(DateTimeFormatter.ISO_DATE));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should not be visible after the 'to' date")
                .isFalse();
    }

    @Test
    public void testVisibleWithinTimeRange() {
		
        Map<String, Object> params = new HashMap<>();
        params.put("time_from", time().minusHours(1).format(DateTimeFormatter.ISO_LOCAL_TIME));
        params.put("time_to", time().plusHours(1).format(DateTimeFormatter.ISO_LOCAL_TIME));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should be visible within the time range")
                .isTrue();
    }

    @Test
    public void testNotVisibleBeforeTime() {
        Map<String, Object> params = new HashMap<>();
        params.put("time_from", time().plusHours(1).format(DateTimeFormatter.ISO_LOCAL_TIME));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should not be visible before the 'time_from'")
                .isFalse();
    }

    @Test
    public void testNotVisibleAfterTime() {
        Map<String, Object> params = new HashMap<>();
        params.put("time_to", time().minusHours(1).format(DateTimeFormatter.ISO_LOCAL_TIME));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should not be visible after the 'time_to'")
                .isFalse();
    }

    @Test
    public void testVisibleForSpecificMonths() {
        Map<String, Object> params = new HashMap<>();
        params.put("month", "1," + date().getMonthValue());

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should be visible for December and January")
                .isTrue();
    }

    @Test
    public void testNotVisibleForOtherMonths() {
        Map<String, Object> params = new HashMap<>();
        params.put("month", date().minusMonths(1).getMonthValue());

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should not be visible for months other than November")
                .isFalse();
    }

    @Test
    public void testVisibleOnSpecificDate() {
        Map<String, Object> params = new HashMap<>();
        params.put("date", date().format(DateTimeFormatter.ISO_DATE));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should be visible on the specific date")
                .isTrue();
    }

    @Test
    public void testNotVisibleOnOtherDates() {
        Map<String, Object> params = new HashMap<>();
        params.put("date", date().plusDays(1).format(DateTimeFormatter.ISO_DATE));

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should not be visible on other dates")
                .isFalse();
    }

    @Test
    public void testVisibleForSpecificDays() {
        Map<String, Object> params = new HashMap<>();
        params.put("day", date().getDayOfWeek().name());

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should be visible for specified days")
                .isTrue();
    }

    @Test
    public void testNotVisibleOnOtherDays() {
        Map<String, Object> params = new HashMap<>();
        params.put("day", date().minusDays(1).getDayOfWeek().name());

        assertThat(VisibilityEvaluator.isVisible(params))
                .as("Should not be visible on other days")
                .isFalse();
    }
}

