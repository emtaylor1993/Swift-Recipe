![image](https://github.com/emtaylor1993/School-Projects/assets/93065901/2a771cb7-46e5-4255-b806-d1cbf6c3c2cc)

# Team Members:
- Emmanuel Taylor

# System Requirements:
- Java 17
- Apache Maven >= 3.9.5
- Visual Studio Code >= 1.86.0
- Docker >= 27.5.1
- MySQL >= 8.0.40

# Installation Instructions:
## Installing Java
1. Navigate to https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html
2. Select the appropriate package for your Operating System.
3. Follow the installation prompts from the wizard.
4. Open up your command prompt and type the following command: <code>java --version</code>
5. You should see the following output: <code>OpenJDK 64-Bit Server VM Corretto-17.0.7.7.1 (build 17.0.7+7-LTS, mixed mode, sharing)</code>

## Installing Apache Maven
1. Navigate to https://maven.apache.org/download.cgi
2. Select the appropriate package for your Operating System.
3. Follow the installation prompts from the wizard.
4. Open up your command prompt and type the following command: <code>mvn --version</code>
5. You should see the following output: <code>Apache Maven 3.9.5 (57804ffe001d7215b5e7bcb531cf83df38f93546)</code>

## Installing Visual Studio Code
1. Navigate to https://code.visualstudio.com/download
2. Select the appropriate package for your Operating System.
3. Follow the installation prompts from the wizard.

## Installing Docker
1. Navigate to https://www.docker.com/get-started/
2. Select the appropriate package for your Operating System.
3. Follow the installation prompts from the wizard.
4. Verify proper installation by running the command: `docker --version`.

## Installing MySQL
1. Navigate to https://dev.mysql.com/downloads/installer/
2. Select the appropriate package for your Operating System
3. Follow the installation prompts from the wizard.
4. Verify proper installation by running the command: `mysql --version`.
5. Create a connection for the application.

# Running the Application
## Command Line
1. Ensure MySQL is running on http://localhost:3306/swift-recipe
2. Open the project in Visual Studio Code.
3. In the embedded terminal, type the following command: <code>mvn clean spring-boot:run</code>
4. Navigate to https://localhost:8080

## Executable Jar File
1. Ensure MySQL is running on http://localhost:3306/swift-recipe
2. Open the project in Visual Studio Code.
3. In the embedded terminal, type the following command: <code>mvn package</code>
4. Navigate to the folder containing the newly generated jar file.
5. Double click the jar file to run the application.
6. Navigate to https://localhost:8080

## Docker
1. Open the project in Visual Studio Code.
2. In the embedded terminal, navigate to the `swift-recipe` directory.
3. Type in the following commmand: `docker build -t swift-recipe .`
4. Navigate to the `swift-recipe-cypress-testing` directory.
5. Type in the following command: `docker build -t cypress .`
6. Navigate to the root of the project and type: `docker compose up`
7. If you wish to run Cypress testing, open a separate terminal and type in `docker exec -it cypress /bin/sh`
8. In the interactive container shell, type the following command: `npx cypress run`
