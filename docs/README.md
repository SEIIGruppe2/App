# Dokumentation & Formattierungskonventionen

## Inhalt
* [Dokumentation](#dokumentation)
* [GIT](#git)
  * [Konventionen für Commit-Messages](#konventionen-für-commit-messages)

## Dokumentation

* grundsätzlich Dokumentation via `JavaDoc` Kommentare
* Für die Erklärung von Frameworks, Workflows, etc. immer ein `.md` File erstellen
  * Issue erstellen -> Label: `question` -> selbst assignen
  * Kurzbeschreibung `.md`-Syntax [hier](MarkdownTemplate.md)
  * Der Android Studio Editor für Markdown hat für unsere JDK keine Preview Funktion
    * Alternative: `.md` Files in VSCode erstellen

## GIT

* **Merges in den `main` Branch nur via Pull Request**

### Konventionen für Commit-Messages

* [Konventionen Kick-Off Meeting](https://www.conventionalcommits.org/en/v1.0.0/#specification)

#### Kurzzusammenfassung:

```
[type]: Titel

<body>
* mit Sternchen als Bullet Point arbeiten
</body>
```

**Rufzeichen nach dem type stehen für eine BREAKING CHANGE**

Folgende Typen sind möglich:

* feat(scope)
  * neues Feature
  * scope optional
  * Issue type -> `enhancement`
* docs
  * Recherche bzw. Dokumentation
  * Issue type -> `question` oder `documentation`
* chore
  * für alle weiteren Commits / Arbeiten am Projekt zu beachten
* fix
  * Issue type -> `bug`
* test
  * Für Testfälle
