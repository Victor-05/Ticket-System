# Ticket System - POO, SERBAN Victor Gabriel 323CA

## Descriere
Acest proiect reprezinta un sistem de gestionare a ticketelor si milestone-urilor intr-o echipa de dezvoltare software, permitand raportarea bug-urilor, asignarea lor catre developeri si urmarirea progresului prin diferite stari ale aplicatiei.

## Design Patterns Utilizate

In cadrul acestui proiect, am implementat 4 design pattern-uri fundamentale pentru a asigura o structura modulara, extensibila si usor de intretinut:

### 1. Singleton
**Unde este folosit:** `TicketStorage` si `MilestoneStorage`.  
**De ce l-am ales:** Am folosit Singleton pentru a gestiona bazele de date interne ale aplicatiei. Aceste storage-uri trebuie sa fie unice la nivel global in aplicatie pentru a asigura consistenta datelor (toate comenzile trebuie sa lucreze cu aceeasi lista de tickete si milestone-uri).  
**Cum ma ajuta:** Previne instantierea multipla si ofera un punct de acces unic catre date, evitand conflictele de sincronizare a informatiilor intre diferite module ale programului.

### 2. Factory
**Unde este folosit:** `CommandFactory` si `UserFactory`.  
**De ce l-am ales:** Aplicatia primeste input-uri diverse (diferite tipuri de comenzi si diferite roluri de utilizatori). Factory ne permite sa delegam responsabilitatea instantierii acestor obiecte catre o clasa specializata.  
**Cum ma ajuta:** Decupleaza logica de business de procesul de creare a obiectelor. Daca este nevoie de adaugarea unui nou tip de comanda sau un nou rol de utilizator, trebuiemodificat doar Factory-ul respectiv, fara a afecta restul sistemului.

### 3. Builder
**Unde este folosit:** Clasele de utilizatori: `Developer`, `Manager` si `Reporter`.  
**De ce l-am ales:** Utilizatorii sistemului au numeroase atribute (username, email, rol, data angajarii, expertiza, subalterni etc.), unele fiind optionale sau specifice doar anumitor roluri.  
**Cum ma ajuta:** Builder-ul permite construirea pas cu pas a obiectelor complexe, facand codul mult mai lizibil decat un constructor cu foarte multi parametri (telescoping constructor). De asemenea, asigura imutabilitatea obiectelor odata create.

### 4. State
**Unde este folosit:** Gestiunea fazelor aplicatiei (`ApplicationState` cu implementarile `DevelopmentState`, `TestingState`, `VerificationState`).  
**De ce l-am ales:** Aplicatia trece prin mai multe faze, iar comportamentul anumitor actiuni poate depinde de starea curenta a sistemului.  
**Cum ma ajuta:** Permite obiectului `Application` sa isi schimbe comportamentul atunci cand starea sa interna se modifica. In loc sa folosim numeroase structuri repetitive, logica specifica fiecarei stari este incapsulata in propria sa clasa, respectand principiul Single Responsibility.

## Functionalitati
- Gestionare utilizatori (Reporter, Developer, Manager).
- Raportare si urmarire tickete (bug-uri).
- Organizarea ticketelor in Milestone-uri.
- Generare de rapoarte (Customer Impact, Ticket Risk, Application Stability etc.).
- Gestiunea notificarilor pentru utilizatori.

## Structura Pachetelor

Proiectul este organizat in urmatoarele pachete pentru o mai buna separare a responsabilitatilor:

- **`main`**: Contine punctul de intrare in aplicatie si logica principala de control, inclusiv gestionarea starilor (State pattern).
- **`users`**: Gestioneaza entitatile de tip utilizator (Developer, Manager, Reporter) si crearea acestora folosind Builder si Factory.
- **`ticket`**: Contine clasele necesare pentru reprezentarea si stocarea ticketelor (bug-uri).
- **`milestone`**: Se ocupa de gruparea ticketelor in milestone-uri si urmarirea progresului acestora.
- **`command`**: Implementeaza diversele actiuni pe care utilizatorii le pot efectua in sistem, folosind un Factory pentru instantiere.
- **`actions`**: Contine logica specifica pentru anumite operatiuni de calcul si procesare a datelor.
- **`fileio`**: Gestioneaza citirea datelor de intrare si scrierea rezultatelor (input/output).
- **`constants`**: Stocheaza valorile constante utilizate la nivelul intregului proiect.
