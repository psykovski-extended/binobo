## Relevante Arbeitsschritte

### Front - and Backend

- blog umbauen
  - api erweitern
  - solved-the-issue button einbauen
  - editorjs.io anstelle von editor.io verwenden
- responsive layout
- neues layout fertigstellen
  - operator-pages (list all users, delete or set deactivated)
  - auf react wechseln

- Projektübersicht machen, so weit wie möglich zur Zeit
- Rauschen der übertragenen Werte filtern (IFR - Filter → exponential Filter)

### Firmware

- UART - commands interpretieren, really?

### Emulator

- connection lost popup
- live-plot for current rotation of all joints / current selected joints
- show current selected joint and its hierarchy (handy-svg) ?
- how-to-use dialog einbauen
- Kalibrationsschritte einbauen

# Ideensammlung zur Weiterentwicklung und Implementierung weiterer Features

- Editor.md weiterentwickeln, um User verlinken zu können
- Öffentliche Ansicht des Kontos eines Users
- Searchbar bei Blogeinträgen
- Tags für Blogeinträge
- Google login (Firebase)
- OAuth2
- Eigener Cloud-Server um Bilder hochzuladen
- Mehrere Configs speichern (WLAN, Token)
- mehrere Modelle für Emulator
- WebRTC - Derivat für Emulator, um Punkt-zu-Punkt Verbindung vom ESP32 zum Webbrowser aufzubauen

# Anforderungen an den 3D-Simulator

- Kalibrierbar auf eine bestimmte fixe Abtastrate des Kontrollers
- Als Funktion aufrufbar: `simulator3D(let dataInJsonFormat)` wobei dann die aktuellen Daten gesetzt werden, welche angeben, wohin sich die Hand bewegen soll
- Eine `init(let canvas, let startPosition)` - Funktion zur aktivierung des Simulators, beaufschlagt werden Startposition der Hand und das Canvas, in welches gerendert werden soll
- Sollte ein Objekt mit den Werten "null" ankommen, soll eine Funktion aufgerufen werden, welche am Bildschirm eine Warnung ausgibt (Funktion dafür schreibe ich, Name der Funktion lass ich dir noch zukommen) und die letzte Position soll gespeichert werden
- Pfad zu obj- und mtl-File soll folgendem Muster entsprechen: `../simulatorModels/nameOfTheFile`
- Für das FOV muss immer die aktuelle Weite und Höhe des Canvas hergenommen werden

Der Aufbau des JSON-Files liegt als Template gespeichert in unserem Team, sollte sich darin etwas ändern, dann geb ich eh Bescheid (sampling_rate – Feld wir noch hinzukommen, gibt die aktuelle Abtastrate an).
Sollten weitere Bedingungen für das Skript auftauchen, geben ich ebenfalls Bescheid, sollte dir noch etwas einfallen, dann gib entweder mir Bescheid oder bau es einfach ein, das entscheidest dann du je nach Gewichtung
Fertigstellung des Ganzem wär so Ende September super, August wär auch gut, aber aufgrund der Sommerferien verstehe ich, wenn nicht viel passiert. (Aber eventuell sollten wir die Zeit clever nutzten, da die 5te bestimmt nicht einfach wird)

# Dokumentation

- Flussdiagramme
- Datenstrukturen, relationale Datenstrukturen erklären
- Verwendete Frameworks
- Grundgedanken hinter der Software
- Warum Java/Micropython?
- Warum `three.js`?
- Aufbau der wichtigsten Komponenten:
  - Server: (+ Flussdiagramme)
    - REST API
    - WebSockets (STOMP)
    - Blog
    - Simulator
    - Spring
    - Hibernate
    - MySQL
    - Thymeleaf
    - MVC
    - JPA
      - ORM
  - Kontroller:
    - Micropython?
    - Flussdiagramm
    - Warum mit Handy verbunden?
    - Assertion
  - Asynchrones Programmieren
  - Android App:
    - Warum?
- Kontroller:
  - Funktionsweise
  - Kalibration
  - Konnex zu Firmware erklären
  - Blender statt Inventor?
  - Poti Halterung

## Aufbau

- Ausgangslage und Vision
- Zielsetzung
- Projektübersicht
  - Aufgabeneinteilung
- Java Webserver
  - Spring 4
  - Hibernate
    - JDBC
    - ORM
    - PostgreSQL
  - Frontend
    - Thymeleaf
    - Design
    - Emulator
    - Blog
  - Authentication
  - STOMP-Sockets
  - SSL-Zertifikat
- Python Websocket-Server
  - Warum Python?
  - Was sind Websockets?
  - Source Code
  - Flussdiagramm
- Firmware
  - Micropython
  - Source Code
    - Verwendete Libraries
  - Flussdiagramm
- Android App
  - Warum eine App?
  - Flussdiagramm
- Kontroller
- Resultat
