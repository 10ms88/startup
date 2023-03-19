/*
 * created by Sergey Eg 3/21/2018
 */

package com.company.startup.utils;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public static SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat DDMMYYYY_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static SimpleDateFormat DTOPER_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static SimpleDateFormat DDMMYYYYHHMM_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static SimpleDateFormat DATE_DDMM = new SimpleDateFormat("dd.MM");
    private static SimpleDateFormat DATE_HHDDMM = new SimpleDateFormat("yyyyMMddHH");
    public static SimpleDateFormat DATE_DDMMYYYY = new SimpleDateFormat("dd.MM.yyyy");

    private static SimpleDateFormat SHOP_CALL_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private static final DateFormat INFOBIP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    public static final DateFormat OSMI_DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");

    public static final SimpleDateFormat DATEFACTORY = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static final DateFormat INFOBIP_EXT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private static final DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private static final DateFormat EMARSYS_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat EMARSYS_FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm");
    private static final DateFormat EMARSYS_RESPONSE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SQL_DATE_STRING = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat STANDART_NOONE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH-mm");

    public static Date OneCNullDate = DateUtils.getDateFromIso8601("0001-01-01T00:00:00");
    public static SimpleDateFormat RATELIMIT_YEAR = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat RATELIMIT_MONTH = new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat RATELIMIT_DAY = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat RATELIMIT_HOUR = new SimpleDateFormat("yyyyMMddHH");
    public static SimpleDateFormat RATELIMIT_MINUTE = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat RATELIMIT_SECOND = new SimpleDateFormat("yyyyMMddHHmmss");

    //2003-06-01 00:00
    public static final Date MINIMAL_TAXFREE_RECEIP_DATE = new Date(1054425600 * 1000L);


    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    public static Date minusDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    public static Date getDateFromOneC(Date date) {
        if (date != null && date.after(OneCNullDate)) {
            return date;
        } else {
            return null;
        }
    }

    //Преобразование из INFOBIP_DATE_FORMAT в Date
    public static Date getDateFromInfoBipDate(String stringDate) {
        Date date;
        try {
            date = INFOBIP_DATE_FORMAT.parse(stringDate);
        } catch (ParseException e) {
            LogUtil.printError("DateUtils getDateFromInfoBipDate error: " + e.getMessage());
            return null;
        }

        return date;
    }

    public static Date getDateFromDDMMYYYYDate(String stringDate) {
        Date date;
        try {
            date = DDMMYYYY_DATE_FORMAT.parse(stringDate);
        } catch (ParseException e) {
            LogUtil.printError("DateUtils getDateFromDDMMYYYYDate error: " + e.getMessage());
            return null;
        }

        return date;
    }


    public static Date getDateFromIso8601(String stringDate) {
        Date date;
        try {
            date = ISO8601_DATE_FORMAT.parse(stringDate);
        } catch (ParseException e) {
            LogUtil.printError("DateUtils getDateFromIso8601 error: " + e.getMessage());
            return null;
        }
        return date;
    }

    public static Date getDateWithMilliseconds(String stringDate) {
        Date date = null;
        try {
            date = DATEFACTORY.parse(stringDate);
        } catch (Exception e) {
           ExceptionUtils.logErrors(e);
        }
        return date;
    }

    public static String getStringDateWithMilliseconds(Date date) {
        String stringDate = null;
        try {
            stringDate = date == null ? null : DATEFACTORY.format(date);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return stringDate;
    }

    public static String getDateToISO8601(Date date) {
        return ISO8601_DATE_FORMAT.format(date);

    }

    //Преобразование из "20180331"(yyyy:MM:dd) в дату
    public static Date sqlDateFormatToDate(String sqlDate) {
        try {
            return SQL_DATE_FORMAT.parse(sqlDate);
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date stringTimeStampToDate(String timeStamp) {
        Date date;
        try {
            long longTime = Long.parseLong(timeStamp);
            longTime *= 1000L;
            date = new Date(longTime);
        } catch (NumberFormatException e) {
            log.error("Error", e);
            return null;
        }

        return date;
    }

    public static String fromDateToSQLDateFormat(Date date) {
        return SQL_DATE_FORMAT.format(date);
    }

    public static Date set10MorningTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        return cal.getTime();
    }

    public static Date setEndOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date truncateDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getEndOfDay(Date date) {
        date = truncateDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    public static Date addMinutesToDate(Date date, int min) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, min);
        return cal.getTime();
    }

    public static Date getDateMinusMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -minutes);
        return cal.getTime();
    }

    public static Date getDateMinusHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -hours);
        return cal.getTime();
    }

    public static String getDateDDMMFormat(Date date) {
        return DATE_DDMM.format(date);
    }

    public static String getDateHOURFormat(Date date) {
        return RATELIMIT_HOUR.format(date);
    }

    public static String getDateDDMMYYYYFormat(Date date) {
        return DATE_DDMMYYYY.format(date);
    }

    public static Date getBeginningOfYear(Date date) {
        date.setDate(1);
        date.setMonth(1);
        return date;
    }

    /*
     * Если месяц или день указан в виде Integer, преобразует в стринг.
     * Если день или месяц до 10, то к числу добавить 0. 3 -> 03.
     * */
    public static String formatDayOrMonth(Integer dayOrMonth) {
        String data = dayOrMonth.toString();
        if (data.length() == 1) {
            data = "0" + data;
        } else if (data.length() > 2) {
            data = "";
        }

        return data;
    }

    /*
     * Получить дату в виде массива [день, месяц, год] из даты вида dd.MM.yyyy
     * */
    public static Integer[] getDayMonthYearFromDate(String stringDate) {
        Date date;
        Integer[] dayMonthYear = null;
        try {
            date = DATE_DDMMYYYY.parse(stringDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            dayMonthYear = new Integer[3];
            dayMonthYear[0] = cal.get(Calendar.DATE);
            dayMonthYear[1] = cal.get(Calendar.MONTH) + 1;
            dayMonthYear[2] = cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayMonthYear;
    }

    //Проверка попадания даты в интервал
    public static boolean inInterval(Date start, Date end, Date date) {
        return ((date.compareTo(start) >= 0) && (date.compareTo(end) <= 0));
    }

    /*
     * Возвращает БОЛЬШУЮ дату из двух. Пример
     * first: 25.11.2004, second:27.11.2004 - вернется 27.11.2004
     * */
    public static Date getAfterDateFromDates(Date first, Date second) {
        Date afterDate = null;
        if (first != null && second != null) {
            afterDate = (first.after(second)) ? first : second;
        }
        return afterDate;
    }

    /*
     * Возвращает МЕНЬШУЮ дату из двух. Пример
     * first: 25.11.2035, second:27.11.2135 - вернется 25.11.2035
     * */
    public static Date getBeforeDateFromDates(Date first, Date second) {
        Date beforeDate = null;
        if (first != null && second != null) {
            beforeDate = (first.before(second)) ? first : second;
        }
        return beforeDate;
    }

    //Получить UTCTimestamp (исп. для Emarsys)
    public static String getUTCTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    public static String getEmarsysStringDate(Date date, boolean fullTime) {
        return (fullTime) ? EMARSYS_FULL_DATE_FORMAT.format(date)
                : EMARSYS_DATE_FORMAT.format(date);
    }

    public static Date getEmarsysDateFromShortString(String date) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        Date resultDate;
        try {
            resultDate = EMARSYS_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            LogUtil.printError("getEmarsysDateFromShortString ParseException: " + date);
            return null;
        }

        return resultDate;
    }

    public static Long convertDateToTimestamp(Date date) {
        Long result = null;
        if (date != null) {
            result = date.getTime() / 1000L;
        }
        return result;
    }

    public static Date getDateFromEmarsysResponse(String date) {
        Date resultDate;
        try {
            resultDate = EMARSYS_RESPONSE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            LogUtil.printError("getDateFromEmarsysResponse ParseException: " + date);
            return null;
        }

        return resultDate;
    }

    public static Date getDateFromNooneStandartString(String date) {
        Date resultDate;
        try {
            resultDate = STANDART_NOONE_FORMAT.parse(date);
        } catch (ParseException e) {
            LogUtil.printError("getDateFromNooneStandartString ParseException: " + date);
            return null;
        }

        return resultDate;
    }

    public static String getStringDateDtOperShort(Date date) {
        if (date != null) {
            String res = DTOPER_DATE_FORMAT.format(date);
            return res.substring(0, 8);
        } else {
            return StringUtils.EMPTY_STRING;
        }
    }

    public static String getStringDateDtOper(Date date) {
        if (date != null) {
            return DTOPER_DATE_FORMAT.format(date);
        } else {
            return StringUtils.EMPTY_STRING;
        }
    }

    /**
     * Вычисление разницы в днях между датами
     *
     * @param date           - Дата
     * @param subtrahendDate - вычитаемая дата
     * @return - Integer разница в днях между датами.
     */
    public static Integer getDaysBetween(Date date, Date subtrahendDate) {
        Long diff = date.getTime() - subtrahendDate.getTime();
        Long days = (diff / (1000 * 60 * 60 * 24));
        return days.intValue();
    }

    public static String getShopCAllStringDate(Date date) {
        if (date != null) {
            return SHOP_CALL_DATE_FORMAT.format(date);
        } else {
            return StringUtils.EMPTY_STRING;
        }
    }

    @Nullable
    public static Date getDateByMoscowBasedTimezone(@NotNull Date date, int moscowBasedTimezone) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, moscowBasedTimezone);
            return cal.getTime();
        } else {
            return null;
        }
    }

    @Nullable
    public static Date getDateByFormatPattern(String date, FormatPattern formatPattern) {
        try {
            return new SimpleDateFormat(formatPattern.id).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getStringByFormatPattern(Date date, FormatPattern formatPattern) {
        try {
            return new SimpleDateFormat(formatPattern.id).format(date);
        } catch (IllegalArgumentException e) {
            log.error("DateUtils.getStringByFormatPattern(" + date + ", " + formatPattern.id + "): " + e.getMessage());
            return null;
        }
    }

    public enum FormatPattern {

        dd_MM_yyyy___HH_mm_ss("dd.MM.yyyy HH:mm:ss"), //01.01.0001 00:00:00 формат даты 1с
        QUESTION_STAR_DATE_FROM_CSV("MM/dd/yyyy HH:mm:ss XXX"), // формат даты Question Star Api из csv-файла
        QUESTION_STAR_FROM_API("yyyy-MM-dd'T'HH:mm:ss XXX"), // формат даты Question Star Api от реста
        DATE_PATTERN_1C("yyyy-MM-dd'T'HH:mm:ss"), // формат даты Question Star Api от реста
        DATA_BASE_PATTERN("yyyy-MM-dd HH:mm:ss.SS"),
        SIMPLE_DATA_BASE_PATTERN("yyyy-MM-dd HH:mm:ss"),
        SIMPLE_DATE_AND_TIME_PATTERN("dd.MM.yyyy HH:mm");

        private final String id;

        FormatPattern(String id) {
            this.id = id;
        }
    }
}
