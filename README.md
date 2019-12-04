# CashRegisterSystemJEE
## Description:

### Cash Register System

 Cash Register System. The cashier can open the check, add the selected goods by code from the database (parsley = 234, bread = 222) or by the name of the goods, indicate the number of goods or weight. Close the check. The senior cashier can cancel the check, cancel one item on the check and return the money to the buyer. Make X and Z reports. A merchandiser can create goods and indicate their number in stock.
 
## You should install to launch the project:
-JDK 8

-MySQl

-Tomcat

-Maven

## Installation instructions:
-Clone or download project from GitHub

-Open folder "CashRegisterSystemJEE/WebContent/WEB-INF/sql/createDatabase.sql"

-Create and insert database at MySQL

-"mvn clean package -DskipTests" command for build

-deploy "*.war" file to Tomcat from /target

-start up Tomcat, launch your web browser, navigate to application URL

### Data for login
-Cashier - login: cashier@gmail.com password: cashier

-Senior Cashier - login: scashier@gmail.com password: scashier

-Adder in stock - login: adder@gmail.com password: adder
