# MVC

**Quelle:** [Wikipedia](https://de.wikipedia.org/wiki/Model_View_Controller#:~:text=Model%20View%20Controller%20(MVC%2C%20englisch,und%20Programmsteuerung%20(englisch%20controller).)

* Entwurfsmuster zur Softwarepartition in 3 Komponenten
  * Model (Datenkomponente)
  * View (Ansichtskomponente / GUI)
  * Controller (Steuerung / Verwaltung)
* ermöglicht flexiblen Programmentwurf
* erleichtert Erweiterung & Änderung des Programms
* ermöglicht Wiederverwendbarkeit von Komponenten
* Komponenten grundsätzlich voneinander unabhängig

## Model

**Quelle:** [javabeginners.de](https://javabeginners.de/Design_Patterns/Model-View-Controller.php)

* Datenkomponente
* enthält Daten wie Nutzereingaben, vordefinierte Messages, Resultate aus Datenbankabfragen
* implementiert oft die Geschäftslogik
  * also Änderung der Daten etc.
  * entscheidet welche Response auf bestimmte Nutzereingaben gesendet wird
* enthält **keine** Referenzen auf die anderen beiden Komponenten
  * komplett unabhängig

## View

**Quelle:** [javabeginners.de](https://javabeginners.de/Design_Patterns/Model-View-Controller.php)

* Rein für die graphische Datendarstellung verantwortlich
* implementiert **weder** Programmlogik **noch** Daten im Source-Code
  * Definiert Schnittstellen dafür (bspw. EventListener)

## Controller

**Quelle:** [javabeginners.de](https://javabeginners.de/Design_Patterns/Model-View-Controller.php)

* Komponente die für die Verwaltung zuständig ist
* vermittelt zwischen *Model* und *View*
* Oft mittels `Observer` - Pattern realisiert
* Braucht Zugang zu *Model* und *View*


**Beispiele:**

* [javabeginners.de](https://javabeginners.de/Design_Patterns/Model-View-Controller.php)
  * Beispiel mit Java Swing
  * Simple nur für Verständnis
* [openclassrooms.com](https://openclassrooms.com/en/courses/4661936-develop-your-first-android-application/4679186-learn-the-model-view-controller-pattern#)
  * Android App Beispiel
  * bei uns wird das Model am Server liegen bzw. teilweise auf beide Repos aufgeteilt werden
* [codegym.cc](https://codegym.cc/de/groups/posts/de.303.teil-7-einfuhrung-in-das-mvc-muster-model-view-controller-)
  * Genaue Beschreibung der Modularisierung

## Mapping auf das Projekt

* **View** --> Android App
* **Controller** --> Netzwerkkommunikation
  * WebSocketClient in der App
  * WebSocketHandler am Server
* **Model** --> Nutzereingaben
  * via Netzwerkkommunikation an den Server übertragen
  * speichert rein die Werte
  * Spiellogik
