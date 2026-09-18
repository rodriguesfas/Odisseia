# Odisseia

Jogo de nave desenvolvido em Java (Swing).

## Requisitos

- JDK 21 ou superior
- Maven 3.8+ (opcional, para o build padrão)

## Compilar e executar

Com Maven:

```bash
mvn -q package
java -jar target/odisseia-1.0.0-SNAPSHOT.jar
```

Sem Maven (javac):

```bash
mkdir -p target/classes
javac -d target/classes $(find src -name '*.java')
cp -r resources/* target/classes/
java -cp target/classes main.Window
```

## Controles

- Setas ou WASD: mover a nave
- Espaço / Ctrl: laser
- ESC: voltar ao menu
- Mísseis: liberados por power-up

## Áudio

Efeitos e músicas ficam em `resources/audio/` (WAV) e são tocados via `javax.sound.sampled`.
Pastas antigas `music/`, `sounds/` e `sons/` (MP3/OGG não usados) foram removidas.

## Recordes

Os recordes são gravados em `~/.odisseia/arquivoRecorde.txt`.

## Layout

A UI escala a partir de 1280×800 (`UiScale`), então botões e títulos acompanham a resolução/tela cheia.

## Screenshots

Tela inicial  
![](https://github.com/rodriguesfas/Odisseia/blob/master/img/tela_Menu_Inicial.png)

Tela jogo  
![](https://github.com/rodriguesfas/Odisseia/blob/master/img/tela_Game.png)

Tela gameover  
![](https://github.com/rodriguesfas/Odisseia/blob/master/img/tela_Game_Over.png)

Tela pontuação  
![](https://github.com/rodriguesfas/Odisseia/blob/master/img/tela_Recorde.png)
