public final class Easing {
    public static double easeInQuad(double t, double b, double c, double d){
        t /= d;
        return c*t*t + b;
    }

    public static double easeOutQuad(double t, double b, double c, double d){
        t /= d;
        return -c * t*(t-2) + b;
    }

    public static double easeInOutQuad(double t, double b, double c, double d){
        if ((t/=d/2) < 1) return c/2*t*t + b;
        return -c/2 * ((--t)*(t-2) - 1) + b;
    }
}
