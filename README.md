# APP

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








