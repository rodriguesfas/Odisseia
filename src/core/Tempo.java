package core;

/**
 * Delta time em relação a 60 FPS de referência.
 * Velocidades antigas (px/frame @ 60) usam {@link #scale()}.
 */
public final class Tempo {

    public static final double REF_FPS = 60.0;
    public static final double REF_DT_SECONDS = 1.0 / REF_FPS;
    private static final double MAX_DT_SECONDS = 1.0 / 20.0; // evita saltos após hitch

    private static double deltaSeconds = REF_DT_SECONDS;

    private Tempo() {
    }

    public static void setDeltaNanos(long nanos) {
        double dt = nanos / 1_000_000_000.0;
        if (dt < 0) {
            dt = REF_DT_SECONDS;
        } else if (dt > MAX_DT_SECONDS) {
            dt = MAX_DT_SECONDS;
        }
        deltaSeconds = dt;
    }

    /** Segundos desde o último frame. */
    public static double dt() {
        return deltaSeconds;
    }

    /**
     * Multiplicador para velocidades definidas em px/frame @ 60 FPS.
     * Em 60 FPS retorna ~1; em 30 FPS ~2; em 120 FPS ~0.5.
     */
    public static double scale() {
        return deltaSeconds / REF_DT_SECONDS;
    }
}
