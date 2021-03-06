# Description:
Java UI program that manages a store with clients, suppliers and products. <br />
Has a notifications system that notifies a client when a product goes on sale or is re-stocked. <br />
<br />
Complies with all the OOP standards but could also be improved. <br />
Can import a file directly using '-Dimport=<file_path>'. <br />

# Design Patterns:
* State -> Used to manage client's ranking inside the store
* Observer -> Used to manage a notifications system between clients and products
* Facade -> Used to connect our app (frontend) with our backend (core)

## How to run:
Execute the following command:
```
cd woo-core && make clean && make && cd .. && cd woo-app && make clean && make && cd .. 
java -cp <po-uuilib.jar_path>:<woo-app.jar_path>:<woo-core.jar_path> woo.app.App
```
