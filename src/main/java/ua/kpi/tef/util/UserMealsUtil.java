package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> resultMealList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDaysMap = new HashMap<>();
        for (UserMeal element : mealList) {
            if (!caloriesPerDaysMap.containsKey(element.getDateTime().toLocalDate())){
                caloriesPerDaysMap.put(element.getDateTime().toLocalDate(),element.getCalories());
            } else {
                caloriesPerDaysMap.put(element.getDateTime().toLocalDate(), element.getCalories()
                        + caloriesPerDaysMap.get(element.getDateTime().toLocalDate()).intValue());
            }
        }
        for (UserMeal element : mealList) {
            if (TimeUtil.isBetween(element.getDateTime().toLocalTime(), startTime, endTime)) {
                resultMealList.add(new UserMealWithExceed(element.getDateTime(), element.getDescription(),
                        caloriesPerDaysMap.get(element.getDateTime().toLocalDate()).intValue(),
                        caloriesPerDaysMap.get(element.getDateTime().toLocalDate()).intValue()>caloriesPerDay));
            }
        }

        for (UserMealWithExceed element:resultMealList) {
            System.out.println(element);

        }
        return resultMealList;
    }
}
