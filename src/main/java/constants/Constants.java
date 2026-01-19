package constants;

public final class Constants {
    private Constants() {

    }
    public static int STRING_MIN_SIZE = 10;
    public static int DAYS_TO_CHANGE_STATUS = 3;
    public static int DAYS_TO_CLOSE_TESTING = 12;

    public static final int FREQUENCY_RARE = 1;
    public static final int FREQUENCY_OCCASIONAL = 2;
    public static final int FREQUENCY_FREQUENT = 3;
    public static final int FREQUENCY_ALWAYS = 4;

    public static final int SEVERITY_MINOR = 1;
    public static final int SEVERITY_MODERATE = 2;
    public static final int SEVERITY_CRITICAL = 3;

    public static final int BUSINESS_PRIORITY_LOW = 1;
    public static final int BUSINESS_PRIORITY_MEDIUM = 2;
    public static final int BUSINESS_PRIORITY_HIGH = 3;
    public static final int BUSINESS_PRIORITY_CRITICAL = 4;

    public static final double PERCENATGE = 100.;

    public static final int BUSINESS_VALUE_S = 1;
    public static final int BUSINESS_VALUE_M = 3;
    public static final int BUSINESS_VALUE_L = 6;
    public static final int BUSINESS_VALUE_XL = 10;

    public static final int CUSTOMER_DEMAND_LOW = 1;
    public static final int CUSTOMER_DEMAND_MEDIUM = 3;
    public static final int CUSTOMER_DEMAND_HIGH = 6;
    public static final int CUSTOMER_DEMAND_VERY_HIGH = 10;

    public static final int BUG_IMPACT = 48;
    public static final int FEATURE_IMPACT = 100;
    public static final int UI_IMPACT = 100;

    public static final double BUG_SCORE = 10.0;
    public static final double BUG_RESOLUTION = 70.0;
    public static final int FEATURE_RESOLUTION = 100;
    public static final int UI_RESOLUTION = 100;

    public static final double BUG_RISK = 12.;
    public static final double FEATURE_RISK = 20.;
    public static final int UI_RISK = 11;

    public static final int RISK_NEGLIGIBLE = 24;
    public static final int RISK_MODERATE  = 49;
    public static final int RISK_SIGNIFICANT = 74;
    public static final int RISK_MAJOR = 100;

}
