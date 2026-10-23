# Student Management System — Phase 7 (Maven + Git)

Console-based Java 17 application using PostgreSQL through JDBC. This upgrades the Phase 6 code with Maven dependency management, SLF4J/Logback logging, JUnit 5 tests, student search by course, and centralized validation for null/malformed email addresses.

## Prerequisites
- JDK 17+
- Maven 3.9+
- PostgreSQL and a database named `student_management`

## Database setup
Run the SQL scripts in order:
```bash
psql -U postgres -d student_management -f sql/schema.sql
psql -U postgres -d student_management -f sql/sample_data.sql
```
Configure local connection settings using environment variables:
```bash
export DB_URL='jdbc:postgresql://localhost:5432/student_management'
export DB_USER='postgres'
export DB_PASSWORD='your-local-password'
```
Never commit credentials or `.env` files.

## Build, test, package and run
```bash
mvn clean
mvn compile
mvn test
mvn package
mvn install
mvn dependency:tree
java -jar target/student-management-1.0.0-SNAPSHOT.jar
```
The Shade plugin creates the runnable fat JAR at `target/student-management-1.0.0-SNAPSHOT.jar`. Database operations require a reachable PostgreSQL database. Validate with `mvn clean package` on a fresh clone.

## Phase 7 features
- Maven coordinates: `com.yourname.sms:student-management:1.0.0-SNAPSHOT`; Java 17 and UTF-8.
- PostgreSQL JDBC (runtime), JUnit Jupiter (test), SLF4J + Logback (compile), Apache Commons Lang (additional dependency).
- Student search by course name (menu option 23).
- Centralized validation rejects null/malformed email addresses and normalizes accepted emails.
- JUnit tests cover email, name, age and marks validation.

## Git branching strategy
- `main`: stable, release-ready code; no direct commits.
- `develop`: integration branch.
- `feature/*`: created from `develop`, merged through pull requests.
- `hotfix/*`: urgent fix branched from `main`, merged into both `main` and `develop`.
Suggested branches: `feature/maven-setup`, `feature/logging`, `feature/unit-tests`, `feature/search-student`; hotfix: `hotfix/fix-null-email`.

## Git command cheat-sheet
```bash
git status
git log --oneline --graph --all
git diff
git stash
git stash pop
git revert <commit-sha>
git commit --amend
```
Use `git commit --amend` to correct the latest local commit before it is shared. Avoid rewriting shared commits unless the team coordinates it.

## GitHub submission checklist
- [ ] Push `main`, `develop`, all feature branches and `hotfix/fix-null-email`.
- [ ] Make at least 20 meaningful, focused commits; do not manufacture empty commits.
- [ ] Create and resolve one deliberate merge conflict and document the resolution here.
- [ ] Open and merge at least 3 pull requests from feature branches into `develop`.
- [ ] Tag and push the stable release: `git tag -a v1.0.0 -m "Stable release v1.0.0" && git push origin v1.0.0`.
- [ ] Capture screenshots of the Git graph, a merged PR and successful Maven build in `docs/`.

Local project files cannot create GitHub pull requests or remote evidence automatically; complete those items in your GitHub repository.

## Initialize Git
```bash
git init
git config user.name "Your Name"
git config user.email "you@example.com"
git add . && git commit -m "chore: import Phase 6 student management project"
git branch -M main
git checkout -b develop
```

# Student Management System — Phase 7 (Maven + Git)

Console-based Java 17 application using PostgreSQL through JDBC. This upgrades the Phase 6 code with Maven dependency management, SLF4J/Logback logging, JUnit 5 tests, student search by course, and centralized validation for null/malformed email addresses.

## Prerequisites
- JDK 17+
- Maven 3.9+
- PostgreSQL and a database named `student_management`

## Database setup
Run the SQL scripts in order:
```bash
psql -U postgres -d student_management -f sql/schema.sql
psql -U postgres -d student_management -f sql/sample_data.sql
```
Configure local connection settings using environment variables:
```bash
export DB_URL='jdbc:postgresql://localhost:5432/student_management'
export DB_USER='postgres'
export DB_PASSWORD='your-local-password'
```
Never commit credentials or `.env` files.

## Build, test, package and run
```bash
mvn clean
mvn compile
mvn test
mvn package
mvn install
mvn dependency:tree
java -jar target/student-management-1.0.0-SNAPSHOT.jar
```
The Shade plugin creates the runnable fat JAR at `target/student-management-1.0.0-SNAPSHOT.jar`. Database operations require a reachable PostgreSQL database. Validate with `mvn clean package` on a fresh clone.

## Phase 7 features
- Maven coordinates: `com.yourname.sms:student-management:1.0.0-SNAPSHOT`; Java 17 and UTF-8.
- PostgreSQL JDBC (runtime), JUnit Jupiter (test), SLF4J + Logback (compile), Apache Commons Lang (additional dependency).
- Student search by course name (menu option 23).
- Centralized validation rejects null/malformed email addresses and normalizes accepted emails.
- JUnit tests cover email, name, age and marks validation.

## Git branching strategy
- `main`: stable, release-ready code; no direct commits.
- `develop`: integration branch.
- `feature/*`: created from `develop`, merged through pull requests.
- `hotfix/*`: urgent fix branched from `main`, merged into both `main` and `develop`.
Suggested branches: `feature/maven-setup`, `feature/logging`, `feature/unit-tests`, `feature/search-student`; hotfix: `hotfix/fix-null-email`.

## Git command cheat-sheet
```bash
git status
git log --oneline --graph --all
git diff
git stash
git stash pop
git revert <commit-sha>
git commit --amend
```
Use `git commit --amend` to correct the latest local commit before it is shared. Avoid rewriting shared commits unless the team coordinates it.

## GitHub submission checklist
- [ ] Push `main`, `develop`, all feature branches and `hotfix/fix-null-email`.
- [ ] Make at least 20 meaningful, focused commits; do not manufacture empty commits.
- [ ] Create and resolve one deliberate merge conflict and document the resolution here.
- [ ] Open and merge at least 3 pull requests from feature branches into `develop`.
- [ ] Tag and push the stable release: `git tag -a v1.0.0 -m "Stable release v1.0.0" && git push origin v1.0.0`.
- [ ] Capture screenshots of the Git graph, a merged PR and successful Maven build in `docs/`.

Local project files cannot create GitHub pull requests or remote evidence automatically; complete those items in your GitHub repository.

## Initialize Git
```bash
git init
git config user.name "Your Name"
git config user.email "you@example.com"
git add . && git commit -m "chore: import Phase 6 student management project"
git branch -M main
git checkout -b develop
```
