# testAnalyzer
Napisz program umożliwiający opracowanie wyników testów wyboru (przeprowadzonych np. podczas kolokwium). Opracowanie ma polegać na załadowaniu dwóch plików - szablonu testu (format dowolny) zawierającego pytania i odpowiedzi, ze wskazaniem na prawidłowe, a także zbioru kart odpowiedzi (proponowany format .csv) z odpowiedziami udzielonymi przez studentów. Wczytane dane powinny zostać przetworzone, a rezultaty wyświetlone w przystępnej formie. 

Aplikacja powinna posiadać własną ikonę, ekran powitalny, dokumentację (javadoc). 

Przykładowe statystyki obliczane przez program:
Histogram wyników (dla każdej karty odpowiedzi weryfikuje się liczbę zdobytych punktów, a następnie robi się histogram tych punktów)
Liczby przypadków wpadających w zadane przedziały ocen
Rozkład poprawnych odpowiedzi wg klucza
i inne

Program powinien być podzielony na dwie części:
Bibliotekę pomocniczą
Właściwą aplikację

Biblioteka powinna zapewniać modele danych powiązanych z testami (szablon, karta odpowiedzi) oraz logikę ich przetwarzania (wyliczanie statystyk), aplikacja natomiast obsługę UI i ładowania danych. 

Biblioteka powinna być spakowana za pomocą narzędzia jar, a następnie dołączona do aplikacji, która również ma zostać spakowana przy pomocy narzędzia jar. 
