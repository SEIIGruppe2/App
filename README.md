# APP

## TCP
Eine TCP-Verbindung wird in einer dedizierten Klasse für die Serververbindung implementiert. Dabei werden folgende Klassen verwendet:
- Socket
- BufferedReader
- BufferedWriter

Ein String wird an den Server übergeben.

## Observable
In der Hauptmethode wird anstelle eines Threads mit dem Observable gearbeitet. Das Observable führt den Netzwerkaufruf durch (d.h. die Methode aus der TCP-Klasse wird aufgerufen). Durch das Abonnieren (subscribe) des Observers wird dieser darüber benachrichtigt, dass es eine Änderung im Observable gab. Der Observer verfügt über drei Methoden:
- `onNext`: Was mit der Nachricht geschehen soll, die vom Server empfangen wurde
- `onError`: Wenn keine oder eine falsche Antwort empfangen wurde oder ein anderer Fehler aufgetreten ist
- `onComplete`: Was nach Abschluss geschehen soll (z.B. Aufruf der nächsten Runde)

Disposable wird verwendet, um den Netzwerkaufruf zu verwalten und ihn bei Bedarf gezielt zu beenden.

## Testen
Der TCP-Client wird in einem try-catch-Block umschlossen, wodurch IOExceptions bei Fehlern im try-Block geworfen werden. Im Hauptbereich muss bereits überprüft werden, ob die Eingabe des Benutzers korrekt ist, bevor sie an den Server gesendet wird. Andernfalls sollte eine entsprechende Fehlermeldung ausgegeben werden.

## Vorraussichtliche Struktur
Der Netzwerkaufruf wird voraussichtlich in der MainActivity stattfinden, die sich im Paket `com.example.munchkin` befindet. Die TCP-Verbindung wird in einer separaten Klasse implementiert, die sich im Paket `/TCP` befindet.

## Imports
Für die TCP-Verbindung werden die folgenden Imports benötigt:
```java
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
```

Für den Observer und das Observable werden die folgenden Imports benötigt:
```java
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
```




