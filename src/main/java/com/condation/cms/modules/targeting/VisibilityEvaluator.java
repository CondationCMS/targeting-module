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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class VisibilityEvaluator {

    public static boolean isVisible(Map<String, Object> params) {
        LocalDateTime now = LocalDateTime.now();

        // Prüfe das Datum "from" und "to"
        if (params.containsKey("from")) {
            LocalDate fromDate = LocalDate.parse(params.get("from").toString(), DateTimeFormatter.ISO_DATE);
            if (now.toLocalDate().isBefore(fromDate)) {
                return false;
            }
        }

        if (params.containsKey("to")) {
            LocalDate toDate = LocalDate.parse(params.get("to").toString(), DateTimeFormatter.ISO_DATE);
            if (now.toLocalDate().isAfter(toDate)) {
                return false;
            }
        }

        // Prüfe die Zeit "time_from" und "time_to"
        if (params.containsKey("time_from")) {
            LocalTime timeFrom = LocalTime.parse(params.get("time_from").toString(), DateTimeFormatter.ISO_TIME);
            if (now.toLocalTime().isBefore(timeFrom)) {
                return false;
            }
        }

        if (params.containsKey("time_to")) {
            LocalTime timeTo = LocalTime.parse(params.get("time_to").toString(), DateTimeFormatter.ISO_TIME);
            if (now.toLocalTime().isAfter(timeTo)) {
                return false;
            }
        }

        // Prüfe den Monat (Akzeptiert mehrere Monate)
        if (params.containsKey("month")) {
            String[] months = params.get("month").toString().split(",");
            int currentMonth = now.getMonthValue();
            boolean monthMatch = false;
            for (String month : months) {
                if (Integer.parseInt(month.trim()) == currentMonth) {
                    monthMatch = true;
                    break;
                }
            }
            if (!monthMatch) {
                return false;
            }
        }

        // Prüfe spezifisches Datum
        if (params.containsKey("date")) {
            LocalDate date = LocalDate.parse(params.get("date").toString(), DateTimeFormatter.ISO_DATE);
            if (!now.toLocalDate().equals(date)) {
                return false;
            }
        }

        // Prüfe Wochentage
        if (params.containsKey("day")) {
            String[] days = params.get("day").toString().split(",");
            DayOfWeek currentDay = now.getDayOfWeek();
            boolean dayMatch = false;
            for (String day : days) {
                if (DayOfWeek.valueOf(day.trim().toUpperCase()).equals(currentDay)) {
                    dayMatch = true;
                    break;
                }
            }
            if (!dayMatch) {
                return false;
            }
        }

        return true; // Wenn alle Bedingungen erfüllt sind
    }
}
