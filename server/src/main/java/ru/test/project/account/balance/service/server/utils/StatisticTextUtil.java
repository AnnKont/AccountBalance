package ru.test.project.account.balance.service.server.utils;

import java.time.Instant;

import ru.test.project.account.balance.service.server.models.StatisticInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StatisticTextUtil {

    /**
     * Name of get amount request
     */
    private static final String NAME_OF_GET_AMOUNT_REQUEST = "Get Amount";
    /**
     * Name of add amount request
     */
    private static final String NAME_OF_ADD_AMOUNT_REQUEST = "Add Amount";

    public static String createText(StatisticInfo getRequestStatisticInfo, StatisticInfo addRequestStatisticInfo) {
        StringBuilder stringBuilder = new StringBuilder("\nStatistic: ").append(Instant.now()).append("\n");
        addMapToStringBuilder(stringBuilder, getRequestStatisticInfo, NAME_OF_GET_AMOUNT_REQUEST);
        addMapToStringBuilder(stringBuilder, addRequestStatisticInfo, NAME_OF_ADD_AMOUNT_REQUEST);
        return stringBuilder.toString();
    }


    /**
     * Add map data to string builder
     *
     * @param stringBuilder - current string builder
     * @param statisticInfo - statistic
     * @param nameOfRequest - name of map
     */
    private static void addMapToStringBuilder(StringBuilder stringBuilder, StatisticInfo statisticInfo,
            String nameOfRequest) {
        stringBuilder.append(nameOfRequest)
                .append("\n")
                .append(" in second: ")
                .append(statisticInfo.getCountInSecond())
                .append("\n")
                .append(" in minute: ")
                .append(statisticInfo.getCountInMinute())
                .append("\n")
                .append(" in hour: ")
                .append(statisticInfo.getCountInHour());
        stringBuilder.append("\n");
    }
}