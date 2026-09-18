package gerenteSom;

/**
 * Nomes canônicos dos efeitos usados pelo jogo.
 */
public final class Sons {

    public static final String LASER = "audio/laser.wav";
    public static final String EXPLODE = "audio/explode.wav";
    public static final String EXPLODE_MINI = "audio/explode_mini.wav";
    public static final String MENU_CLICK = "audio/menu_click.wav";
    public static final String GAME_OVER = "audio/game_over.wav";
    public static final String POWERUP = "audio/powerup.wav";
    public static final String MUSIC_MENU = "audio/music_menu.wav";
    public static final String MUSIC_GAME = "audio/music_game.wav";

    private Sons() {
    }

    public static void laser() {
        GerenteSom.getInstance().play(LASER);
    }

    public static void explode() {
        GerenteSom.getInstance().play(EXPLODE);
    }

    public static void explodeMini() {
        GerenteSom.getInstance().play(EXPLODE_MINI);
    }

    public static void menuClick() {
        GerenteSom.getInstance().play(MENU_CLICK);
    }

    public static void powerUp() {
        GerenteSom.getInstance().play(POWERUP);
    }

    public static void gameOver() {
        GerenteSom.getInstance().play(GAME_OVER);
    }

    public static void musicaMenu() {
        GerenteSom.getInstance().playMusica(MUSIC_MENU);
    }

    public static void musicaJogo() {
        GerenteSom.getInstance().playMusica(MUSIC_GAME);
    }

    public static void pararMusica() {
        GerenteSom.getInstance().pararMusica();
    }
}
