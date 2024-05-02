# APP

# Munchkin Panic - App

## Spielbeschreibung
Munchkin Panic ist ein Tower Defense-Spiel für vier Spieler, das sowohl kooperative als auch 
kompetitive Elemente enthält. Alle Spieler arbeiten zusammen, um eine Burg vor anrückenden Monstern
zu schützen. Sollte die Burg zerstört werden, verlieren alle Spieler. Wenn die Monster erfolgreich 
abgewehrt werden, gewinnt der Spieler mit den meisten getöteten Monstern (Monster-Punkten).

### Spielmechanik
Das Spielfeld ist in vier Zonen (oben, rechts, unten, links) und 
vier Ringe (Wald, Bogenschütze, König, Schwertkämpfer) unterteilt. Zu Beginn jeder Runde würfelt
ein Spieler dreimal, um die Zonen zu bestimmen, in denen neue Monster erscheinen. Während die
anderen Spieler die Hauptansicht sehen, steuert der würfelnde Spieler die Monsterplatzierungen.

### Monster und Kämpfe
- Jedes Monster erscheint am äußersten Ring, dem Wald, und bewegt sich jede Runde einen Ring 
weiter nach innen. Wenn ein Monster die Burg erreicht, fügt es der Burg Schaden zu und stirbt 
anschließend selbst. Die Burg hat insgesamt 10 Lebenspunkte.
- Jedes Monster verfügt über 1 bis 3 Lebenspunkte. Die Art des Monsters und seine Lebenspunkte 
werden zufällig bestimmt, wenn es am Spielfeld erscheint. Die Spieler müssen strategisch 
handeln, um die Monster erfolgreich zu bekämpfen.

### Kampfkarten
Die Spieler setzen Kampfkarten ein, um die Monster anzugreifen. Jede Zone auf dem Spielfeld ist 
einer anderen Farbe zugeordnet:
- Rot
- Grün
- Blau
- Braun

Die Kampfkarten sind ebenfalls diesen Farben zugeordnet und spezifisch für die Ringe ausgerichtet:

- **Roter Ritter**: Kann nur in der roten Zone im Ritter-Ring verwendet werden.
- **Grüner Bogenschütze**: Wirkt ausschließlich in der grünen Zone im Bogenschützen-Ring.
- **Gilt auch für alle anderen Karten**
- **Ausnahme: Held**: Der Held hat zwar eine Farbe und kann somit auch nur in dieser Zone 
eingesetzt werden, kann dafür aber in jedem Ring in dieser Zone benutzt werden. 

Jede Karte fügt dem Monster 1 Schadenspunkt zu. Diese spezifischen Zuordnungen erfordern, dass die
Spieler taktisch überlegen, welche Karten sie wo und wann einsetzen, um die Monster effektiv zu 
bekämpfen und die Burg zu schützen.

### Ziel des Spiels
Das Spielziel ist es, die Burg vor den Monstern zu schützen, indem die Spieler sie erfolgreich 
besiegen und gleichzeitig durch strategischen Einsatz ihrer Karten interagieren. Das Spiel endet 
entweder, wenn die Burg zerstört wird oder alle Monster erfolgreich abgewehrt wurden. Der Sieger
ist der Spieler, der die meisten Monster besiegt hat.


## Technische Details


## WebSocket

In dieser Anwendung wird eine WebSocket-Verbindung verwendet, um mit einem Server zu kommunizieren. 
Die WebSocket-Verbindung wird durch die `WebSocketClient`-Klasse implementiert, die in der Datei 
`WebSocketClient.java` zu finden ist. Dabei wird die `OkHttpClient`-Bibliothek für die 
WebSocket-Kommunikation verwendet.

Der WebSocket-Client stellt Methoden bereit, um eine Verbindung zum Server herzustellen
(`connectToServer`) und Nachrichten an den Server zu senden (`sendMessageToServer`).

Der WebSocket-Client verfügt über einen WebSocket-Listener, der auf Ereignisse wie das Öffnen 
der Verbindung, den Empfang einer Nachricht, das Schließen der Verbindung und Fehler reagiert. 
Bei Empfang einer Nachricht wird die Methode `onMessageReceived` des
`WebSocketMessageHandler`-Interfaces aufgerufen.


### Imports

Für die WebSocket-Kommunikation werden folgende Imports benötigt:

```java
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
````




## MainActivity
Die Hauptaktivität der Anwendung, MainActivity.java, ist für die Benutzeroberfläche zuständig 
und enthält die Logik zum Senden von Nachrichten über WebSocket und zum Anzeigen von empfangenen
Nachrichten.

### Methoden
onCreate: Wird aufgerufen, wenn die Aktivität erstellt wird. Hier werden die UI-Elemente 
initialisiert und der WebSocket-Client erstellt.

connectToWebSocketServer: Methode zum Herstellen einer Verbindung zum WebSocket-Server.
Registriert einen Handler für den Empfang von Nachrichten.

sendMessage: Methode zum Senden einer Nachricht an den WebSocket-Server.

messageReceivedFromServer: Methode, die aufgerufen wird, wenn eine Nachricht vom Server empfangen 
wird. Diese Methode zeigt die empfangene Nachricht in der TextView an und gibt sie über Log aus.



### Imports
Für die MainActivity werden folgende Imports benötigt:
```java
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
```


# WebSocketMessageHandler
Die WebSocketClient-Klasse implementiert die WebSocket-Kommunikation mit dem Server.

## Methoden

connectToServer: Methode zum Herstellen einer Verbindung zum WebSocket-Server.

sendMessageToServer: Methode zum Senden einer Nachricht an den WebSocket-Server.

finalize: Schließung der Verbindung








