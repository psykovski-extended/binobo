## Relevante Arbeitsschritte

- rest-controller schreiben für simulator und werteerfassung (delete-funktion einbauen damit datenbank nicht zu groß wird)
  - Funktion für Time-Stamp überwachung
  - Geschwindigkeit der REST-API mit Python skript testen
- blog fertigstellen
  - Sub-comment funktion schreiben
  - Delete-Funktion für Kommentare und Sub-Kommentare einbauen
  - 'This solved the issue' - Button einbauen
- admin-pages fertigstellen

- 3D-Simulator fertigstellen (Philipp nerven!)
  - ajax implementieren
  - Struktur für 3D-Skript überlegen

- home page fertig designen
- Entwicklerseite schreiben
    - Fotos von mir und philipp
  
- Sponsoren-seite machen
- Projektübersicht machen, so weit wie möglich zur Zeit

## Zweitrangige Ziele

- REST-API mit OAuth2 sichern (Verification-Token)
- Operator-Pages
- HTTPS einbauen

## Drittrangige Ziele

- exploit schutz einbauen (mit werner reden):
  - sql-injection
  
# Anforgerungen an den 3D-Simulator

- Kalibirierbar auf eine bestimmte fixe Abtastrate des Kontrollers // hierbei muss davon ausgegangen werden, dass die Abtastrate zumindest für eine Sekunde konstant bleibt
- Spontan adaptierbar auf einen Einbruch der Netzwerkgeschwindigkeit // optional
- Als Funktion aufrufbar: `simulator3D(let dataInJsonFormat)` wobei dann die aktuellen Daten gesetzt werden, welche angeben, wohin sich die Hand bewegen soll
- Eine `init(let canvas, let startPosition)` - Funktion zur aktivierung des Simulators, beaufschlagt werden Startposition der Hand und das Canvas, in welches gerendert werden soll
- Sollte ein Objekt mit den Werten "null" ankommen, soll eine Funktion aufgerufen werden, welche am Bildschirm eine Warnung ausgibt (Funktion dafür schreibe ich, Name der Funktion lass ich dir noch zukommen) und die letzte Position soll gespeichert werden
- Pfad zu obj- und mtl-File soll folgendem Muster entsprechen: '../simulatorModels/nameOfTheFile'
- Für das FOV muss immer die aktuelle Weite und Höhe des Canvas hergenommen werden

Der Aufbau des JSON-Files liegt als Template gespeichter in unserem Teams, sollte sich darin etwas ändern, dann geb ich eh Bescheid (sampling_rate - Feld wir noch hinzukommen, gibt die aktuelle Abtastrate an)
Sollten weitere Bedingungen für das Skript auftauchen, geben ich ebenfalls Bescheid, sollte dir noch etwas einfallen, dann gib entweder mir Bescheid oder bau es einfach ein, das entscheidest dann du je nach Gewichtung
Fertigstelleung des Ganzem wär so Ende September super, August wär auch gut, aber aufgrund der Sommerferien verstehe ich, wenn nicht viel passiert. (Aber eventuell sollten wir die Zeit clever nutzten, da die 5te bestimmt nicht einfach wird)
