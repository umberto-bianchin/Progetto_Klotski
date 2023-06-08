# KLOTSKI PUZZLE

Welcome to the Klotski game project. This is a classic sliding block puzzle game that challenges you to move a large red block into a particular escape position. 
The program is written with Java Swing and uses a database and a solver API hosted on AWS. The project includes the game itself, a test suite, and the documentation. 
The source code is available on GitHub under the MIT License.

## Authors

* [@umberto-bianchin](https://www.https/github.com/umberto-bianchin)
* [@ivanbrillo](https://www.github.com/ivanbrillo)
* [@ricky0219](https://www.github.com/ricky0219)
* [@AlessandroCoron](https://www.github.com/AlessandroCoron)
* [@claudiadecarlo19](https://www.github.com/claudiadecarlo19)

## Badges

[![MIT License](https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge)](https://github.com/umberto-bianchin/Progetto_Klotski/blob/master/LICENSE.md)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)](https://www.java.com/en/) 
[![Node.js](https://img.shields.io/badge/Node.js-43853D?style=for-the-badge\&logo=node.js\&logoColor=white)](https://nodejs.org/en) 
[![AWS](https://img.shields.io/badge/Amazon\_AWS-232F3E?style=for-the-badge\&logo=amazon-aws\&logoColor=white)](https://aws.amazon.com/)
[![Maven](https://img.shields.io/badge/MAVEN-3.9.2-BLUE?style=for-the-badge&logo=appveyor)](https://https://maven.apache.org/)

[![view - Documentation](https://img.shields.io/badge/view-Documentation-blue?style=for-the-badge)](https://umberto-1.gitbook.io/klotski-game/documentation)

## Manual
### Requirements
You will need Java to be installed on your computer in order to run the project, and you have to use JDK 19 or newer versions as it has been developed and tested on that version.
You will also need an internet connection.
### Installation

**Follow these steps**:
1) Open our  [@GitHub](https://github.com/umberto-bianchin/Progetto_Klotski)
2) Click on the green button **Code** and then on **Download ZIP**
<figure><img src=".gitbook/assets/Immaginegit.png" alt=""></figure>

3) Go to the download directory and unzip the file

4) a) **Using Maven from cmd**: navigate to the project folder 

```bash
  cd Download\Progetto_Klotski-master
  mvn package -DskipTests
```
You can now find the **JAR file** in the directory named **Target** as **Progetto_Klotski-1.0-jar-with-dependencies.jar**, or you can run it throught cmd with
```bash
cd target
java -jar Progetto_Kltotski-1.0-jar-with-dependencies.jar
```
4) b) **Maven not installed**: If you don't have maven installed, but the JAVA_HOME system variable already set-up, you can achive the same results with 
```bash
./mvnw package -DskipTests 
```
4) c) **Using Intellij Idea**: open **Intellij Idea**, go to the **File** window, then **Open** and select the project folder. After that, click on the configuration window
<figure><img src=".gitbook/assets/immagineintellij1.png" alt=""></figure>

Click on **Add new configuration**, then on **Application**
<figure><img src=".gitbook/assets/Immagineintellij2.jpg" alt=""></figure>

Now select the main class and click on **Ok** on both windows
<figure><img src=".gitbook/assets/Immagineintellij3.png" alt=""></figure>

Eventually, click on the run button

### Game rules

You can read the game rules on the Klotski's [Wikipedia](https://en.wikipedia.org/wiki/Klotski) page

## Documentation

Open the [documentation](https://umberto-1.gitbook.io/klotski-game/documentation)

## Screenshots

<figure><img src=".gitbook/assets/image (2).png" alt=""></figure>

## Acknowledgements

* [Klotski Solver](https://github.com/jeantimex/klotski/tree/master)

  This excellent NodeJS Klotski solver runs on our AWS Lambda. 

  It is called in Solver class thanks to sendPostRequest() method.
* [FlatLaf](https://www.formdev.com/flatlaf/)

  The FlatLaf library helps us achieve a modern look compared to Java Swing.
  
  Thanks to the setLookAndFeel method we are able to load the look specified by the given class name: FlatDarkLaf (Dark theme)

            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");

* [GitBook](https://www.gitbook.com/)

  GitBook helped us create clean and organized documentation for this project.
* [Maven](https://maven.apache.org/)
* [JSON simple](https://code.google.com/archive/p/json-simple/)

  JSON Simple helped us in parsing the response from AWS Lambda into separate JSON objects, enabling us to process the data easily.

              moves = (JSONArray) parser.parse(response);
              ...
              JSONObject json = (JSONObject) moves.get(index_moves++);
  
* [MySQL Connector](https://www.mysql.com/)
  We use MySQL Connector to enable interaction and connection to our AWS database, since it provides the required driver. 

        conn = DriverManager.getConnection(dbURL, "admin", "mypassword");
        ...
        ResultSet rs = stmt.executeQuery(query);

  
