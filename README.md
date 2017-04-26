# ChatServer

## Server can be run by compiling and running ChatServer.java. By default the server will start listening on port 1111.

## Clients can be run by running the ChatClient.java. Only 2 clients are supported.
  
## CNN Model 2: 

### VGG19 model:
Files added:
dVGG19.py

Notes: The Visual Geometry Groups's CNN model(Paper: ICLR 2015) retrained for Fish Species classification and couple of dense layers added on top. Various hyperparameters tried for optimizations and training, results were to the order of 1.87 log loss on Validation set with 57% accuracy over the 8 species. Gains expected with more epochs with better SGD convergence and by ensembling with LGB..
	
Directions to run: 
```
python dVGG19.py #Train the model
```
Once trained and the weight file is created, turn train flag to False and re-run.
```
python dVGG19.py #Loads model with weight files and runs prediction on test dataset
```
