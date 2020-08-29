## Zadanie do wykładu 6 - Przedszkolaki na obiedzie

W Przedszkolu "Bajtek" nadeszła pora obiadu. Dzieci jak zwykle umyły ręce, i radośnie podbiegły do stołu.
Nie był to zwykły stół - był on okrągły. Dzieci obsiadły go grzecznie dookoła, i chwyciły za widelce... a przynajmniej niektóre z nich...
Widelcy było dokładnie tyle, ile dzieci - i każdy z nich był umieszczony dokładnie pomiędzy talerzami dwóch sąsiadujących ze sobą bombelków. Aby zjeść, każde dziecko potrzebuje dwóch widelców, ze swojej prawej i lewej strony.

Wcielasz się w rolę Pani Przedszkolanki. Twoim zadaniem jest takie zarządzanie widelcami, aby wszystkie dzieci miały okazję zjeść, i aby żadne nie było poszkodowane. Jeśli któreś z dzieci zapłacze - niestety, przegrałeś... Uważaj - niektóre z dzieci są bardziej niecierpliwe niż inne!


### Nieco bardziej technicznie
Należy doimplementować klasę `Kindergarten` w taki sposób, aby rozwiązywała problem jedzących dzieci (zupełnie przypadkowo podobny do problemu jedzących filozofów).

Program przyjmuje jeden parametr - nazwa pliku wejściwego do wczytannia.

Plik w pierwszej linii zawiera ilość przedszkolaków w danej grupie (jest to też ilość widelców). Nastepnie zawiera opisy przedszkolaków; jeden przedszkolak na linię. Opis przedszkolaka zawiera jego imię, oraz czas głodnienia. Imię oraz czas należy wykrzystać przy konstruckji obiektów `Child`. Przedszkolaki siedzą w okręgu; ostatni sąsiaduje z przedostatnim, oraz z pierwszym.

Każdy obiekt `Child` tworzy wątek, który sprawdza, czy dane dziecko nie jest zbyt głodne. Jeśli jest zbyt głodne - zapłacze na `System.err`. Zadanie jest poprawnie rozwiązane, jeśli żadne dziecko nie zapłacze w trakcie obiadu (wykonania programu). Obiad trwa 10 sekund.

Można (a nawet należy) dziedziczyć po klasie Child.

### UWAGA
W tym zadaniu nie wystarczy "przejść" pipeline'a - konieczna analiza kodu.
