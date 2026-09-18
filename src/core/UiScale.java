package core;

/**
 * Escala UI a partir de uma resolução de design (1280x800).
 * O jogo original usava coordenadas fixas; isto adapta a qualquer tela.
 */
public final class UiScale {

    public static final int DESIGN_WIDTH = 1280;
    public static final int DESIGN_HEIGHT = 800;

    private UiScale() {
    }

    public static int sx(int x) {
        return Math.round(x * (Framework.frameLargura / (float) DESIGN_WIDTH));
    }

    public static int sy(int y) {
        return Math.round(y * (Framework.frameAltura / (float) DESIGN_HEIGHT));
    }

    public static int sw(int w) {
        return Math.max(1, Math.round(w * (Framework.frameLargura / (float) DESIGN_WIDTH)));
    }

    public static int sh(int h) {
        return Math.max(1, Math.round(h * (Framework.frameAltura / (float) DESIGN_HEIGHT)));
    }

    public static float scaleX() {
        return Framework.frameLargura / (float) DESIGN_WIDTH;
    }

    public static float scaleY() {
        return Framework.frameAltura / (float) DESIGN_HEIGHT;
    }

    /** Escala uniforme (menor eixo) — útil para ícones sem distorcer. */
    public static float uniform() {
        return Math.min(scaleX(), scaleY());
    }

    public static int su(int size) {
        return Math.max(1, Math.round(size * uniform()));
    }
}
